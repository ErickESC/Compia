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
import mx.uam.tsinsoft.adoptPokemon.negocio.EspecialidadService;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Especialidad;

/**
 * Controlador para el API rest
 * 
 * @author erick
 *
 */
@RestController
@Slf4j
public class EspecialidadController {
	
	
	@Autowired
	private EspecialidadService especialidadService;
	
	/**
	 * 
	 * @param nuevaEspecialidad
	 * @return status creado y Especialidad creado, status bad request en caso contrario
	 */
	@ApiOperation(
			value = "Crear Especialidad",
			notes = "Permite crear una nueva Especialidad"
			)
	@PostMapping(path = "/especialidades", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Especialidad nuevaEspecialidad) {
		
		log.info("Recibí llamada a create con "+ nuevaEspecialidad);
		
		Especialidad especialidad = especialidadService.create(nuevaEspecialidad);
		
		if(especialidad != null) {
			log.info("Creado el grupo: "+ nuevaEspecialidad);
			return ResponseEntity.status(HttpStatus.CREATED).body(especialidad);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear especialidad");
		}
	}
	
	/**
	 * 
	 * @return status ok y lista de especialidades
	 */
	@ApiOperation(
			value = "Regresa todos las especialidades",
			notes = "Regresa un json con una lista de las especialidades en la BD"
			)
	@GetMapping(path = "/especialidades", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		Iterable <Especialidad> result = especialidadService.retriveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}

	/**
	 * 
	 * @param id
	 * @return status ok y grupo solicitado, not found en caso contrario
	 */
	@ApiOperation(
			value = "Regresa Especialidad",
			notes = "Regresa un json con los datos de la especialidad solicitada"
			)
	@GetMapping(path = "/especialidades/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("id") @Valid Integer id) {
		log.info("Buscando al alumno con matricula "+id);
		
		Optional<Especialidad> especialidad = especialidadService.retrive(id);
		
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
			value = "Actualiza Especialidad",
			notes = "Permite actualizar los datos de una especialidad existente en la DB"
			)
	@PutMapping(path = "/especialidades/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@RequestBody @Valid Especialidad especialidadActualizada) {
		
		log.info("Recibí llamada a update con "+ especialidadActualizada);
		
		Especialidad especialidad;
		
		//Revisa que exista en el repositorio de grupos
		if(especialidadService.exist(especialidadActualizada.getId())) {
			try {
				
				especialidad = especialidadService.update(especialidadActualizada);
				
				return ResponseEntity.status(HttpStatus.OK).body(especialidad);
				
			}catch(Exception e){
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al actualizar alumno");
			}
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No se encontro la especialidad con id: "+ especialidadActualizada.getId());
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return status no content, status conflic en caso de que algo haya salido mal, not found en caso de no encontrar el grupo
	 */
	@ApiOperation(
			value = "Borra Especialidad",
			notes = "Permite borrar una especialidad de la BD"
			)
	@DeleteMapping(path = "/especialidades/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> delete(@PathVariable("matricula") @Valid Integer id) {
		
		log.info("Recibí llamada a delete con "+ id);
		
		//Revisa que exista en el repositorio de alumnos
		if(especialidadService.delete(id)) {
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
	 * POST /grupos/id del grupo/pokemonss?id={1234}
	 * 
	 * @param nuevoGrupo
	 * @return 
	 */
	@ApiOperation(
			value = "Internar a un pokemon una especialidad",
			notes = "Permite Agregar un Pokemon a un Especialidad"
			)
	@PostMapping(path = "/especialidades/{espId}/pokemons/{pokemonId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> addPokemonToSpecialty(@PathVariable("espId") Integer id, @PathVariable("pokemonId") String pokemonID) {
		
		log.info("Recibí llamada a addPokemon con grupo"+ id +" y matricula: "+pokemonID);
		
		boolean result = especialidadService.addPokemonToSpecialty(id, pokemonID);
		
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
}
