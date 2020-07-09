/**
 * 
 */
package mx.uam.tsinsoft.adoptPokemon.negocio;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.datos.EntrenadorRepository;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Entrenador;

/**
 * @author erick
 *
 */
@Service
@Slf4j
public class EntrenadorService {
	
	@Autowired
	private EntrenadorRepository entrenadorRepository;
	
	/**
	 * 
	 * @param nuevoEntrenador
	 * @return El entrenador recien creado, null de lo contrario
	 */
	public Entrenador create(Entrenador nuevoEntrenador) {
		log.info("Creando pokemon con matricula "+nuevoEntrenador.getId());
		//Regla de negocio: no se puede crear mas de un alumno con la misma matricula
		Optional <Entrenador> pokemonOpt = entrenadorRepository.findById(nuevoEntrenador.getId());
		
		if(!pokemonOpt.isPresent()) {
			log.info("Creado el entrenador con matricula "+nuevoEntrenador.getId());
			return entrenadorRepository.save(nuevoEntrenador);
		}else {
			log.info("El alumno ya existe");
			return null;
		}
	}
	
	/**
	 * 
	 * @return Lista de los entrenadores
	 */
	public Iterable <Entrenador> retriveAll(){
		log.info("Regresando arreglo con Entrenadores");
		return entrenadorRepository.findAll();
	}
	
	/**
	 * 
	 * @param Id
	 * @return Pokemon al que le pertenece la matricula
	 */
	public Entrenador retrive(Integer Id){
		log.info("Llamado a regresar al entrenador con matricula "+Id);
		
		Optional <Entrenador> entrenadorOpt = entrenadorRepository.findById(Id);
		
		if(entrenadorOpt.isPresent()) {
			return entrenadorOpt.get();
		}else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param entrenadorActualizado
	 * @return entrenador actualizado, null en caso de no haberse encontrado
	 */
	public Entrenador update(Entrenador entrenadorActualizado){ 
		
		log.info("Actualizando al pokemon con matricula "+entrenadorActualizado.getId());
		
		Optional<Entrenador> entrenadorOpt = entrenadorRepository.findById(entrenadorActualizado.getId());
		
		if(entrenadorOpt.isPresent()) {
			return entrenadorRepository.save(entrenadorActualizado);
		}else {
			log.info("El alumno no existe");
			return null;
		}
	}
	
	/**
	 * 
	 * @param Id
	 * @return verdadero si se borro bien o falso en caso contrario
	 */
	public boolean delete(Integer Id){
		
		log.info("Borrando pokemon con matricula "+Id);
		try {
			entrenadorRepository.deleteById(Id);
			return true;
		}catch(Exception e){
			log.info("Algo salio mal");
			e.getMessage();
			return false;
		}	
	}
	
	/**
	 * 
	 * @param Id
	 * @return true si existe, false en caso contrario
	 */
	public boolean exist(Integer Id){
		log.info("Revisando existencia del entrenador con matricula "+Id);
		return entrenadorRepository.existsById(Id);
	}
	
}
