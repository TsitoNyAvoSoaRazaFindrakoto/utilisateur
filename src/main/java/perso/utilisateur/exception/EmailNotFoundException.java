package perso.utilisateur.exception;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(String email){
        super("L'email "+email+" n'existe pas");
    }
}
