package perso.utilisateur.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import perso.utilisateur.dto.InscriptionDTO;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.models.Pin;
import perso.utilisateur.models.TentativeConnection;
import perso.utilisateur.models.Token;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.repositories.TentativeRepo;
import perso.utilisateur.repositories.UtilisateurRepo;
import perso.utilisateur.util.SecurityUtil;

@Service
public class InscriptionService {
	@Autowired private UtilisateurRepo utilisateurRepo;
	@Autowired private TokenService tokenService;
	@Autowired private MailService mailService;
	@Autowired private PinService pinService;
	@Autowired private TentativeRepo tentativeRepo;


	@Transactional
	public ResponseJSON sendValidationMail(Utilisateur u){
		
		String p = SecurityUtil.generatePin();
		Pin pin = pinService.save(new Pin(p));
		
		TentativeConnection tentativeConnection = new TentativeConnection();
		tentativeConnection.setNombre(0);
		tentativeConnection = tentativeRepo.save(tentativeConnection);
		
		u.setPin(pin);
		u.setTentativeConnection(tentativeConnection);

		u = utilisateurRepo.save(u);
		
		mailService.sendPinEmail(u, p);
		
		return new ResponseJSON("email envoye",200,u.getToken().getTokenValue());
	}

}
