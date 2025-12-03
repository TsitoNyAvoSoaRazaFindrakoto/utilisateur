package perso.utilisateur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perso.utilisateur.models.Token;
import perso.utilisateur.models.Utilisateur;

import java.util.Optional;
import java.util.List;

public interface UtilisateurRepo extends JpaRepository<Utilisateur, Integer> {
	@Query("select u from Utilisateur u where u.email = :email")
	public Optional<Utilisateur> findByEmail(@Param("email") String email);

	@Query("""
			select t.utilisateur from Token t where t.tokenValue = :tokenValue
			""")
	Optional<Utilisateur> findUtilisateurByToken(@Param("tokenValue") String tokenValue);

}
