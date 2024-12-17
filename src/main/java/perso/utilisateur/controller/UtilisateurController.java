package perso.utilisateur.controller;

import org.springframework.web.bind.annotation.*;
import perso.utilisateur.dto.LoginDTO;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.services.UtilisateurService;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {
    private UtilisateurService utilisateurService;
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/login")
    public ResponseJSON login(@ModelAttribute LoginDTO loginDTO){
        return utilisateurService.login(loginDTO.getEmail(), loginDTO.getPassword());
    }
}
