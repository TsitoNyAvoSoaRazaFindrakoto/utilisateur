package perso.utilisateur.models.inscription;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import perso.utilisateur.other.POV;
import perso.utilisateur.util.SecurityUtil;

import java.time.LocalDateTime;

@Entity
@Table(name = "token_inscription")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token_inscription")
    @JsonView(POV.Public.class)
    private Integer idToken;

    @Column(name = "token", nullable = false, unique = true)
    @JsonView(POV.Public.class)
    private String tokenValue;

    @Column(name = "date_expiration", nullable = false)
    private LocalDateTime dateExpiration;

    private LocalDateTime dateCreation=LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_inscription")
    private Inscription inscription;

    public TokenInscription(Inscription inscription){
        this.setTokenValue(SecurityUtil.generateToken());
        this.setDateExpiration(LocalDateTime.now().plusHours(1));
        this.setInscription(inscription);
    }

    public TokenInscription(String tokenValue,LocalDateTime dateExpiration){
        this.setTokenValue(tokenValue);
        this.setDateExpiration(dateExpiration);
    }

    public void updateToken(){
        this.setTokenValue(SecurityUtil.generateToken());
        this.setDateExpiration(LocalDateTime.now().plusHours(1));
    }

    public String getUrl(){
        return "http://localhost:8082/utilisateur/inscription/validation/"+this.tokenValue;
    }
}
