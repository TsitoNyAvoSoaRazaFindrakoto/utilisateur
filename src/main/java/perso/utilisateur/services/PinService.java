package perso.utilisateur.services;

import org.springframework.stereotype.Service;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.models.Pin;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.repositories.PinRepo;
import perso.utilisateur.util.SecurityUtil;

@Service
public class PinService {
    private PinRepo pinRepo;

    public PinService(PinRepo pinRepo) {
        this.pinRepo = pinRepo;
    }

    public Pin save(Pin pin){
        return this.pinRepo.save(pin);
    }
}
