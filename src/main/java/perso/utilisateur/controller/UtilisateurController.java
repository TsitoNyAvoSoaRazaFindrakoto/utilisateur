package perso.utilisateur.controller;

import org.springframework.web.bind.annotation.*;
import perso.utilisateur.dto.InscriptionDTO;
import perso.utilisateur.dto.LoginDTO;
import perso.utilisateur.dto.PinLoginDTO;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.services.InscriptionService;
import perso.utilisateur.services.UtilisateurService;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {
    private UtilisateurService utilisateurService;
		private InscriptionService inscriptionService;

		public UtilisateurController(UtilisateurService utilisateurService, InscriptionService inscriptionService) {
			this.utilisateurService = utilisateurService;
			this.inscriptionService = inscriptionService;
		}

		@PostMapping("/login")
    public ResponseJSON login(@RequestBody LoginDTO loginDTO){
        return utilisateurService.login(loginDTO.getEmail(), loginDTO.getPassword());
        //return new ResponseJSON("ok",200,loginDTO);
    }

    @PostMapping("/login/pin")
    public ResponseJSON loginPin(@RequestBody PinLoginDTO pinLoginDTO){
        return utilisateurService.loginPin(pinLoginDTO.getPin(), pinLoginDTO.getTokenUtilisateur());
    }

    @PostMapping("/inscription")
    public ResponseJSON inscription(@RequestBody InscriptionDTO inscriptionDTO){
			return inscriptionService.sendValidationMail(Utilisateur.from(inscriptionDTO));
    }
}
