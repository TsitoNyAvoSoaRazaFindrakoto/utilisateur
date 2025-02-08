package perso.utilisateur.services;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import perso.utilisateur.exception.TokenExpiredException;
import perso.utilisateur.exception.TokenNotFoundException;
import perso.utilisateur.models.Token;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.models.inscription.Inscription;
import perso.utilisateur.models.inscription.TokenInscription;
import perso.utilisateur.repositories.TokenRepo;
import perso.utilisateur.repositories.UtilisateurRepo;
import perso.utilisateur.repositories.inscription.InscriptionRepo;
import perso.utilisateur.repositories.inscription.TokenInscriptionRepo;

@Service
@AllArgsConstructor
public class TokenService {
	private final TokenRepo tokenRepo;
	private final UtilisateurRepo utilisateurRepo;
	private final TokenInscriptionRepo tokenInscriptionRepo;

	private Token createToken(Utilisateur utilisateur){
		Token t = new Token(utilisateur);
		return tokenRepo.save(t);
	}

	private TokenInscription createToken(Inscription utilisateur){
		TokenInscription t = new TokenInscription(utilisateur);
		return tokenInscriptionRepo.save(t);
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

	public TokenInscription findUserToken(Inscription utilisateur){
		TokenInscription token=this.tokenInscriptionRepo.findTokenByUtilisateur(utilisateur.getIdInscription()).orElse(null);
		if(token==null){
			return tokenInscriptionRepo.save(new TokenInscription(utilisateur));
		}
		return token;
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
		return createToken(u);
	}

	@Transactional
	public TokenInscription createUserToken(Inscription u){
		TokenInscription newT = createToken(u);
		tokenInscriptionRepo.save(newT);
		return newT;
	}

	@Transactional
	public Token updateUserToken(Utilisateur u){
		Token oldToken = this.findUserToken(u);
		oldToken.updateToken();
		return tokenRepo.save(oldToken);
	}

	@Transactional
	public Token findByToken(String tokenValue) throws TokenNotFoundException, TokenExpiredException {
		Token token=tokenRepo.findToken(tokenValue).orElseThrow(TokenNotFoundException::new);
		Utilisateur utilisateur=token.getUtilisateur();
		if(!this.findUserToken(utilisateur).getTokenValue().equals(tokenValue)) {
			throw new TokenExpiredException();
		}
		if(token.getDateExpiration().isBefore(LocalDateTime.now())){
			throw new TokenExpiredException();
		}
		return token;
	}

	public Token findByTokenIdUtilisateur(Integer idUtilisateur, String tokenValue) {
		return this.tokenRepo.findByTokenIdUtilisateur(idUtilisateur,tokenValue).orElseThrow(TokenNotFoundException::new);
	}
}
