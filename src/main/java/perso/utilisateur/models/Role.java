package perso.utilisateur.models;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import perso.utilisateur.other.POV;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_role")
	@JsonView(POV.Public.class)
	private Integer idRole;

	@Column(name = "role", nullable = false, unique = true)
	private String roleName;
}
