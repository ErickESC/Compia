package mx.uam.tsinsoft.adoptPokemon.servicios;

import java.util.Optional;

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
import mx.uam.tsinsoft.adoptPokemon.negocio.LiberadosService;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Liberados;

/**
 * Controlador para el API rest
 * 
 * @author erick
 *
 */
@RestController
@Slf4j
public class LiberadosController {
	
	
	@Autowired
	private  LiberadosService liberadosService;
	
	/**
	 * 
	 * @param nuevoGrupo
	 * @return status creado y Especialidad creado, status bad request en caso contrario
	 */
	@ApiOperation(
			value = "Crear nuevo grupo de liberacion",
			notes = "Permite crear un nuevo grupo de liberacion"
			)
	@PostMapping(path = "/liberados", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid  Liberados nuevoGrupo) {
		
		log.info("Recibí llamada a create con "+ nuevoGrupo);
		
		 Liberados grupo = liberadosService.create(nuevoGrupo);
		
		if(grupo != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(grupo);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear el grupo");
		}
	}
	
	/**
	 * 
	 * @return status ok y lista de especialidades
	 */
	@ApiOperation(
			value = "Regresa todos los grupos de liberacion",
			notes = "Regresa un json con una lista de los grupos de liberacion en la BD"
			)
	@GetMapping(path = "/liberados", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable < Liberados> result = liberadosService.retriveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}

	/**
	 * 
	 * @param claved
	 * @return status ok y grupo solicitado, not found en caso contrario
	 */
	@ApiOperation(
			value = "Regresa el grupo de liberacion",
			notes = "Regresa un json con los datos de grupo de liberacion solicitado"
			)
	@GetMapping(path = "/liberados/{clave}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("clave") @Valid String clave) {
		log.info("Buscando al grupo con clave "+clave);
		
		Optional< Liberados> especialidad = liberadosService.retrive(clave);
		
		if(especialidad != null) {
			return ResponseEntity.status(HttpStatus.OK).body(especialidad);
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
			value = "Actualiza grupo de liberacion",
			notes = "Permite actualizar los datos de un grupo de liberacion existente en la DB"
			)
	@PutMapping(path = "/liberados/{clave}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@RequestBody @Valid Liberados grupoActualizado) {
		
		log.info("Recibí llamada a update con "+ grupoActualizado);
		
		Liberados grupo;
		
		//Revisa que exista en el repositorio de grupos
		if(liberadosService.exist(grupoActualizado.getClave())) {
			try {
				
				grupo = liberadosService.update(grupoActualizado);
				
				return ResponseEntity.status(HttpStatus.OK).body(grupo);
				
			}catch(Exception e){
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al actualizar grupo");
			}
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontro el grupo con id: "+ grupoActualizado.getClave());
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return status no content, status conflic en caso de que algo haya salido mal, not found en caso de no encontrar el grupo
	 */
	@ApiOperation(
			value = "Borra grupo de liberacion",
			notes = "Permite borrar un grupo de cuidado de la BD"
			)
	@DeleteMapping(path = "/liberados/{clave}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> delete(@PathVariable("clave") @Valid String id) {
		
		log.info("Recibí llamada a delete con "+ id);
		
		//Revisa que exista en el repositorio de alumnos
		if(liberadosService.delete(id)) {
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
	 * POST /grupos/id del grupo/pokemons?id={1234}
	 * 
	 * @param nuevoGrupo
	 * @return OK si se agrego con exito o No content en caso contrario
	 */
	@ApiOperation(
			value = "Agregar a un pokemon un grupo de liberados",
			notes = "Permite Agregar un Pokemon a un grupo de adopcion"
			)
	@PostMapping(path = "/liberados/add/{clave}/pokemons/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> addPokemonToCare(@PathVariable("clave") String id, @PathVariable("id") String pokemonID) {
		
		log.info("Recibí llamada a addPokemon con grupo"+ id +" y matricula: "+pokemonID);
		
		boolean result = liberadosService.addPokemonToFreedom(id, pokemonID);
		
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
}
