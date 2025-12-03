package perso.utilisateur.exception;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String email){
        super("l'email "+email+" existe déjà");
    }
}
