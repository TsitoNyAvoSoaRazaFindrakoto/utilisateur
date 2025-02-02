package perso.utilisateur.services;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class InscriptionService {
	private UtilisateurRepo utilisateurRepo;
	private TokenService tokenService;
	private MailService mailService;
	private PinService pinService;
	private TentativeRepo tentativeRepo;


	@Transactional
	public ResponseJSON sendValidationMail(Utilisateur u){
		
		String p = SecurityUtil.generatePin();
		Pin pin = pinService.save(new Pin(p,u));
		
		TentativeConnection tentativeConnection = new TentativeConnection(u);
		tentativeConnection.setNombre(0);
		tentativeRepo.save(tentativeConnection);
		
		pinService.save(pin);

		u = utilisateurRepo.save(u);
		
		mailService.sendPinEmail(u, p);
		
		return new ResponseJSON("email envoye",200,tokenService.findUserToken(u).getTokenValue());
	}

}
