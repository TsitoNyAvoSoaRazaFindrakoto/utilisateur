package perso.utilisateur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import perso.utilisateur.models.Utilisateur;

public interface UtilisateurRepo extends JpaRepository<Utilisateur,Integer>{
	
}
