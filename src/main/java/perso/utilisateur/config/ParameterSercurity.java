package perso.utilisateur.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
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
    private int authentificationPinDuree;

    private ConfigurableEnvironment environment;

    public void updateNombreTentativeLimite(int nbTentative){
        environment.getSystemEnvironment().put("tentative.limite.nombre",nbTentative);
    }

    public void updateDureeVieSession(double durreVieSession){
        environment.getSystemEnvironment().put("session.duree",durreVieSession);
    }

    public void updateAuthentificationPinDuree(double authentificationPinDuree){
        environment.getSystemEnvironment().put("authentification.pin.duree",authentificationPinDuree);
    }
}
