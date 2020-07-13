/**
 * 
 */
package mx.uam.tsinsoft.adoptPokemon.negocio;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.datos.PokemonRepository;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;

/**
 * @author erick
 *
 */
@Service
@Slf4j
public class PokemonService {
	
	@Autowired
	private PokemonRepository pokemonRepository;
	
	/**
	 * 
	 * @param nuevoPokemon
	 * @return El alumno recien creado, null de lo contrario
	 */
	public Pokemon create(Pokemon nuevoPokemon) {
		log.info("Creando pokemon con matricula "+nuevoPokemon.getPokemonId());
		//Regla de negocio: no se puede crear mas de un alumno con la misma matricula
		Optional <Pokemon> pokemonOpt = pokemonRepository.findById(nuevoPokemon.getPokemonId());
		
		if(!pokemonOpt.isPresent()) {
			log.info("Creado el pokemon con matricula "+nuevoPokemon.getPokemonId());
			String idCompleto = nuevoPokemon.getPokemonId().concat(Integer.toString((int)Math.floor(Math.random()*100)));
			nuevoPokemon.setPokemonId(idCompleto);
			return pokemonRepository.save(nuevoPokemon);
		}else {
			log.info("El alumno ya existe");
			return null;
		}
	}
	
	/**
	 * 
	 * @return Lista de los alumnos
	 */
	public Iterable <Pokemon> retriveAll(){
		log.info("Regresando arreglo con pokemons");
		return pokemonRepository.findAll();
	}
	
	/**
	 * 
	 * @param Id
	 * @return Pokemon al que le pertenece la matricula
	 */
	public Pokemon retrive(String Id){
		log.info("Llamado a regresar al a pokemon con matricula "+Id);
		
		Optional <Pokemon> alumnoOpt = pokemonRepository.findById(Id);
		
		if(alumnoOpt.isPresent()) {
			return alumnoOpt.get();
		}else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param pokemonActualizado
	 * @return Pokemon actualizado, null en caso de no haberse encontrado
	 */
	public Pokemon update(Pokemon pokemonActualizado){ 
		
		log.info("Actualizando al pokemon con matricula "+pokemonActualizado.getPokemonId());
		
		Optional <Pokemon> pokemonOpt = pokemonRepository.findById(pokemonActualizado.getPokemonId());
		
		if(pokemonOpt.isPresent()) {
			
			if((pokemonActualizado.getInformation() == null) && (pokemonOpt.get().getInformation() != null)) {
				pokemonActualizado.setInformation(pokemonOpt.get().getInformation());
			}
			if((pokemonActualizado.getEntrenador() == null) && (pokemonOpt.get().getEntrenador() != null)) {
				pokemonActualizado.setEntrenador(pokemonOpt.get().getEntrenador());
			}
			
			return pokemonRepository.save(pokemonActualizado);
			
		}else {
			log.info("El pokemon no existe");
			return null;
		}
	}
	
	/**
	 * 
	 * @param Id
	 * @return verdadero si se borro bien o falso en caso contrario
	 */
	public boolean delete(String Id){
		
		log.info("Borrando pokemon con matricula "+Id);
		try {
			pokemonRepository.deleteById(Id);
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
	public boolean exist(String Id){
		log.info("Revisando existencia del pokemon con matricula "+Id);
		return pokemonRepository.existsById(Id);
	}
	
}
