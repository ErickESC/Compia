package mx.uam.tsinsoft.adoptPokemon.negocio;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.datos.CuidadoRepository;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Cuidado;

/**
 * @author erick
 *
 */
@Slf4j
@Service
public class CuidadoService {
	
	@Autowired
	private CuidadoRepository cuidadoRepository;
	@Autowired
	private PokemonService pokemonService;
	
	public Cuidado create(Cuidado nuevoCare) {
		
		try {
			return cuidadoRepository.save(nuevoCare);
		}catch(Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	/**
	 * 
	 * @return Lista de grupos de cuidado
	 */
	public Iterable<Cuidado> retriveAll(){
		return cuidadoRepository.findAll();
	}
	
	/**
	 * 
	 * @param id
	 * @return grupo de cuidado
	 */
	public Optional<Cuidado> retrive(Integer id){
		
		Optional<Cuidado> grupoOpt = cuidadoRepository.findById(id);
		
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
	public Cuidado update(Cuidado grupoActualizado){ 
		log.info("Llamado a update");
		Optional<Cuidado> especialidadOpt = cuidadoRepository.findById(grupoActualizado.getId());

		if(especialidadOpt.isPresent()) {
			return cuidadoRepository.save(grupoActualizado);
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
			cuidadoRepository.deleteById(id);
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
		return cuidadoRepository.existsById(id);
	}
	
	/**
	 * Permite agregar un alumno a un grupo
	 * 
	 * @param id del grupo
	 * @param pokemonId
	 * @return true si se agrego con exito, false en caso contrario
	 */
	public boolean addPokemonToCare(Integer groupId, String pokemonId) {
		
		Pokemon pokemon = pokemonService.retrive(pokemonId);
		
		Optional <Cuidado> grupoOpt = cuidadoRepository.findById(groupId);
		
		if(!grupoOpt.isPresent() || pokemon == null) {
			
			return false;
		}

		Cuidado grupo = grupoOpt.get();
		grupo.addPokemon(pokemon);
		
		cuidadoRepository.save(grupo);
		
		return true;
	}
}
