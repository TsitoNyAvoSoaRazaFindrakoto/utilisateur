package perso.utilisateur.exception;

import lombok.Getter;
import lombok.Setter;
import perso.utilisateur.models.Utilisateur;

@Getter
@Setter
public class WrongPinException extends RuntimeException{
    private Utilisateur utilisateur;
    public WrongPinException(Utilisateur utilisateur){
        super("Pin invalide");
        this.setUtilisateur(utilisateur);
    }
}
