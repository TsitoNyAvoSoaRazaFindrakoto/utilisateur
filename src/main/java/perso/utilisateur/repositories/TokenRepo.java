package perso.utilisateur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import perso.utilisateur.models.Token;
import java.util.List;
import java.util.Optional;


public interface TokenRepo extends JpaRepository<Token,Integer>{
	Optional<Token> findByTokenValue(String tokenValue);
}
