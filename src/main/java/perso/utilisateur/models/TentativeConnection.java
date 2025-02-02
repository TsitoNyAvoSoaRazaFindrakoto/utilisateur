package perso.utilisateur.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "tentative_connection")
@Data
@Entity
@NoArgsConstructor
public class TentativeConnection {
    @Id
    @Column(name = "id_tentative_connection")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTentativeConnection;

    private int nombre;

    private LocalDateTime dateTentative=LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    public TentativeConnection(Utilisateur utilisateur){
        this.setNombre(0);
        this.setUtilisateur(utilisateur);
    }

    public TentativeConnection(int nombre){
        this.nombre=nombre;
    }
}
