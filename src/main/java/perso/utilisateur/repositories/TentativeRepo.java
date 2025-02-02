package perso.utilisateur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import perso.utilisateur.models.TentativeConnection;

import java.util.Optional;

@Repository
public interface TentativeRepo extends JpaRepository<TentativeConnection,Integer> {
    @Query("""
            select t from TentativeConnection t 
            where t.dateTentative in 
                (select max(t.dateTentative) 
                    from TentativeConnection t 
                    where t.utilisateur.idUtilisateur = :idUtilisateur)
            """)
    Optional<TentativeConnection> findLastAttemptConnectionUser(@Param("idUtilisateur")Integer idUtilisateur);
}
