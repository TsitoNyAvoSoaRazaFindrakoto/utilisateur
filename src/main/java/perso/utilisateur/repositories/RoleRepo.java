package perso.utilisateur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import perso.utilisateur.models.Role;

public interface RoleRepo extends JpaRepository<Role,Integer>{
	
}
