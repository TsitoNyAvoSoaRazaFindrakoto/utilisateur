package perso.utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import perso.utilisateur.config.ParameterSercurity;
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

    @PostMapping
    public ResponseJSON configureParam(@RequestBody ParametreDTO parametreDTO){
        parametreDTO.updateAll(parameterSercurity);
        return new ResponseJSON("Modification des paramètre réussie",200,parametreDTO);
    }
}
