package perso.utilisateur.services;

import org.springframework.stereotype.Service;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.exception.ConnectionAttemptException;
import perso.utilisateur.exception.PasswordInvalidException;
import perso.utilisateur.exception.PinExpiredException;
import perso.utilisateur.exception.WrongPinException;
import perso.utilisateur.models.Token;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.repositories.UtilisateurRepo;
import perso.utilisateur.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UtilisateurService {
    private UtilisateurRepo utilisateurRepo;
    private TentativeConnectionService tentativeConnectionService;
    private RoleService roleService;
    private TokenService tokenService;

    private MailService mailService;

    public UtilisateurService(TokenService tokenService,UtilisateurRepo utilisateurRepo, TentativeConnectionService tentativeConnectionService, RoleService roleService, MailService mailService) {
        this.utilisateurRepo = utilisateurRepo;
        this.tentativeConnectionService = tentativeConnectionService;
        this.roleService = roleService;
        this.mailService = mailService;
        this.tokenService=tokenService;
    }

		public Utilisateur findByEmail(String email)throws RuntimeException{
        return utilisateurRepo.findByEmail(email).orElseThrow(()->new RuntimeException("Email inexistante"));
    }

    public ResponseJSON testLogin(String email,String password)throws RuntimeException{
        Utilisateur utilisateur=this.findByEmail(email);
        if(SecurityUtil.matchPassword(password,utilisateur.getPassword())){
            utilisateur.setPin();
            mailService.sendPinEmail(utilisateur,utilisateur.getPin().getPinValue());
            utilisateur = this.save(utilisateur);
            return new ResponseJSON("Login valide",200,utilisateur.getToken().getTokenValue());
        }
        throw new PasswordInvalidException(utilisateur);
    }

    public Utilisateur save(Utilisateur utilisateur){
        utilisateur.setRole(this.roleService.findById(1));
        return utilisateurRepo.save(utilisateur);
    }

    public ResponseJSON getByIdWithToken(int idUtilisateur) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepo.findById(idUtilisateur);

        if (utilisateurOpt.isEmpty()) {
            return new ResponseJSON("Utilisateur introuvable", 404);
        }

        Utilisateur utilisateur = utilisateurOpt.get();

       Token token = tokenService.reassignUserToken(utilisateur.getToken().getTokenValue());

       if (token == null) {
           return new ResponseJSON("Token expiré", 401);
       }
        return new ResponseJSON("Utilisateur bien récupéré", 200, utilisateur);
    }


    public ResponseJSON login(String email, String password)throws RuntimeException{
        try{
            return this.testLogin(email,password);
        }
        catch (PasswordInvalidException exception){
            return this.increaseAttempt(exception.getUtilisateur(),exception.getMessage());
        }
        catch (RuntimeException exception){
            return new ResponseJSON(exception.getMessage(),401);
        }
    }

    public ResponseJSON increaseAttempt(Utilisateur utilisateur,String message){
        try{
            tentativeConnectionService.increaseNumberAttempt(utilisateur);
        }
        catch (ConnectionAttemptException connectionException){
            return new ResponseJSON(connectionException.getMessage(),200,utilisateur.getIdUtilisateur());
        }

        finally {
            this.save(utilisateur);
        }
        return new ResponseJSON(message,500);
    }

    public ResponseJSON loginPin(String pin,Utilisateur utilisateur){
        String pinHashed=utilisateur.getPin().getPinValue();
        if(SecurityUtil.matchPassword(pin,pinHashed)){
            if(utilisateur.getPin().getDateExpiration().isBefore(LocalDateTime.now())){
                throw new PinExpiredException(utilisateur);
            }
            tokenService.reassignUserToken(utilisateur);
            return new ResponseJSON("Code pin valide",200,utilisateur);
        }
        throw new WrongPinException(utilisateur);
    }

    public ResponseJSON loginPin(String pin,String tokenUtilisateur){
        Utilisateur utilisateur=null;
        try{
            utilisateur=this.utilisateurRepo.findUtilisateurByToken(tokenUtilisateur).orElseThrow(()->new RuntimeException());
            return loginPin(pin,utilisateur);
        }
        catch (PinExpiredException ex){
            return increaseAttempt(ex.getUtilisateur(),ex.getMessage());
        }
        catch (WrongPinException ex){
            return increaseAttempt(ex.getUtilisateur(),ex.getMessage());
        }
        catch (RuntimeException ex){
            System.out.println("Exception "+ex.getMessage());
            return new ResponseJSON(ex.getMessage(),401);
        }
    }
}
