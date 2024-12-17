package perso.utilisateur.exception;

import lombok.Data;
import perso.utilisateur.models.Utilisateur;

@Data
public class PasswordInvalidException extends RuntimeException{
    private Utilisateur utilisateur;
    public PasswordInvalidException(Utilisateur utilisateur){
        super("Mot de passe invalide");
        this.setUtilisateur(utilisateur);
    }
}
