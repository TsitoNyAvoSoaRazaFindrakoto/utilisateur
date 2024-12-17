package perso.utilisateur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import perso.utilisateur.models.Pin;

public interface PinRepo extends JpaRepository<Pin,Integer>{
	
}
