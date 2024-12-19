package perso.utilisateur.dto;

import lombok.Data;
import perso.utilisateur.config.ParameterSercurity;

@Data
public class ParametreDTO {
    private Integer nbTentativeMax;
    private Integer dureeSession;
    private Integer pinDuree;

    public void updateAll(ParameterSercurity parameterSercurity){
        if(nbTentativeMax!=null){
            parameterSercurity.setNombreTentativeLimite(nbTentativeMax);
        }
        if(dureeSession!=null){
            parameterSercurity.setDurreVieSession(dureeSession);
        }
        if(pinDuree!=null){
            parameterSercurity.setAuthentificationPinDuree(pinDuree);
        }
    }
}
