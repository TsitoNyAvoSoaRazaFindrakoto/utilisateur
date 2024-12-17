package perso.utilisateur.services;

import org.springframework.stereotype.Service;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.exception.ConnectionAttemptException;
import perso.utilisateur.exception.PasswordInvalidException;
import perso.utilisateur.exception.WrongPinException;
import perso.utilisateur.models.TentativeConnection;
import perso.utilisateur.models.Token;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.repositories.UtilisateurRepo;
import perso.utilisateur.util.SecurityUtil;

@Service
public class UtilisateurService {
    private UtilisateurRepo utilisateurRepo;
    private TentativeConnectionService tentativeConnectionService;
    private RoleService roleService;
		private TokenService tokenService;

    public UtilisateurService(UtilisateurRepo utilisateurRepo, TentativeConnectionService tentativeConnectionService,
				RoleService roleService, TokenService tokenService) {
			this.utilisateurRepo = utilisateurRepo;
			this.tentativeConnectionService = tentativeConnectionService;
			this.roleService = roleService;
			this.tokenService = tokenService;
		}

		public Utilisateur findByEmail(String email)throws RuntimeException{
        return utilisateurRepo.findByEmail(email).orElseThrow(()->new RuntimeException("Email inexistante"));
    }

    public ResponseJSON testLogin(String email,String password)throws RuntimeException{
        Utilisateur utilisateur=this.findByEmail(email);
        if(SecurityUtil.matchPassword(password,utilisateur.getPassword())){
            tokenService.createUserToken(utilisateur);
            return new ResponseJSON("Login valide",200,utilisateur.getIdUtilisateur());
        }
        throw new PasswordInvalidException(utilisateur);
    }

    public Utilisateur save(Utilisateur utilisateur){
        utilisateur.setRole(this.roleService.findById(1));
        utilisateur.setTentativeConnection(new TentativeConnection());
        return utilisateurRepo.save(utilisateur);
    }

    public ResponseJSON login(String email, String password)throws RuntimeException{
        try{
            return this.testLogin(email,password);
        }
        catch (PasswordInvalidException exception){
            return this.increaseAttempt(exception.getUtilisateur(),exception.getMessage());
        }
        catch (RuntimeException exception){
            return new ResponseJSON(exception.getMessage(),500);
        }
    }

    public ResponseJSON increaseAttempt(Utilisateur utilisateur,String message){
        try{
            tentativeConnectionService.increaseNumberAttempt(utilisateur);
            this.save(utilisateur);
        }
        catch (ConnectionAttemptException connectionException){
            return new ResponseJSON(connectionException.getMessage(),200,utilisateur.getIdUtilisateur());
        }
        return new ResponseJSON(message,500);
    }

    public ResponseJSON loginPin(String pin,Utilisateur utilisateur){
        String pinHashed=utilisateur.getPin().getPinValue();
        if(SecurityUtil.matchPassword(pin,pinHashed)){
            return new ResponseJSON("Code pin valide",200,tokenService.createUserToken(utilisateur).getTokenValue());
        }
        throw new WrongPinException(utilisateur);
    }

    public ResponseJSON loginPin(String pin,Integer idUtilisateur){
        Utilisateur utilisateur=null;
        try{
            utilisateur=this.utilisateurRepo.findById(idUtilisateur).orElseThrow(()->new RuntimeException());
            return loginPin(pin,utilisateur);
        }
        catch (WrongPinException ex){
            return increaseAttempt(ex.getUtilisateur(),ex.getMessage());
        }
        catch (RuntimeException ex){
            return new ResponseJSON(ex.getMessage(),500);
        }
    }
}
