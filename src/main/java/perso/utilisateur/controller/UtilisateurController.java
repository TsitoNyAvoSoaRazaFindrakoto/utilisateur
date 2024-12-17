package perso.utilisateur.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/login")
    public ResponseJSON login(@ModelAttribute LoginDTO loginDTO){
        return utilisateurService.testLogin(loginDTO.getEmail(), loginDTO.getPassword());
    }
}
