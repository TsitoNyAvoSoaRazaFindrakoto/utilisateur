package perso.utilisateur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perso.utilisateur.models.Token;
import perso.utilisateur.models.Utilisateur;

import java.util.List;
import java.util.Optional;


public interface TokenRepo extends JpaRepository<Token,Integer>{
	@Query("select t from Token t where t.dateCreation in (select max(t.dateCreation) from Token t where t.utilisateur.idUtilisateur = :idUtilisateur)")
	Optional<Token> findTokenByUtilisateur(@Param("idUtilisateur")Integer idUtilisateur);
}
