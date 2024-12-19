package perso.utilisateur.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import perso.utilisateur.dto.InscriptionDTO;
import perso.utilisateur.dto.LoginDTO;
import perso.utilisateur.dto.PinLoginDTO;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.models.Utilisateur;
import perso.utilisateur.services.InscriptionService;
import perso.utilisateur.services.MailService;
import perso.utilisateur.services.TokenService;
import perso.utilisateur.services.UtilisateurService;
import perso.utilisateur.util.SecurityUtil;

@RestController
@RequestMapping("/utilisateur")
@Tag(name = "Utilisateur", description = "API pour gérer les utilisateurs")
public class UtilisateurController {
	private InscriptionService inscriptionService;

    private MailService mailService;

    private final UtilisateurService utilisateurService;
    private final TokenService tokenService;



    public UtilisateurController(InscriptionService inscriptionService, MailService mailService, UtilisateurService utilisateurService, TokenService tokenService) {
        this.inscriptionService = inscriptionService;
        this.mailService = mailService;
        this.utilisateurService = utilisateurService;
        this.tokenService = tokenService;
    }


    @Operation(summary = "Connexion utilisateur", description = "Permet à un utilisateur de se connecter avec son email et son mot de passe", responses = {
			@ApiResponse(responseCode = "200", description = "Verification code PIN", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseJSON.class))),
			@ApiResponse(responseCode = "401", description = "Email non reconnue ou mot de passe incorrecte", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseJSON.class)))
	})
	@PostMapping("/login")
	public ResponseJSON login(
			@RequestBody(description = "Email et mot de passe", required = false, content = @Content(schema = @Schema(implementation = LoginDTO.class))) @org.springframework.web.bind.annotation.RequestBody LoginDTO loginDTO) {
		return utilisateurService.login(loginDTO.getEmail(), loginDTO.getPassword());
		// return new ResponseJSON("ok",200,loginDTO);
	}

	@Operation(
            summary = "Connexion utilisateur par PIN",
            description = "Permet à un utilisateur de se connecter avec un PIN",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Connexion réussie avec le PIN",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Code PIN invalide",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseJSON.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Code PIN expiré",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseJSON.class))
                    )
            }
    )
    @PostMapping("/login/pin")
    public ResponseJSON loginPin(@RequestBody(
            description = "Verification code PIN",
            required = false,
            content = @Content(schema = @Schema(implementation = PinLoginDTO.class))
    ) @org.springframework.web.bind.annotation.RequestBody PinLoginDTO pinLoginDTO) {
        return utilisateurService.loginPin(pinLoginDTO.getPin(), pinLoginDTO.getTokenUtilisateur());
    }

	@Operation(
            summary = "Inscription d'un utilisateur",
            description = "Enregistre un nouvel utilisateur et génère un token",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Utilisateur inscrit avec succès",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseJSON.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erreur de validation",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseJSON.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Email qui deja existe",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseJSON.class))
                    )
            }
    )
    @PostMapping("/inscription")
    public ResponseJSON inscription(@RequestBody(
			description = "Inscription d'une utilisateur",
			required = false,
			content = @Content(schema = @Schema(implementation = InscriptionDTO.class))
    ) @org.springframework.web.bind.annotation.RequestBody InscriptionDTO inscriptionDTO ){
			return inscriptionService.sendValidationMail(Utilisateur.from(inscriptionDTO));
    }

    @Operation(
            summary = "Modification des information d'un utilisateur",
            description = "Mise a jours de l' information d'un utilisateur",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Utilisateur inscrit avec succès",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseJSON.class))
                    )
            }
    )
    @PostMapping("/update")
    public ResponseJSON update(@RequestBody Utilisateur utilisateur){
    }

    @Operation(
            summary = "Recuperation d'une utilisateur",
            description = "Prendre les information d'une utilisateur",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Utilisateur bien recuperé",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseJSON.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Token expire",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseJSON.class))
                    )
            }
    )
    @GetMapping("/getUtilisateur")
    public ResponseJSON getUtilisateur(@Parameter(description = "Identifiant unique de l'utilisateur", example = "123")
                                           @PathVariable("idUtilisateur") int idUtilisateur){
        return utilisateurService.getByIdWithToken(idUtilisateur);
    }


    
}

