package perso.utilisateur.services;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.exception.TokenExpiredException;
import perso.utilisateur.exception.TokenNotFoundException;
import perso.utilisateur.exception.UserAlreadyExistException;
import perso.utilisateur.models.*;
import perso.utilisateur.models.inscription.Inscription;
import perso.utilisateur.models.inscription.TokenInscription;
import perso.utilisateur.repositories.RoleRepo;
import perso.utilisateur.repositories.UtilisateurRepo;
import perso.utilisateur.repositories.inscription.InscriptionRepo;
import perso.utilisateur.repositories.inscription.TokenInscriptionRepo;
import perso.utilisateur.services.firebase.FirestoreService;
import perso.utilisateur.util.MailType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class InscriptionService {
	private UtilisateurRepo utilisateurRepo;
	private TokenService tokenService;
	private MailService mailService;
	private FirestoreService firestoreService;
	private EntityManager entityManager;
	private InscriptionRepo inscriptionRepo;
	private TokenInscriptionRepo tokenInscriptionRepo;
	private RoleRepo roleRepo;

	@Transactional
	public String validateInscription(String tokenInscription, Model model){
		try{
			return this.validate(tokenInscription);
		}
		catch (TokenNotFoundException | TokenExpiredException | UserAlreadyExistException e){
			model.addAttribute("message",e.getMessage());
			return "error_inscription";
		}
	}

	@Transactional
	public ResponseJSON sendValidationMail(Inscription u) {
		u=findInscriptionByEmail(u);
		TokenInscription tokenInscription=tokenService.findUserToken(u);
		mailService.sendEmailInscription(MailType.validation(u,tokenInscription));
		return new ResponseJSON("email envoye",200,tokenInscription.getTokenValue());
	}

	@Transactional
	public Inscription findInscriptionByEmail(Inscription u){
		Inscription inscription=inscriptionRepo.findInscriptionByEmail(u.getEmail()).orElse(null);
		if(inscription==null){
			return inscriptionRepo.save(u);
		}
		return inscription;
	}

	@Transactional
	public String validate(String tokenInscription)throws TokenExpiredException,TokenNotFoundException,UserAlreadyExistException{
		TokenInscription token=tokenInscriptionRepo.findToken(tokenInscription).orElseThrow(TokenNotFoundException::new);
		if(token.getDateExpiration().isBefore(LocalDateTime.now())){
			throw new TokenExpiredException();
		}
		Utilisateur utilisateur=utilisateurRepo.findByEmail(token.getInscription().getEmail()).orElse(null);
		if(utilisateur!=null){
			throw new UserAlreadyExistException(token.getInscription().getEmail());
		}
		utilisateur=token.getInscription().turnUtilisateur();
		utilisateur.setRole(roleRepo.findById(1).orElseThrow(()->new RuntimeException("Id role non retrouve")));
		saveUtilisateur(utilisateur);
		return "inscription";
	}

	@Transactional
	public void saveUtilisateur(Utilisateur u) {
		try {
			u.setIdUtilisateur(firestoreService.insertUtilisateur(turnIntoMap(u)));
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		u = this.savePrepare(u);
	}

	@Transactional
	public Map<String,Object> turnIntoMap(Utilisateur utilisateur){
		Role role = utilisateur.getRole();
		Map<String,Object> map=new HashMap<>();
		map.put("email",utilisateur.getEmail());
		map.put("mobile",false);
		map.put("password",utilisateur.getPassword());
		map.put("pseudo",utilisateur.getPseudo());
		map.put("role",utilisateur.getRole().getRoleName());
		return map;
	}

	@Transactional
	public Utilisateur savePrepare(Utilisateur utilisateur){
		this.entityManager.createNativeQuery("""
                                            insert into utilisateur(id_utilisateur,
                                                                    pseudo,
                                                                    email,
                                                                    password,
                                                                    id_role) 
                                            values(?,?,?,?,?)
                                            """)
				.setParameter(1, utilisateur.getIdUtilisateur())
				.setParameter(2, utilisateur.getPseudo())
				.setParameter(3, utilisateur.getEmail())
				.setParameter(4, utilisateur.getPassword())
				.setParameter(5,1)
				.executeUpdate();
		return this.utilisateurRepo.findById(utilisateur.getIdUtilisateur()).orElseThrow(()->new RuntimeException("Tsy possibile ty exception ty nefa tsy maintsy asina throw"));
	}
}
