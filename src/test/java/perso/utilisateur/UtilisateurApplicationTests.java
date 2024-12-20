package perso.utilisateur;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import perso.utilisateur.dto.InscriptionDTO;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.services.InscriptionService;
import perso.utilisateur.services.TokenService;

@SpringBootTest
class UtilisateurApplicationTests {

	@Autowired InscriptionService inscriptionService;
	@Autowired TokenService tokenService;
	

	@Test
	void contextLoads() {
	}

	@Test
	void testInscription(){
		InscriptionDTO insdto = new InscriptionDTO();
		insdto.setEmail("tsitonyavo@gmail.com");
		insdto.setPseudo("tsito");
		insdto.setPassword("cheh");

		ResponseJSON rep = inscriptionService.sendValidationMail(Utilisateur.from(insdto));


		System.out.println("for pin token :"+rep.getData());
	}

	@Test
	void testNewToken(){
		System.out.println("new token"+tokenService.reassignUserToken("8599f805-2b6a-478a-a328-e5b1bf88869e").getTokenValue());
	}
	

}
