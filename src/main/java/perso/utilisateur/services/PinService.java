package perso.utilisateur.services;

import org.springframework.stereotype.Service;
import perso.utilisateur.models.Pin;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.repositories.PinRepo;
import perso.utilisateur.util.PasswordUtil;

@Service
public class PinService {
    private PinRepo pinRepo;

    public PinService(PinRepo pinRepo) {
        this.pinRepo = pinRepo;
    }

    public String generatePin(Utilisateur utilisateur){
        String pinRaw= PasswordUtil.generatePin();
        Pin pin=new Pin(PasswordUtil.hashPassword(pinRaw));
        utilisateur.setPin(pin);
        return pinRaw;
    }

    public Pin save(Pin pin){
        return this.pinRepo.save(pin);
    }
}
