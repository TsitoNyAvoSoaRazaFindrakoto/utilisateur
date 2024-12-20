package perso.utilisateur.exception;


import lombok.Data;
import perso.utilisateur.models.Utilisateur;

@Data
public class PinExpiredException extends RuntimeException {
    private Utilisateur utilisateur;

    public PinExpiredException(Utilisateur utilisateur){
        super("Ce pin est expiré, Veuillez recevoir une autre pin dans l'email");
        this.setUtilisateur(utilisateur);
    }
}
