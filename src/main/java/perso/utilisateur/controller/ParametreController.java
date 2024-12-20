package perso.utilisateur.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import perso.utilisateur.config.ParameterSercurity;
import perso.utilisateur.dto.LoginDTO;
import perso.utilisateur.dto.ParametreDTO;
import perso.utilisateur.dto.ResponseJSON;
import perso.utilisateur.services.PinService;

@RestController
@RequestMapping("/parametre")
public class ParametreController {
    private final ParameterSercurity parameterSercurity;

    public ParametreController(ParameterSercurity parameterSercurity) {
        this.parameterSercurity = parameterSercurity;
    }

    @Operation(
            summary = "Parametre de securite",
            description = "Modification de parametre de securite",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "mise a jours reussi",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseJSON.class))
                    )
            }
    )
    @PostMapping
    public ResponseJSON configureParam(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Parametre a modifier (a mettre null si on ne veut pas le modifier)",
            required = false,
            content = @Content(schema = @Schema(implementation = ParametreDTO.class))
    ) @org.springframework.web.bind.annotation.RequestBody ParametreDTO parametreDTO ){
        parametreDTO.updateAll(parameterSercurity);
        return new ResponseJSON("Modification des paramètre réussie",200,parametreDTO);
    }
}
