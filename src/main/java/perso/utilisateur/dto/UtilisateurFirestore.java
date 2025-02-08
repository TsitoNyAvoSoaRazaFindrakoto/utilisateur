package perso.utilisateur.dto;

import com.google.cloud.Timestamp;
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
    private Token token;
    private String role;
    private Long tentativeConnection;

    public UtilisateurFirestore(Map<String,Object> map){
        this.idUtilisateur=((Long)map.get("idUtilisateur")).intValue();
        this.pseudo=(String)map.get("pseudo");
        this.email=(String)map.get("email");
        this.password=(String)map.get("password");
        this.pin=(String)map.get("pin");
        this.email=(String)map.get("email");
        Map<String,Object> token=(Map<String, Object>) map.get("token");
        this.token=new Token((String)token.get("token"),((Timestamp) token.get("dateExpiration")).toSqlTimestamp().toLocalDateTime());
        this.role=(String)map.get("role");
        this.tentativeConnection=(Long)map.get("tentativeConnection");
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
        Map<String,Object> token=(Map<String, Object>) map.get("token");
        this.token=new Token((String)token.get("token"),((Timestamp) token.get("dateExpiration")).toSqlTimestamp().toLocalDateTime());
        this.role=(String)map.get("role");
        this.tentativeConnection=(Long)map.get("tentativeConnection");
        return this;
    }

    public Utilisateur createUser(Role role){
        return new Utilisateur(this.idUtilisateur,pseudo,email,password,role);
    }
}
