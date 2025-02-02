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

	private Token createToken(Utilisateur utilisateur){
		Token t = new Token(utilisateur);
		return tokenRepo.save(t);
	}

	public Token save(Token token){
		return this.tokenRepo.save(token);
	}

	@Transactional
	public boolean isTokenValid(Token t){
		return t.getDateExpiration().isAfter(LocalDateTime.now());
	}

	@Transactional
	public Token reassignUserToken(String ActualToken){
		Utilisateur u = utilisateurRepo.findUtilisateurByToken(ActualToken).orElse(null);
		return reassignUserToken(u);
	}

	public Token findUserToken(Utilisateur utilisateur){
		return this.tokenRepo.findTokenByUtilisateur(utilisateur.getIdUtilisateur()).orElse(new Token(utilisateur));
	}

	@Transactional
	public Token reassignUserToken(Utilisateur u){
		if (u == null || !isTokenValid(this.findUserToken(u))) {
			return null;
		}
		return createUserToken(u);
	}

	@Transactional
	public Token createUserToken(Utilisateur u){
		if (this.findUserToken(u) != null) {
			return updateUserToken(u);
		}
		return assignUserToken(u);
	}

	@Transactional
	public Token updateUserToken(Utilisateur u){
		Token oldToken = this.findUserToken(u);
		oldToken.updateToken();
		return tokenRepo.save(oldToken);
	}

	@Transactional
	public Token assignUserToken(Utilisateur u){
		Token newT = createToken(u);
		tokenRepo.save(newT);
		return newT;
	}
}
