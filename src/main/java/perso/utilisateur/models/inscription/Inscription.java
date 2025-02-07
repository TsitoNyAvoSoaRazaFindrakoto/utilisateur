package perso.utilisateur.models.inscription;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import perso.utilisateur.dto.InscriptionDTO;
import perso.utilisateur.models.Role;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.other.POV;
import perso.utilisateur.util.SecurityUtil;

@Entity
@Table(name = "inscription")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscription")
    @JsonView(POV.Public.class)
    private Integer idInscription;

    @Column(name = "pseudo", nullable = false)
    @JsonView(POV.Public.class)
    private String pseudo;

    @Column(name = "email", nullable = false, unique = true)
    @JsonView(POV.Public.class)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    public static Inscription from(InscriptionDTO inscriptionDTO) {
        Inscription utilisateur = new Inscription();
        utilisateur.setPseudo(inscriptionDTO.getPseudo());
        utilisateur.setEmail(inscriptionDTO.getEmail());
        utilisateur.setPassword(SecurityUtil.hashPassword(inscriptionDTO.getPassword()));
        return utilisateur;
    }

    public Utilisateur turnUtilisateur(){
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo(this.getPseudo());
        utilisateur.setEmail(this.getEmail());
        utilisateur.setPassword(this.getPassword());
        return utilisateur;
    }
}
