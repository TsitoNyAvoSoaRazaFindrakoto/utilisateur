package perso.utilisateur.models;

import jakarta.persistence.*;
import lombok.*;
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
    private Integer idUtilisateur;

    @Column(name = "pseudo", nullable = false)
    private String pseudo;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pin", referencedColumnName = "id_pin")
    private Pin pin;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_token", referencedColumnName = "id_token")
    private Token token;

    @OneToOne(optional = false,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_tentative_connection")
    private TentativeConnection tentativeConnection;

    public void setPin(){
        this.pin=new Pin(SecurityUtil.generatePin());
    }

    public void increaseNumberAttempt(){
        this.tentativeConnection.setNombre(this.tentativeConnection.getNombre()+1);
    }
}

