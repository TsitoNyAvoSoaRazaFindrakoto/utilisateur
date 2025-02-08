package perso.utilisateur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import perso.utilisateur.models.Role;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Integer>{

    @Query("select r from Role r where r.roleName = :role")
    Optional<Role> findByRole(@Param("role") String role);
}
