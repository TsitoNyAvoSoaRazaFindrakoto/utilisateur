package perso.utilisateur.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
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

	private Token createToken(){
		Token t = new Token();

		return tokenRepo.save(t);
	}

	public boolean isTokenValid(Token t){
		return t.getDateExpiration().isBefore(LocalDateTime.now());
	}

	@Transactional
	public Token reassignUserToken(String ActualToken){
		Utilisateur u = utilisateurRepo.findUtilisteurFromTokenValue(ActualToken).orElse(null);
		if (u == null || !isTokenValid(u.getToken())) {
			return null;
		}
		return createUserToken(u);
	}

	@Transactional 
	public Token createUserToken(Utilisateur u){
		if (u.getToken() != null) {
			deleteUserToken(u);
		}
		return assignUserToken(u);
	}

	@Transactional
	void deleteUserToken(Utilisateur u){
		Token oldToken = u.getToken();
		tokenRepo.delete(oldToken);
		u.setToken(null);
	}

	@Transactional
	Token assignUserToken(Utilisateur u){
		Token newT = createToken();
		u.setToken(newT);
		utilisateurRepo.save(u);
		return newT;
	}
}
