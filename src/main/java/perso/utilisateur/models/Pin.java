package perso.utilisateur.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pin")
	private Integer idPin;

	@Column(name = "pin", nullable = false)
	private String pinValue;

	public Pin(String pinValue){
		this.setPinValue(pinValue);
	}
}
