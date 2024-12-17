package perso.utilisateur.dto;

public record AuthentificationDTO(String email,String password,int nombreTentative) {
}
