package perso.utilisateur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perso.utilisateur.models.Pin;

import java.util.Optional;

public interface PinRepo extends JpaRepository<Pin,Integer>{

    @Query("""
            select p from Pin p where p.dateCreation in (select max(p.dateCreation) from Pin p where p.utilisateur.idUtilisateur = :idUtilisateur)
            """)
    Optional<Pin> findPinUtilisateur(@Param("idUtilisateur")Integer idUtilisateur);
}
