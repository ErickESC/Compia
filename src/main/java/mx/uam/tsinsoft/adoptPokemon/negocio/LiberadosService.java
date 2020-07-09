package mx.uam.tsinsoft.adoptPokemon.negocio;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.datos.LiberadosRepository;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Liberados;

/**
 * @author erick
 *
 */
@Slf4j
@Service
public class LiberadosService {
	
	@Autowired
	private LiberadosRepository liberadosRepository;
	@Autowired
	private PokemonService pokemonService;
	
	/**
	 * 
	 * @param nuevoGrupo
	 * @return el grupo de liberados creado
	 */
	public Liberados create(Liberados nuevoGrupo) {
		
		try {
			return liberadosRepository.save(nuevoGrupo);
		}catch(Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	/**
	 * 
	 * @return Lista de grupos de adopcion
	 */
	public Iterable<Liberados> retriveAll(){
		return liberadosRepository.findAll();
	}
	
	/**
	 * 
	 * @param id
	 * @return grupo de liberados
	 */
	public Optional<Liberados> retrive(String id){
		
		Optional<Liberados> grupoOpt = liberadosRepository.findById(id);
		
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
	public Liberados update(Liberados grupoActualizado){ 
		log.info("Llamado a update");
		Optional<Liberados> especialidadOpt = liberadosRepository.findById(grupoActualizado.getClave());

		if(especialidadOpt.isPresent()) {
			return liberadosRepository.save(grupoActualizado);
		}else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return true
	 */
	public boolean delete(String id){

		try {
			liberadosRepository.deleteById(id);
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
	public boolean exist(String id){
		return liberadosRepository.existsById(id);
	}
	
	/**
	 * Permite agregar un pokemon a un grupo de liberados
	 * 
	 * @param id del grupo
	 * @param pokemonId
	 * @return true si se agrego con exito, false en caso contrario
	 */
	public boolean addPokemonToFreedom(String groupId, String pokemonId) {
		
		Pokemon pokemon = pokemonService.retrive(pokemonId);
		
		Optional <Liberados> grupoOpt = liberadosRepository.findById(groupId);
		
		if(!grupoOpt.isPresent() || pokemon == null) {
			
			return false;
		}

		Liberados grupo = grupoOpt.get();
		grupo.addPokemon(pokemon);
		
		liberadosRepository.save(grupo);
		
		return true;
	}
}
