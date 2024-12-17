package perso.utilisateur.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.exception.ConnectionAttemptException;
import perso.utilisateur.exception.PasswordInvalidException;
import perso.utilisateur.models.Token;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.repositories.UtilisateurRepo;
import perso.utilisateur.util.SecurityUtil;

@Service
public class UtilisateurService {
    private UtilisateurRepo utilisateurRepo;

    private TentativeConnectionService tentativeConnectionService;

    public UtilisateurService(UtilisateurRepo utilisateurRepo, TentativeConnectionService tentativeConnectionService) {
        this.utilisateurRepo = utilisateurRepo;
        this.tentativeConnectionService = tentativeConnectionService;
    }

    public Utilisateur findByEmail(String email)throws RuntimeException{
        return utilisateurRepo.findByEmail(email).orElseThrow(()->new RuntimeException("Email inexistante"));
    }

    public ResponseJSON testLogin(String email,String password)throws RuntimeException{
        Utilisateur utilisateur=this.findByEmail(email);
        if(SecurityUtil.matchPassword(password,utilisateur.getPassword())){
            Token token=new Token();
            utilisateur.setToken(token);
            this.save(utilisateur);
            return new ResponseJSON("Login valide",200,utilisateur.getIdUtilisateur());
        }
        throw new PasswordInvalidException(utilisateur);
    }

    public Utilisateur save(Utilisateur utilisateur){
        return utilisateurRepo.save(utilisateur);
    }

    public ResponseJSON login(String email, String password)throws RuntimeException{
        try{
            return this.testLogin(email,password);
        }
        catch (PasswordInvalidException exception){
            Utilisateur utilisateur = exception.getUtilisateur();
            try{
                tentativeConnectionService.increaseNumberAttempt(utilisateur);
                this.save(utilisateur);
            }
            catch (ConnectionAttemptException connectionException){
                return new ResponseJSON(connectionException.getMessage(),200,utilisateur.getIdUtilisateur());
            }
            return new ResponseJSON(exception.getMessage(),500);
        }
        catch (RuntimeException exception){
            return new ResponseJSON(exception.getMessage(),500);
        }
    }


}
