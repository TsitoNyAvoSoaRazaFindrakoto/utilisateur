package perso.utilisateur.models;

import jakarta.persistence.*;
import lombok.*;
import perso.utilisateur.listener.PinListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "pin")
@Data
@AllArgsConstructor
@EntityListeners(PinListener.class)
public class Pin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pin")
	private Integer idPin;

	@Column(name = "pin", nullable = false)
	private String pinValue;

	@Column(name = "date_expiration",nullable = false)
	private LocalDateTime dateExpiration;

	private LocalDateTime dateCreation;

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utilisateur")
	private Utilisateur utilisateur;

	public Pin(){

	}

	public Pin(String pinValue,Utilisateur utilisateur){
		this.setPinValue(pinValue);
		this.setUtilisateur(utilisateur);
	}
}
