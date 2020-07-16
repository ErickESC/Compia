package mx.uam.tsinsoft.adoptPokemon.negocio;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.datos.AdopcionRepository;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Adopcion;

/**
 * @author erick
 *
 */
@Slf4j
@Service
public class AdopcionService {
	
	@Autowired
	private AdopcionRepository adopcionRepository;
	@Autowired
	private PokemonService pokemonService;
	
	public Adopcion create(Adopcion nuevoGrupo) {
		
		try {
			return adopcionRepository.save(nuevoGrupo);
		}catch(Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	/**
	 * 
	 * @return Lista de grupos de adopcion
	 */
	public Iterable<Adopcion> retriveAll(){
		return adopcionRepository.findAll();
	}
	
	/**
	 * 
	 * @param id
	 * @return grupo de adopcion
	 */
	public Optional<Adopcion> retrive(Integer id){
		
		Optional<Adopcion> grupoOpt = adopcionRepository.findById(id);
		
		if(grupoOpt.isPresent()) {
			return grupoOpt;
		}else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param grupoActualizado
	 * @return grupo actualizado, null en caso de no haberse encontrado
	 */
	public Adopcion update(Adopcion grupoActualizado){ 
		log.info("Llamado a update");
		Optional<Adopcion> especialidadOpt = adopcionRepository.findById(grupoActualizado.getId());

		if(especialidadOpt.isPresent()) {
			return adopcionRepository.save(grupoActualizado);
		}else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return true
	 */
	public boolean delete(Integer id){

		try {
			adopcionRepository.deleteById(id);
			return true;
		}catch(Exception e){
			e.getMessage();
			return false;
		}
		
		
		
	}
	
	/**
	 * 
	 * @param id del grupo
	 * @return true si existe, false en caso contrario
	 */
	public boolean exist(Integer id){
		return adopcionRepository.existsById(id);
	}
	
	/**
	 * Permite agregar un alumno a un grupo
	 * 
	 * @param id del grupo
	 * @param pokemonId
	 * @return true si se agrego con exito, false en caso contrario
	 */
	public boolean addPokemonToAdoption(Integer groupId, String pokemonId) {
		
		Pokemon pokemon = pokemonService.retrive(pokemonId);
		
		Optional <Adopcion> grupoOpt = adopcionRepository.findById(groupId);
		
		if(!grupoOpt.isPresent() || pokemon == null) {
			log.info("Regreso false--- grupo: "+grupoOpt.isPresent()+" pokemon: "+pokemon.getPokemonId());
			return false;
		}

		Adopcion grupo = grupoOpt.get();
		grupo.addPokemon(pokemon);
		
		adopcionRepository.save(grupo);
		log.info("Guardo pokemon");
		return true;
	}
	
	/**
	 * 
	 * @param groupId
	 * @param pokemonId
	 * @return true si se retiro con exito
	 */
	public boolean quitPokemonFromAdoption(Integer groupId, String pokemonId) {
		
		// 1.- Recuperamos primero al pokemon
		Pokemon pokemon = pokemonService.retrive(pokemonId);
		
		// 2.- Recuperamos el grupo
		Optional <Adopcion> grupoOpt = adopcionRepository.findById(groupId);
		
		// 3.- Verificamos que el pokemon y grupo existan
		if(!grupoOpt.isPresent() || pokemon == null) {
			
			return false;
		}
		// 4.- retiro al pokemon de la especialidad
		Adopcion grupo = grupoOpt.get();
		grupo.quitPokemon(pokemon);
		
		pokemon.setStatus("undefined");
		
		// 5.- Persistir el cambio
		adopcionRepository.save(grupo);
		pokemonService.update(pokemon);
		
		return true;
	}
}
