package perso.utilisateur.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_token")
	private Integer idToken;

	@Column(name = "token", nullable = false, unique = true)
	private String tokenValue;

	@Column(name = "date_expiration", nullable = false)
	private LocalDateTime dateExpiration;

	public Token(){
		this.setTokenValue();
	}
}
