package perso.utilisateur.exception;

public class TokenNotFoundException extends RuntimeException{
    public TokenNotFoundException(){
        super("Token introuvable");
    }
}
