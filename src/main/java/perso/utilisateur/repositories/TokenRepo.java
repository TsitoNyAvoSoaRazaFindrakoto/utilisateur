package perso.utilisateur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import perso.utilisateur.models.Token;

public interface TokenRepo extends JpaRepository<Token,Integer>{
	
}
