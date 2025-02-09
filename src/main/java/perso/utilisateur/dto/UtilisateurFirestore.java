package perso.utilisateur.dto;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import perso.utilisateur.models.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurFirestore {
    private int idUtilisateur;
    private String pseudo;
    private String email;
    private String password;
    private String pin;
    private String role;

    public UtilisateurFirestore(Map<String,Object> map){
        this.idUtilisateur=((Long)map.get("idUtilisateur")).intValue();
        this.pseudo=(String)map.get("pseudo");
        this.email=(String)map.get("email");
        this.password=(String)map.get("password");
        this.pin=(String)map.get("pin");
        this.email=(String)map.get("email");
        this.role=(String)map.get("role");
    }

    public static UtilisateurFirestore turnIntoUtilisateurFirestore(QueryDocumentSnapshot document){
        try{
            return new UtilisateurFirestore(document.getData());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UtilisateurFirestore setData(Map<String,Object> map){
        this.pseudo=(String)map.get("pseudo");
        this.email=(String)map.get("email");
        this.password=(String)map.get("password");
        this.pin=(String)map.get("pin");
        this.email=(String)map.get("email");
        this.role=(String)map.get("role");
        return this;
    }

    public Utilisateur createUser(){
        return new Utilisateur(this.idUtilisateur,pseudo,email,password,new Role(role));
    }
}
