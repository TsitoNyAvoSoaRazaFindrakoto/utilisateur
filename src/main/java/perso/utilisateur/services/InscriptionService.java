package perso.utilisateur.services;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import perso.utilisateur.dto.InscriptionDTO;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.models.*;
import perso.utilisateur.repositories.TentativeRepo;
import perso.utilisateur.repositories.UtilisateurRepo;
import perso.utilisateur.services.firebase.FirestoreService;
import perso.utilisateur.util.SecurityUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class InscriptionService {
	private UtilisateurRepo utilisateurRepo;
	private TokenService tokenService;
	private MailService mailService;
	private PinService pinService;
	private TentativeRepo tentativeRepo;
	private FirestoreService firestoreService;
	private EntityManager entityManager;


	@Transactional
	public ResponseJSON sendValidationMail(Utilisateur u) {
		try {
			u.setIdUtilisateur(firestoreService.insertUtilisateur(turnIntoMap(u)));
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		u = this.savePrepare(u);
		String p = SecurityUtil.generatePin();
		Pin pin = pinService.save(new Pin(p,u));

		TentativeConnection tentativeConnection = new TentativeConnection(u);
		tentativeConnection.setNombre(0);
		tentativeRepo.save(tentativeConnection);

		pinService.save(pin);

		mailService.sendPinEmail(u, p);
		
		return new ResponseJSON("email envoye",200,tokenService.findUserToken(u).getTokenValue());
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
				.setParameter(1,utilisateur.getIdUtilisateur())
				.setParameter(2, utilisateur.getPseudo())
				.setParameter(3, utilisateur.getEmail())
				.setParameter(4, utilisateur.getPassword())
				.setParameter(5,1)
				.executeUpdate();
		return this.utilisateurRepo.findById(utilisateur.getIdUtilisateur()).orElseThrow(()->new RuntimeException("Tsy possibile ty exception ty nefa tsy maintsy asina throw"));
	}
}
