package perso.utilisateur;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import perso.utilisateur.services.MailService;

@SpringBootTest
class UtilisateurApplicationTests {

	@Autowired
	MailService mailService;

	@Test
	void contextLoads() {
	}


}
