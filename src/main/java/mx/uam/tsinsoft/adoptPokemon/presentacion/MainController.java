package mx.uam.tsinsoft.adoptPokemon.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import lombok.extern.slf4j.Slf4j;

/*
 * Controlador web de la capa de presentacion
 */

@Controller
@Slf4j
public class MainController {
	
	@GetMapping("/")
	public String index() {
		
		log.info("se invoco el metodo index()");
		
		return "index";
	}

}
