package perso.utilisateur.exception;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(){
        super("Cette token est expiré veillez recevoir une autre");
    }
}
