package perso.utilisateur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perso.utilisateur.dto.AuthentificationDTO;
import perso.utilisateur.models.Utilisateur;

import java.util.Optional;

public interface UtilisateurRepo extends JpaRepository<Utilisateur,Integer>{
    @Query("select u from Utilisateur u inner join u.tentativeConnection t where u.email = :email")
	public Optional<Utilisateur> findByEmail(@Param("email")String email);
}
