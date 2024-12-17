package perso.utilisateur.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.repositories.UtilisateurRepo;

@Service
public class MailService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private UtilisateurRepo utilisateurRepo;

	public void sendEmail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		message.setFrom("your-email@gmail.com");

		mailSender.send(message);
	}

	public void sendEmail(Object utilisateur,String pin) {
		if (utilisateur instanceof Integer) {
			utilisateur = utilisateurRepo.findById((Integer) utilisateur).get();
		}
		sendEmail(((Utilisateur)utilisateur).getEmail(),"Pin code", pin);
	}
}
