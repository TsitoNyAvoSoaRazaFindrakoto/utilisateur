package perso.utilisateur.exception;

public class ConnectionAttemptException extends RuntimeException{
    public ConnectionAttemptException(){
        super("Le nombre de tentative de connection a été dépassé, pour réactiver votre compte inserer le code pin");
    }
}
