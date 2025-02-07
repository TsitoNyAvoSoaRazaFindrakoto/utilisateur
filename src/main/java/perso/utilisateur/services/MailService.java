package perso.utilisateur.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.models.inscription.Inscription;
import perso.utilisateur.repositories.UtilisateurRepo;
import perso.utilisateur.util.MailType;



@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UtilisateurRepo utilisateurRepo;
		@Autowired
		private TokenService tokenService;

    public void sendEmail(MailType mailType) {
        try {
            // Build the HTML content from MailType
            String htmlContent = mailType.buildHtml();
						
            // Prepare the email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(mailType.getTargetEmail());
            helper.setSubject(mailType.getSubject());

            // Set the HTML content
            helper.setText(htmlContent, true); // Enable the second parameter for HTML

            // Send the email
            mailSender.send(message);
            System.out.println("Email sent successfully with HTML content.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendPinEmail(Object utilisateur, String pin) {
        if (utilisateur instanceof Integer) {
            utilisateur = utilisateurRepo.findById((Integer) utilisateur).orElseThrow(
                () -> new IllegalArgumentException("Utilisateur not found"));
        }
        sendEmail(MailType.pin((Utilisateur) utilisateur,pin,tokenService));
    }

    public void sendEmailInscription(MailType mailType) {
        try {
            // Build the HTML content from MailType
            String htmlContent = mailType.buildHtml();

            // Prepare the email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(mailType.getTargetEmail());
            helper.setSubject(mailType.getSubject());

            // Set the HTML content
            helper.setText(htmlContent, true); // Enable the second parameter for HTML

            // Send the email
            mailSender.send(message);
            System.out.println("Email sent successfully with HTML content.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
