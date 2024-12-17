package perso.utilisateur.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ParameterSercurity {
    @Value("${tentative.limite.nombre}")
    private int nombreTentativeLimite;

    //En Heure
    @Value("${session.duree}")
    private double durreVieSession;

    //En seconde
    @Value("${authentification.pin.duree}")
    private double authentificationPinDuree;
}
