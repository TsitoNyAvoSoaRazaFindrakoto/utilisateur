package perso.utilisateur.services;

import org.springframework.stereotype.Service;
import perso.utilisateur.config.ParameterSercurity;
import perso.utilisateur.exception.ConnectionAttemptException;
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

    public TentativeConnection increaseNumberAttempt(Utilisateur utilisateur)throws ConnectionAttemptException{
        TentativeConnection tentativeConnection=utilisateur.getTentativeConnection();
        if(tentativeConnection.getNombre()+1 == parameterSercurity.getNombreTentativeLimite()){
            String pin=this.pinService.generatePin(utilisateur);
            mailService.sendEmail(utilisateur,pin);
            throw new ConnectionAttemptException();
        }
        tentativeConnection.setNombre(tentativeConnection.getNombre()+1);
        return this.save(tentativeConnection);
    }

    public TentativeConnection save(TentativeConnection tentativeConnection){
        return tentativeRepo.save(tentativeConnection);
    }
}
