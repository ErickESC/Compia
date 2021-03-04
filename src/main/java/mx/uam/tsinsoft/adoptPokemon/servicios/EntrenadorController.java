package mx.uam.tsinsoft.adoptPokemon.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

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
import mx.uam.tsinsoft.adoptPokemon.negocio.EntrenadorService;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Entrenador;

/**
 * Controlador para el API rest
 * 
 * @author erick
 *
 */
@RestController
@Slf4j
public class EntrenadorController {
	
	
	@Autowired
	private EntrenadorService entrenadorService;
	
	/**
	 * 
	 * @param entrenadorGrupo
	 * @return status creado y Especialidad creado, status bad request en caso contrario
	 */
	@ApiOperation(
			value = "Crear nuevo entrenador",
			notes = "Permite crear un nuevo entrenador"
			)
	@PostMapping(path = "/entrenadores", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Entrenador entrenadorGrupo) {
		
		log.info("Recibi� llamada a create con "+ entrenadorGrupo);
		
		Entrenador grupo = entrenadorService.create(entrenadorGrupo);
		
		if(grupo != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(grupo);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear el Entrenador");
		}
	}
	
	/**
	 * 
	 * @return status ok y lista de entrenadores
	 */
	@ApiOperation(
			value = "Regresa todos los entrenadores",
			notes = "Regresa un json con una lista de los entrenadores en la BD"
			)
	@GetMapping(path = "/entrenadores", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Entrenador> result = entrenadorService.retriveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}

	/**
	 * 
	 * @param claved
	 * @return status ok y entrenador solicitado, not found en caso contrario
	 */
	@ApiOperation(
			value = "Regresa el entrenador",
			notes = "Regresa un json con los datos del entrenador solicitado"
			)
	@GetMapping(path = "/entrenadores/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("id") @Valid Integer id) {
		log.info("Buscando al entrenador con id "+id);
		
		Entrenador entrenador = entrenadorService.retrive(id);
		
		if(entrenador != null) {
			return ResponseEntity.status(HttpStatus.OK).body(entrenador);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro grupo de cuidado");
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return status ok y grupo actualizado, status no content en caso contrario, status conflict en caso de error
	 */
	@ApiOperation(
			value = "Actualiza entrenador",
			notes = "Permite actualizar los datos del entrenador existente en la DB"
			)
	@PutMapping(path = "/entrenadores/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@RequestBody @Valid Entrenador entrenadorActualizado) {
		
		log.info("Recibi� llamada a update con "+ entrenadorActualizado);
		
		Entrenador entrenador;
		
		//Revisa que exista en el repositorio de grupos
		if(entrenadorService.exist(entrenadorActualizado.getId())) {
			try {
				
				entrenador = entrenadorService.update(entrenadorActualizado);
				
				return ResponseEntity.status(HttpStatus.OK).body(entrenador);
				
			}catch(Exception e){
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al actualizar entrenador");
			}
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontro el entrenador con id: "+ entrenadorActualizado.getId());
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return status no content, status conflic en caso de que algo haya salido mal, not found en caso de no encontrar el grupo
	 */
	@ApiOperation(
			value = "Borra entrenador",
			notes = "Permite borrar un entrenador de la BD"
			)
	@DeleteMapping(path = "/entrenadores/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> delete(@PathVariable("id") @Valid Integer id) {
		
		log.info("Recibi� llamada a delete con "+ id);
		
		//Revisa que exista en el repositorio de alumnos
		if(entrenadorService.delete(id)) {
			try {
				
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				
			}catch(Exception e){
				e.fillInStackTrace();
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	/**
	 * POST /trainer/id del entrenador/pokemons/id={1234}
	 * 
	 * @param nuevoGrupo
	 * @return OK si se agrego con exito o No content en caso contrario
	 */
	@ApiOperation(
			value = "Agregar a un pokemon a un entrenador",
			notes = "Permite Agregar un Pokemon a un entrenador"
			)
	@PostMapping(path = "/entrenadores/add/{trainerId}/pokemons/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> addPokemonToTrainer(@PathVariable("trainerId") Integer id, @PathVariable("id") String pokemonID) {
		
		log.info("Recibi llamada a addPokemon con matricula: "+ id +" y matricula: "+pokemonID);
		
		boolean result = entrenadorService.addPokemonToTrainer(id, pokemonID);
		
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	/**
	 * 
	 * @param claved
	 * @return status ok y entrenador solicitado, not found en caso contrario
	 */
	@ApiOperation(
			value = "Regresa el documento con codigos postales",
			notes = "Regresa un json con el documento de los codigos postales"
			)
	@GetMapping(path = "/entrenadores/document", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve() throws FileNotFoundException, IOException {
		log.info("Regresando documento");
		
		List<String> document = entrenadorService.retriveDoc();
		
		if(document != null) {
			return ResponseEntity.status(HttpStatus.OK).body(document);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro grupo de cuidado");
		}
	}
}

