package perso.utilisateur.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "tentative_connection")
@Data
@Entity
public class TentativeConnection {
    @Id
    @Column(name = "id_tentative_connection")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTentativeConnection;

    private int nombre;


    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE,mappedBy = "tentativeConnection")
    private List<Utilisateur> utilisateur;

    public TentativeConnection(){
        this.setNombre(0);
    }

    public TentativeConnection(int nombre){
        this.nombre=nombre;
    }
}
