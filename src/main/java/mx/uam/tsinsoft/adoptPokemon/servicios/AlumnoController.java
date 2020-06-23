/**
 * 
 */
package mx.uam.tsinsoft.adoptPokemon.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sun.tools.sjavac.Log;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Alumno;

/**
 * @author erick
 *
 * Controlador para el API REST de alumno
 */
@RestController
@Slf4j
public class AlumnoController {
	
	//Simulamos la base de datos
	private Map <Integer, Alumno> alumnoRepository = new HashMap<>();
	
	/*
	 * Crea a los alumnos
	 */
	@PostMapping (path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody Alumno nuevoAlumno) {
		
		log.info("Recibi llamada a create con "+ nuevoAlumno);
		
		//Guarda el alumno de el repositorio de alumnos
		alumnoRepository.put(nuevoAlumno.getMatricula(), nuevoAlumno);
		
		//Regresa la respuesta CREATED
		return ResponseEntity.status(HttpStatus.CREATED).build(); 
	} 
	
	/*
	 * Regresa todos
	 */
	@GetMapping (path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?>retrieveall() {
		
		log.info("Se regresan todos los alumnos");
		//Regresa el repositorio de alumnos
		return ResponseEntity.status(HttpStatus.OK).body(alumnoRepository.values()); 
	}
	
	/*
	 * Regresa uno solo
	 * 
	 * "/alumnos/{matricula}" -> matricula se pone entre llaves porque es variable
	 */
	@GetMapping (path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("matricula") Integer matricula){
		
		log.info("Buscando al alumno con matricula "+matricula);
		
		Alumno alumno = alumnoRepository.get(matricula);
		
		if(alumno != null) {
			//Regresa el objeto solicitado
			return ResponseEntity.status(HttpStatus.OK).body(alumno); 
		}else {
			//Regresa un NOT_FOUND si no se encontro el objeto
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}
	

}
