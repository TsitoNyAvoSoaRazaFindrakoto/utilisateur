package perso.utilisateur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import perso.utilisateur.models.TentativeConnection;

@Repository
public interface TentativeRepo extends JpaRepository<TentativeConnection,Long> {
}
