package perso.utilisateur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import perso.utilisateur.models.Token;
import java.util.List;


public interface TokenRepo extends JpaRepository<Token,Integer>{
	List<Token> findByTokenValue(String tokenValue);
}
