package mx.uam.tsinsoft.adoptPokemon.servicios;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.negocio.PokemonService;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;

/**
 * Controlador para el API rest
 * 
 * @author erick
 *
 */
@RestController
@Slf4j
public class PokemonController {
	
	@Autowired
	private PokemonService pokemonService;
	
	
	/**
	 * 
	 * @param nuevoPokemon
	 * @return status creado y alumno crado, status bad request en caso contrario
	 */
	@ApiOperation(
			value = "Crear Pokemon",
			notes = "Permite crear un nuevo pokemon"
			)
	@PostMapping(path = "/pokemons", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Pokemon nuevoPokemon) {
		
		log.info("Recibí llamada a create con "+nuevoPokemon);
		
		Pokemon pokemon = pokemonService.create(nuevoPokemon);
		
		if(pokemon != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(pokemon);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear pokemon");
		}
	}
	
	/**
	 * 
	 * @return status ok y lista de alumnos
	 */
	@ApiOperation(
			value = "Regresa todos los Pokemons",
			notes = "Regresa un json con una lista de los Pokemons en la BD"
			)
	@GetMapping(path = "/pokemons", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Pokemon> result = pokemonService.retriveAll();
		
		log.info("pokemon info");
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}

	/**
	 * 
	 * @param pokemonId
	 * @return status ok y alumno solicitado, not found en caso contrario
	 */
	@ApiOperation(
			value = "Regresa Pokemon",
			notes = "Regresa un json con los datos del pokemon solicitado"
			)
	@GetMapping(path = "/pokemons/{pokemonId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("pokemonId") @Valid String pokemonId) {
		
		Pokemon pokemon = pokemonService.retrive(pokemonId);
		
		if(pokemon != null) {
			return ResponseEntity.status(HttpStatus.OK).body(pokemon);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro pokemon");
		}
	}
	
	/**
	 * 
	 * @param matricula
	 * @return status ok y alumno actualizado, status no content en caso contrario, status conflict en caso de error
	 */
	@ApiOperation(
			value = "Actualiza pokemon",
			notes = "Permite actualizar los datos de un pokemon existente en la DB"
			)
	@PutMapping(path = "/pokemons/{pokemonId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@RequestBody @Valid Pokemon pokemonActualizado) {
		
		log.info("Recibí llamada a update con "+ pokemonActualizado);
		
		Pokemon pokemon;
		
		//Revisa que exista en el repositorio de alumnos
		if(pokemonService.exist(pokemonActualizado.getPokemonId())) {
			try {
				
				pokemon = pokemonService.update(pokemonActualizado);
				
				return ResponseEntity.status(HttpStatus.OK).body(pokemon);
				
			}catch(Exception e){
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al actualizar pokemon");
			}
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontro al pokemon con matricula: "+ pokemonActualizado.getPokemonId());
		}
	}
	
	/**
	 * 
	 * @param pokemonId
	 * @return status no content, status conflic en caso de que algo haya salido mal, not found en caso de no encontrar al alumno
	 */
	@ApiOperation(
			value = "Borra Pokemon",
			notes = "Permite borrar un pokemon de la BD"
			)
	@DeleteMapping(path = "/pokemons/{pokemonId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> delete(@PathVariable("pokemonId") @Valid String pokemonId) {
		
		log.info("Recibí llamada a delete con "+ pokemonId);
		
		//Revisa que exista en el repositorio de alumnos
		if(pokemonService.delete(pokemonId)) {
			try {
				
				log.info("Se elimino el alumno con matricula: "+ pokemonId);
				
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				
			}catch(Exception e){
				e.fillInStackTrace();
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
