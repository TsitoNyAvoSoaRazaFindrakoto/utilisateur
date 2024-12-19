package perso.utilisateur.models;

import jakarta.persistence.*;
import lombok.*;
import perso.utilisateur.dto.InscriptionDTO;
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

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_pin", referencedColumnName = "id_pin")
	private Pin pin;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_token", referencedColumnName = "id_token")
	private Token token;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
	private Role role;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_tentative_connection", referencedColumnName = "id_tentative_connection")
	private TentativeConnection tentativeConnection;

	public static Utilisateur from(InscriptionDTO inscriptionDTO) {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPseudo(inscriptionDTO.getPseudo());
		utilisateur.setEmail(inscriptionDTO.getEmail());
		utilisateur.setPassword(SecurityUtil.hashPassword(inscriptionDTO.getPassword()));
		return utilisateur;
	}
}
