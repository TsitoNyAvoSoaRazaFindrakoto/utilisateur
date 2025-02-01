package perso.utilisateur.services;

import org.springframework.stereotype.Service;
import perso.utilisateur.config.ParameterSercurity;
import perso.utilisateur.exception.ConnectionAttemptException;
import perso.utilisateur.models.Pin;
import perso.utilisateur.models.TentativeConnection;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.repositories.TentativeRepo;

@Service
public class TentativeConnectionService {

    private ParameterSercurity parameterSercurity;

    private MailService mailService;
    private PinService pinService;
    private TentativeRepo tentativeRepo;

    public TentativeConnectionService(ParameterSercurity parameterSercurity, MailService mailService, PinService pinService, TentativeRepo tentativeRepo) {
        this.parameterSercurity = parameterSercurity;
        this.mailService = mailService;
        this.pinService = pinService;
        this.tentativeRepo = tentativeRepo;
    }

    public void increaseNumberAttempt(Utilisateur utilisateur)throws ConnectionAttemptException{
        TentativeConnection tentativeConnection = utilisateur.increaseNumberAttempt(tentativeRepo.findLastAttemptConnectionUser(utilisateur.getIdUtilisateur()).orElse(new TentativeConnection(utilisateur)));
        if(tentativeConnection.getNombre() % parameterSercurity.getNombreTentativeLimite() == 0){
            Pin pin=utilisateur.setPin();
            pinService.save(pin);
            mailService.sendPinEmail(utilisateur,pin.getPinValue());
            throw new ConnectionAttemptException();
        }
    }

    public TentativeConnection save(TentativeConnection tentativeConnection){
        return tentativeRepo.save(tentativeConnection);
    }
}
