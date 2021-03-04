/**
 * 
 */
package mx.uam.tsinsoft.adoptPokemon.negocio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.datos.EntrenadorRepository;
import mx.uam.tsinsoft.adoptPokemon.datos.PokemonRepository;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Entrenador;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;

/**
 * @author erick
 *
 */
@Service
@Slf4j
public class EntrenadorService {
	
	@Autowired
	private EntrenadorRepository entrenadorRepository;
	
	@Autowired
	private AdopcionService adopcionService;
	
	@Autowired
	private PokemonService pokemonService;
	
	@Autowired
	private PokemonRepository pokemonRepository;
	
	/**
	 * 
	 * @param nuevoEntrenador
	 * @return El entrenador recien creado, null de lo contrario
	 */
	public Entrenador create(Entrenador nuevoEntrenador) {
		log.info("Creando Entrenador con matricula "+nuevoEntrenador.getId());
		return entrenadorRepository.save(nuevoEntrenador);

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
		
		log.info("Actualizando al Entrenador con matricula "+entrenadorActualizado.getId());
		
		Optional<Entrenador> entrenadorOpt = entrenadorRepository.findById(entrenadorActualizado.getId());
		
		if(entrenadorOpt.isPresent()) {
			
			entrenadorOpt.get().getSoyTrabajador().setRank(entrenadorActualizado.getSoyTrabajador().getRank());
			
			return entrenadorRepository.save(entrenadorOpt.get());
			
		}else {
			log.info("El Entrenador no existe");
			return null;
		}
	}
	
	/**
	 * 
	 * @param Id
	 * @return verdadero si se borro bien o falso en caso contrario
	 */
	public boolean delete(Integer Id){
		
		log.info("Borrando Entrenador con matricula "+Id);
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
	
	/**
	 * 
	 * @param trainerId
	 * @param pokemonId
	 * @return
	 */
	public boolean addPokemonToTrainer(Integer trainerId, String pokemonId) {
		
		Pokemon pokemon = pokemonService.retrive(pokemonId);
		
		log.info("Agregando pokemon con Id: "+pokemonId+" a entrenador con id: "+trainerId);
		
		//Se cambia el estatus del pokemon
		pokemon.setStatus("Adoptado");
		
		
		
		Optional <Entrenador> refactorOpt = entrenadorRepository.findById(trainerId);
		
		if(!refactorOpt.isPresent() || pokemon == null) {
			
			return false;
		}

		Entrenador trainer = refactorOpt.get();
		trainer.addPokemon(pokemon);
		
		pokemon.setEntrenador(trainer);
		
		//Retiramos al pokemon de la lista de adopcion
		adopcionService.quitPokemonFromAdoption(1, pokemonId);
		
		log.info("Entrenador contiene a pokemon: "+trainer.getPokemons().contains(pokemon));
		log.info("El entrenador del pokemon es: "+pokemon.getEntrenador().getId());
		// 5.- Persistir el cambio
		pokemonRepository.save(pokemon);
		entrenadorRepository.save(trainer);
		
		return true;
	}
	
	/**
	 * 
	 * @return Documento con codigos postales
	 */
	public List<String> retriveDoc() throws FileNotFoundException, IOException{
		log.info("Llamado a regresar a los codigos postales");
		
		List<String> document = new ArrayList<>();
		
		String cadena = new String();
		
		//Codigo que pasa un txt aun string
        FileReader f = new FileReader("C:\\Users\\eekos\\git\\adoptPokemons\\src\\main\\resources\\static\\docs\\CiudadDeMexico.txt");
        BufferedReader b = new BufferedReader(f);
        
        while((cadena = b.readLine())!=null) {
            document.add(cadena);
            log.info(""+cadena);
        }
        
        b.close();

		return document;
	}
}
