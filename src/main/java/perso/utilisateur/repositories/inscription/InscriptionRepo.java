package perso.utilisateur.repositories.inscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perso.utilisateur.models.inscription.Inscription;

import java.util.Optional;

public interface InscriptionRepo extends JpaRepository<Inscription,Integer> {
    @Query("select i from Inscription i where i.email = :email")
    Optional<Inscription> findInscriptionByEmail(@Param("email")String email);
}
