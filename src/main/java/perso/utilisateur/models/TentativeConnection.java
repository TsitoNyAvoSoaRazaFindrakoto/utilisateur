package perso.utilisateur.models;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "tentative_connection")
@Data
@Entity
public class TentativeConnection {
    @Id
    @Column(name = "id_tentative_connection")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTentativeConnection;

    private int nombre;
}
