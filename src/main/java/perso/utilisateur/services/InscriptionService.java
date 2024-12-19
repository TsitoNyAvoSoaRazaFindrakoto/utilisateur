package perso.utilisateur.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import perso.utilisateur.dto.InscriptionDTO;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.models.Pin;
import perso.utilisateur.models.Token;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.repositories.UtilisateurRepo;
import perso.utilisateur.util.SecurityUtil;

@Service
public class InscriptionService {
	@Autowired
	private UtilisateurRepo utilisateurRepo;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private MailService mailService;

	Utilisateur createUser(InscriptionDTO inscriptionDTO){
		return utilisateurRepo.save(Utilisateur.from(inscriptionDTO));
	}

	@Transactional
	public ResponseJSON sendValidationMail(Utilisateur u){
		Token t = tokenService.createUserToken(u);
		String p = SecurityUtil.generatePin();
		u.setPin(new Pin(p));
		mailService.sendPinEmail(u, p);
		return new ResponseJSON("email envoye",200,t.getTokenValue());
	}

}
