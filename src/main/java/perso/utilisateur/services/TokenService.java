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
	private final TokenRepo tokenRepo;
	private final UtilisateurRepo utilisateurRepo;

	public TokenService(TokenRepo tokenRepo, UtilisateurRepo utilisateurRepo) {
		this.tokenRepo = tokenRepo;
		this.utilisateurRepo = utilisateurRepo;
	}

	private Token createToken(){
		Token t = new Token();
		return tokenRepo.save(t);
	}

	public boolean isTokenValid(Token t){
		return t.getDateExpiration().isAfter(LocalDateTime.now());
	}

	@Transactional
	public Token reassignUserToken(String ActualToken){
		Utilisateur u = utilisateurRepo.findUtilisateurByToken(ActualToken).orElse(null);
		return reassignUserToken(u);
	}

	@Transactional
	public Token reassignUserToken(Utilisateur u){
		if (u == null || !isTokenValid(u.getToken())) {
			return null;
		}
		return createUserToken(u);
	}

	@Transactional 
	public Token createUserToken(Utilisateur u){
		if (u.getToken() != null) {
			return updateUserToken(u);
		}
		return assignUserToken(u);
	}

	@Transactional
	public Token updateUserToken(Utilisateur u){
		Token oldToken = u.getToken();
		oldToken.updateToken();
		return tokenRepo.save(oldToken);
	}

	@Transactional
	public Token assignUserToken(Utilisateur u){
		Token newT = createToken();
		u.setToken(newT);
		utilisateurRepo.save(u);
		return newT;
	}
}
