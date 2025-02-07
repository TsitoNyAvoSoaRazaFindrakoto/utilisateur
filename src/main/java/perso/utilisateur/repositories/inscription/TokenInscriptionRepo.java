package perso.utilisateur.repositories.inscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perso.utilisateur.models.inscription.TokenInscription;

import java.util.Optional;

public interface TokenInscriptionRepo extends JpaRepository<TokenInscription,Integer> {
    @Query("select t from TokenInscription t where t.dateCreation in (select max(t.dateCreation) from TokenInscription t where t.inscription.idInscription = :idInscription)")
    Optional<TokenInscription> findTokenByUtilisateur(@Param("idInscription") Integer idInscription);

    @Query("select t from TokenInscription t where t.tokenValue = :token")
    Optional<TokenInscription> findToken(@Param("token")String token);
}
