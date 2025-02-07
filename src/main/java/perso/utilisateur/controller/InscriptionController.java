package perso.utilisateur.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.services.InscriptionService;

@Controller
@AllArgsConstructor
@RequestMapping("/inscription")
public class InscriptionController {
    private InscriptionService inscriptionService;

    @GetMapping("/validation/{tokenInscription}")
    public String requestPin(@PathVariable("tokenInscription")String tokenInscription, Model model){
        return inscriptionService.validateInscription(tokenInscription,model);
    }
}
