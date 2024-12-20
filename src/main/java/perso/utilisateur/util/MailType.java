package perso.utilisateur.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;

import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.services.TokenService;

public class MailType {
	String path, subject;
	Utilisateur to;
	private HashMap<String, Object> data = new HashMap<>();

	public static MailType pin(Utilisateur to, String pin, TokenService tokenService) {
		MailType mt = new MailType();
		mt.to = to;
		mt.path = "mail/pinMail.html";
		mt.subject = "Login Validation";
		mt.data.put("pin", pin);
		tokenService.createUserToken(to);
		return mt;
	}

	public String getSubject() {
		return subject;
	}

	public String getTargetEmail() {
		return to.getEmail();
	}

	public void addData(String key, Object value) {
		data.put(key, value);
	}

	public String buildHtml() {
		try {
			// Load the file from resources/templates/mail
			Path templatePath = new ClassPathResource("templates/" + path).getFile().toPath();
			String template = Files.readString(templatePath);

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
