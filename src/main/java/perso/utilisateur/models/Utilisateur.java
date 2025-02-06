package perso.utilisateur.models;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import perso.utilisateur.dto.InscriptionDTO;
import perso.utilisateur.other.POV;
import perso.utilisateur.util.SecurityUtil;

@Entity
@Table(name = "utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    @JsonView(POV.Public.class)
    private Integer idUtilisateur;

    @Column(name = "pseudo", nullable = false)
    @JsonView(POV.Public.class)
    private String pseudo;

    @Column(name = "email", nullable = false, unique = true)
    @JsonView(POV.Public.class)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
    @JsonView(POV.Public.class)
    private Role role;

    public Pin setPin() {
        return new Pin(SecurityUtil.generatePin(),this);
    }

    public TentativeConnection increaseNumberAttempt(TentativeConnection tentativeBefore) {
        return new TentativeConnection(tentativeBefore.getNombre()+1);
    }

    public static Utilisateur from(InscriptionDTO inscriptionDTO) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo(inscriptionDTO.getPseudo());
        utilisateur.setEmail(inscriptionDTO.getEmail());
        utilisateur.setPassword(SecurityUtil.hashPassword(inscriptionDTO.getPassword()));
        utilisateur.setRole(new Role(1, "Membre simple"));
        return utilisateur;
    }
}
