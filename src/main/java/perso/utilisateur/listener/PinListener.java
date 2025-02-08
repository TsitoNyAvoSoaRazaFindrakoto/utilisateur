package perso.utilisateur.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;
import perso.utilisateur.config.ParameterSercurity;
import perso.utilisateur.models.Pin;
import perso.utilisateur.util.SecurityUtil;

import java.time.LocalDateTime;

@Component
public class PinListener {

    private ParameterSercurity parameterSercurity;

    public PinListener(ParameterSercurity parameterSercurity) {
        this.parameterSercurity = parameterSercurity;
    }

    @PrePersist
    @PreUpdate
    public void prePersist(Pin pin){
        System.out.println(pin.getPinValue());
        pin.setPinValue(SecurityUtil.hashPassword(pin.getPinValue()));
        pin.setDateExpiration(LocalDateTime.now().plusSeconds(parameterSercurity.getAuthentificationPinDuree()));
    }
}
