package perso.utilisateur.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import perso.utilisateur.config.ParameterSercurity;
import perso.utilisateur.models.Token;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.repositories.TokenRepo;
import perso.utilisateur.repositories.UtilisateurRepo;

@Service
public class TokenService {
	@Autowired
	private TokenRepo tokenRepo;
	@Autowired
	private UtilisateurRepo utilisateurRepo;
	@Autowired
	private ParameterSercurity parameterSercurity;

	private Token createToken(){
		Token t = new Token(null, UUID.randomUUID().toString(), LocalDateTime.now().plusHours(((int)parameterSercurity.getDurreVieSession()))); 

		return tokenRepo.save(t);
	}

	public boolean isTokenValid(Token t){
		return t.getDateExpiration().isBefore(LocalDateTime.now());
	}

	@Transactional
	public Token reassignToken(String ActualToken){
		Utilisateur u = utilisateurRepo.findUtilisteurFromTokenValue(ActualToken).get();
		if (!isTokenValid(u.getToken())) {
			return null;
		}
		deleteToken(u);
		return assignToken(u);
	}

	@Transactional
	public void deleteToken(Utilisateur u){
		Token oldToken = u.getToken();
		tokenRepo.delete(oldToken);
		u.setToken(null);
	}

	@Transactional
	public Token assignToken(Utilisateur u){
		Token newT = createToken();
		u.setToken(newT);
		utilisateurRepo.save(u);
		return newT;
	}
}
