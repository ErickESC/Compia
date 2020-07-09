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
import mx.uam.tsinsoft.adoptPokemon.negocio.AdopcionService;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Adopcion;

/**
 * Controlador para el API rest
 * 
 * @author erick
 *
 */
@RestController
@Slf4j
public class AdopcionController {
	
	
	@Autowired
	private AdopcionService adopcionService;
	
	/**
	 * 
	 * @param nuevoGrupo
	 * @return status creado y Especialidad creado, status bad request en caso contrario
	 */
	@ApiOperation(
			value = "Crear nuevo grupo de adopcion",
			notes = "Permite crear un nuevo grupo de adopcion"
			)
	@PostMapping(path = "/adopciones", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Adopcion nuevoGrupo) {
		
		log.info("Recibí llamada a create con "+ nuevoGrupo);
		
		Adopcion grupo = adopcionService.create(nuevoGrupo);
		
		if(grupo != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(grupo);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear alumno");
		}
	}
	
	/**
	 * 
	 * @return status ok y lista de especialidades
	 */
	@ApiOperation(
			value = "Regresa todos los grupos de adopcion",
			notes = "Regresa un json con una lista de los grupos de adopcion en la BD"
			)
	@GetMapping(path = "/adopciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Adopcion> result = adopcionService.retriveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}

	/**
	 * 
	 * @param claved
	 * @return status ok y grupo solicitado, not found en caso contrario
	 */
	@ApiOperation(
			value = "Regresa Especialidad",
			notes = "Regresa un json con los datos de la especialidad solicitada"
			)
	@GetMapping(path = "/adopciones/{clave}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("clave") @Valid Integer clave) {
		log.info("Buscando al grupo con clave "+clave);
		
		Optional<Adopcion> especialidad = adopcionService.retrive(clave);
		
		if(especialidad != null) {
			return ResponseEntity.status(HttpStatus.OK).body(especialidad);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro especialidad");
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return status ok y grupo actualizado, status no content en caso contrario, status conflict en caso de error
	 */
	@ApiOperation(
			value = "Actualiza grupo de adopcion",
			notes = "Permite actualizar los datos de un grupo de adopcion existente en la DB"
			)
	@PutMapping(path = "/adopciones/{clave}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@RequestBody @Valid Adopcion grupoActualizado) {
		
		log.info("Recibí llamada a update con "+ grupoActualizado);
		
		Adopcion grupo;
		
		//Revisa que exista en el repositorio de grupos
		if(adopcionService.exist(grupoActualizado.getId())) {
			try {
				
				grupo = adopcionService.update(grupoActualizado);
				
				return ResponseEntity.status(HttpStatus.OK).body(grupo);
				
			}catch(Exception e){
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al actualizar grupo");
			}
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontro el grupo con id: "+ grupoActualizado.getId());
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return status no content, status conflic en caso de que algo haya salido mal, not found en caso de no encontrar el grupo
	 */
	@ApiOperation(
			value = "Borra grupo de adopcion",
			notes = "Permite borrar un grupo de adopcion de la BD"
			)
	@DeleteMapping(path = "/adopciones/{clave}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> delete(@PathVariable("clave") @Valid Integer id) {
		
		log.info("Recibí llamada a delete con "+ id);
		
		//Revisa que exista en el repositorio de alumnos
		if(adopcionService.delete(id)) {
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
			value = "Internar a un pokemon una especialidad",
			notes = "Permite Agregar un Pokemon a un Especialidad"
			)
	@PostMapping(path = "/adopciones/{clave}/pokemons/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> addPokemonToSpecialty(@PathVariable("clave") Integer id, @PathVariable("id") String pokemonID) {
		
		log.info("Recibí llamada a addStudent con grupo"+ id +" y matricula: "+pokemonID);
		
		boolean result = adopcionService.addPokemonToAdoption(id, pokemonID);
		
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
}