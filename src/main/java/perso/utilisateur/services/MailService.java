package perso.utilisateur.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.repositories.UtilisateurRepo;

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
            helper.setSubject(mailType.subject);

            // Set the HTML content
            helper.setText(htmlContent, true); // Enable the second parameter for HTML

            // Send the email
            mailSender.send(message);
            System.out.println("Email sent successfully with HTML content.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(Object utilisateur, String pin) {
        if (utilisateur instanceof Integer) {
            utilisateur = utilisateurRepo.findById((Integer) utilisateur).orElseThrow(
                () -> new IllegalArgumentException("Utilisateur not found"));
        }
        sendEmail(MailType.pin((Utilisateur) utilisateur,pin,tokenService));
    }
}

class MailType {
    String path, subject;
		Utilisateur to; 
    private HashMap<String, Object> data = new HashMap<>();

    public static MailType pin(Utilisateur to,String pin,TokenService tokenService) {
        MailType mt = new MailType();
				mt.to = to;
        mt.path = "mail/PinMail.html";
				mt.subject = "Login Validation";
				mt.addData("link", "" + tokenService.createUserToken(to).getTokenValue());
        mt.data.put("pin", pin);
				
        return mt;
    }

		public String getTargetEmail(){
			return to.getEmail();
		}

		public void addData(String key, Object value) {
			data.put(key, value);
		}

    public String buildHtml() {
        try {
            // Read the template file
            String template = Files.readString(Path.of(path));

            // Replace placeholders in the template with data
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String placeholder = "\\{\\{" + entry.getKey() + "\\}\\}"; // Matches {{key}}
                template = template.replaceAll(placeholder, entry.getValue().toString());
            }
            return template;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
