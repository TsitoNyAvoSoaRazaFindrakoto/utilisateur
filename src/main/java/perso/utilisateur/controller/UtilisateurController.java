package perso.utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import perso.utilisateur.dto.InscriptionDTO;
import perso.utilisateur.dto.LoginDTO;
import perso.utilisateur.dto.PinLoginDTO;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.services.MailService;
import perso.utilisateur.services.UtilisateurService;
import perso.utilisateur.util.SecurityUtil;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {
    private UtilisateurService utilisateurService;

    @Autowired
    private MailService mailService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/login")
    public ResponseJSON login(@RequestBody LoginDTO loginDTO){
        return utilisateurService.login(loginDTO.getEmail(), loginDTO.getPassword());
    }

    @PostMapping("/login/pin")
    public ResponseJSON loginPin(@RequestBody PinLoginDTO pinLoginDTO){
        return utilisateurService.loginPin(pinLoginDTO.getPin(), pinLoginDTO.getIdUtilisateur());
    }

    @GetMapping("/email")
    public void email(){
        mailService.sendEmail();
    }

    @PostMapping("/inscription")
    public Utilisateur inscription(@RequestBody InscriptionDTO inscriptionDTO){
        Utilisateur utilisateur=new Utilisateur();
        utilisateur.setPseudo(inscriptionDTO.getPseudo());
        utilisateur.setEmail(inscriptionDTO.getEmail());
        utilisateur.setPassword(SecurityUtil.hashPassword(inscriptionDTO.getPassword()));
        return utilisateurService.save(utilisateur);
    }

}
