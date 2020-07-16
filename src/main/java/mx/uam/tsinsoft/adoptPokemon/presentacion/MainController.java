package mx.uam.tsinsoft.adoptPokemon.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.negocio.AdopcionService;
import mx.uam.tsinsoft.adoptPokemon.negocio.PokemonService;

/*
 * Controlador web de la capa de presentacion
 */


@Controller
@Slf4j
public class MainController {
	
	@Autowired
	private PokemonService pokemonService;
	
	@Autowired
	private AdopcionService adopcionService;
	
	@GetMapping("/")
	public String index() {
		
		log.info("se invoco el metodo index()");
		
		return "index";
	}
	
	@GetMapping("/adopcion")
	public String adopcion() {
		
		log.info("se invoco el metodo adopcion()");
		
		return "adopcion";
	}
	
	@GetMapping("/reclutamiento")
	public String reclutamiento() {
		
		log.info("se invoco el metodo reclutamiento()");
		
		return "reclutamiento";
	}
	
	@GetMapping("/registro")
	public String registro() {
		
		log.info("se invoco el metodo registro()");
		
		return "registro";
	}


}
