package mx.uam.tsinsoft.adoptPokemon.presentacion;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sun.tools.sjavac.Log;

import lombok.extern.slf4j.Slf4j;

/*
 * Controlador web de la capa de presentacion
 */

@Controller
@Slf4j
public class ControladorPrincipal {
	
	Logger logger = Logger.getLogger("logger");
	
	@GetMapping("/")
	public String index() {
		
		logger.info("se invoco el metodo index()");
		
		return "index";
	}

}
