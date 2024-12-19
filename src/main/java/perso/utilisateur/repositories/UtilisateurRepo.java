package perso.utilisateur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perso.utilisateur.models.Token;
import perso.utilisateur.models.Utilisateur;

import java.util.Optional;
import java.util.List;

public interface UtilisateurRepo extends JpaRepository<Utilisateur, Integer> {
	@Query("select u from Utilisateur u inner join u.tentativeConnection t where u.email = :email")
	public Optional<Utilisateur> findByEmail(@Param("email") String email);

	List<Utilisateur> findByToken(Token token);

	@Query(value = "SELECT u.* " +
			"FROM utilisateur u " +
			"INNER JOIN token t ON u.id_token = t.id_token " +
			"WHERE t.token = :tokenValue", nativeQuery = true)
	Optional<Utilisateur> findUtilisateurByToken(@Param("tokenValue") String tokenValue);

}
