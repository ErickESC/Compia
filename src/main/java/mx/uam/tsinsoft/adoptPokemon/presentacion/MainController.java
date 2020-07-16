package mx.uam.tsinsoft.adoptPokemon.presentacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.datos.AdopcionRepository;
import mx.uam.tsinsoft.adoptPokemon.negocio.AdopcionService;
import mx.uam.tsinsoft.adoptPokemon.negocio.PokemonService;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Adopcion;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;

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
	
	@Autowired
	private AdopcionRepository adopcionRepository;
	
	@GetMapping("/")
	public String index() {
		
		log.info("se invoco el metodo index()");
		
		List <Pokemon> pokemons = pokemonService.rellena();
		
		Adopcion nuevoGrupo = new Adopcion();
		nuevoGrupo.setClave("ADPN001");
		nuevoGrupo.setId(1);
		nuevoGrupo.setNombre("Adopcion");
		
		adopcionRepository.save(nuevoGrupo);
		
		for(int i=0; i<pokemons.size(); i++) {
			if(pokemons.get(i).getStatus() == "adopcion") {
				adopcionService.addPokemonToAdoption(1, pokemons.get(i).getPokemonId());
			}
		}
		
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
