package perso.utilisateur.services;

import org.springframework.stereotype.Service;
import perso.utilisateur.models.Role;
import perso.utilisateur.repositories.RoleRepo;

@Service
public class RoleService {
    private RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Role findById(Integer idRole){
        return roleRepo.findById(idRole).orElseThrow(()->new RuntimeException("Id role non existante"));
    }
}
