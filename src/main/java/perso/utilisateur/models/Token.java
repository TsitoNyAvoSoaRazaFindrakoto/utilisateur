package perso.utilisateur.models;

import jakarta.persistence.*;
import lombok.*;
import perso.utilisateur.util.SecurityUtil;

import java.time.LocalDateTime;

@Entity
@Table(name = "token")
@Data
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
		this.setTokenValue(SecurityUtil.generateToken());
		this.setDateExpiration(LocalDateTime.now().plusHours(1));
	}

	public void updateToken(){
		this.setTokenValue(SecurityUtil.generateToken());
		this.setDateExpiration(LocalDateTime.now().plusHours(1));
	}
}
