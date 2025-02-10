package perso.utilisateur.services;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import perso.utilisateur.dto.InscriptionDTO;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.dto.UtilisateurFirestore;
import perso.utilisateur.exception.*;
import perso.utilisateur.models.Pin;
import perso.utilisateur.models.Token;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.repositories.UtilisateurRepo;
import perso.utilisateur.services.firebase.FirestoreService;
import perso.utilisateur.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UtilisateurService {
    private UtilisateurRepo utilisateurRepo;
    private TentativeConnectionService tentativeConnectionService;
    private RoleService roleService;
    private TokenService tokenService;
    private PinService pinService;

    private MailService mailService;
    private FirestoreService firestoreService;
    private InscriptionService inscriptionService;

    public Utilisateur findByEmail(String email) throws RuntimeException {
        return utilisateurRepo.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("Email inexistante"));
    }

    @Transactional
    public ResponseJSON pinRequest(String tokenValue) {
        try {
            Token token = this.tokenService.findByToken(tokenValue);
            Utilisateur utilisateur = token.getUtilisateur();
            Pin pin = utilisateur.setPin();
            mailService.sendPinEmail(utilisateur, pin.getPinValue());
            pinService.save(pin);
            utilisateur = this.save(utilisateur);
            return new ResponseJSON("Pin en attente de validation", 200, tokenService.createUserToken(utilisateur).getTokenValue());
        }
        catch (TokenNotFoundException | TokenExpiredException e){
            return new ResponseJSON("Token expiré",400);
        }
    }

    @Transactional
    public ResponseJSON testLogin(String email, String password) throws EmailNotFoundException, PasswordInvalidException {
        Utilisateur utilisateur = this.findByEmail(email);
        return matchPassword(password, utilisateur, utilisateur.getPassword());
    }

    @Transactional
    public Utilisateur save(Utilisateur utilisateur) {
        return utilisateurRepo.save(utilisateur);
    }

    public ResponseJSON getByIdWithToken(int idUtilisateur) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepo.findById(idUtilisateur);

        if (utilisateurOpt.isEmpty()) {
            return new ResponseJSON("Utilisateur introuvable", 404);
        }

        Utilisateur utilisateur = utilisateurOpt.get();

        Token token = tokenService.reassignUserToken(tokenService.findUserToken(utilisateur).getTokenValue());

        if (token == null) {
            return new ResponseJSON("Token expiré", 401);
        }
        return new ResponseJSON("Utilisateur bien récupéré", 200, utilisateur);
    }


    @Transactional
    public ResponseJSON login(String email, String password) throws RuntimeException {
        try {
            return this.testLogin(email, password);
        } catch (PasswordInvalidException exception) {
            return this.increaseAttempt(exception.getUtilisateur(), exception.getMessage());
        } catch (EmailNotFoundException exception) {
            return this.loginFirestore(email, password);
        }

    }

    @Transactional
    public ResponseJSON loginFirestore(String email, String password) {
        try {
            UtilisateurFirestore utilisateurFirestore = firestoreService.findUtilisateur(email);
            Utilisateur utilisateur = utilisateurFirestore.createUser();
            utilisateur = this.inscriptionService.savePrepare(utilisateur);
            if (SecurityUtil.matchPassword(password, utilisateur.getPassword())) {
                Pin pin = utilisateur.setPin();
                mailService.sendPinEmail(utilisateur, pin.getPinValue());
                pinService.save(pin);
                utilisateur = this.save(utilisateur);
                return new ResponseJSON("Login valide", 200, tokenService.findUserToken(utilisateur).getTokenValue());
            }
            return new ResponseJSON(new PasswordInvalidException(utilisateur).getMessage(), 401);
        } catch (EmailNotFoundException e) {
            return new ResponseJSON(e.getMessage(), 401);
        }
    }

    @Transactional
    public Utilisateur findUtilisateurById(Integer idUtilisateur) {
        return this.utilisateurRepo.findById(idUtilisateur).orElseThrow(() -> new RuntimeException("Id utilisateur non reconnue"));
    }

    @Transactional
    public ResponseJSON matchPassword(String password, Utilisateur utilisateur, String hashedPassword) throws PasswordInvalidException {
        if (SecurityUtil.matchPassword(password, hashedPassword)) {
            Pin pin = utilisateur.setPin();
            if (!utilisateur.getRole().getRoleName().equals("Admin")) {
                mailService.sendPinEmail(utilisateur, pin.getPinValue());
            }
            pinService.save(pin);
            utilisateur = this.save(utilisateur);
            return new ResponseJSON("Login valide", 200)
                    .addObject("token", tokenService.createUserToken(utilisateur).getTokenValue())
                    .addObject("isAdmin", utilisateur.getRole().getRoleName().equals("Admin"))
                    .addObject("utilisateur", utilisateur);
        }
        throw new PasswordInvalidException(utilisateur);
    }

    @Transactional
    public ResponseJSON increaseAttempt(Utilisateur utilisateur, String message) {
        try {
            tentativeConnectionService.increaseNumberAttempt(utilisateur);
        } catch (ConnectionAttemptException connectionException) {
            return new ResponseJSON(connectionException.getMessage(), 401, utilisateur.getIdUtilisateur());
        } finally {
            this.save(utilisateur);
        }
        return new ResponseJSON(message, 401);
    }

    public ResponseJSON loginPin(String pin, Utilisateur utilisateur) {
        Pin pinBase = pinService.findPinUtilisateur(utilisateur.getIdUtilisateur());
        String pinHashed = pinBase.getPinValue();
        if (SecurityUtil.matchPassword(pin, pinHashed)) {
            if (pinBase.getDateExpiration().isBefore(LocalDateTime.now())) {
                throw new PinExpiredException(utilisateur);
            }
            return new ResponseJSON("Code pin valide", 200).addObject("utilisateur",utilisateur).addObject("token",tokenService.createUserToken(utilisateur).getTokenValue());
        }
        throw new WrongPinException(utilisateur);
    }

    @Transactional
    public ResponseJSON loginPin(String pin, String tokenUtilisateur) {
        Utilisateur utilisateur = null;
        try {
            utilisateur = this.utilisateurRepo.findUtilisateurByToken(tokenUtilisateur).orElseThrow(() -> new RuntimeException());
            return loginPin(pin, utilisateur);
        } catch (PinExpiredException ex) {
            Pin newPin = utilisateur.setPin();
            mailService.sendPinEmail(utilisateur, newPin.getPinValue());
            pinService.save(newPin);
            utilisateur = utilisateurRepo.save(utilisateur);
            return new ResponseJSON(ex.getMessage(), 400, tokenService.findUserToken(utilisateur).getTokenValue());
            //return increaseAttempt(ex.getUtilisateur(),ex.getMessage());
        } catch (WrongPinException ex) {
            return increaseAttempt(ex.getUtilisateur(), ex.getMessage());
        }
    }

    public ResponseJSON updateUtilisateur(InscriptionDTO inscriptionDTO) {
        try {
            Token token = this.tokenService.findByToken(inscriptionDTO.getToken());
            Utilisateur utilisateur = token.getUtilisateur();
            utilisateur.setPseudo(inscriptionDTO.getPseudo());
            if (!inscriptionDTO.getPassword().equals("")) {
                utilisateur.setPassword(SecurityUtil.hashPassword(inscriptionDTO.getPassword()));
            }
            utilisateurRepo.save(utilisateur);
            return new ResponseJSON("Modification réussie", 200, tokenService.createUserToken(utilisateur).getTokenValue());
        } catch (TokenExpiredException | TokenNotFoundException e) {
            return new ResponseJSON(e.getMessage(), 400);
        }
    }

    public ResponseJSON testToken(Integer idUtilisateur, String tokenValue) {
        try {
            Token token = this.tokenService.findByTokenIdUtilisateur(idUtilisateur, tokenValue);
            if (token.getDateExpiration().isBefore(LocalDateTime.now())) {
                return new ResponseJSON("Token expiré", 400);
            }
            return new ResponseJSON("Token validé", 200, this.tokenService.createUserToken(token.getUtilisateur()).getTokenValue());
        } catch (TokenNotFoundException e) {
            return new ResponseJSON(e.getMessage(), 400);
        }
    }
}
