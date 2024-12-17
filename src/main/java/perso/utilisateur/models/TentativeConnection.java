package perso.utilisateur.models;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "table_connection")
@Data
@Entity
public class TentativeConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTentativeConnection;

    private int nombre;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_utilisateur",unique = true)
    private Utilisateur utilisateur;
}
