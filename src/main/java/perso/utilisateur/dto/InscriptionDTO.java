package perso.utilisateur.dto;

import lombok.Data;
import perso.utilisateur.models.Utilisateur;

@Data
public class InscriptionDTO {
    private String pseudo;
    private String email;
    private String password;
}
