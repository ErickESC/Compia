package mx.uam.tsinsoft.adoptPokemon.negocio;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.datos.EspecialidadRepository;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Especialidad;

/**
 * @author erick
 *
 */
@Slf4j
@Service
public class EspecialidadService {
	
	@Autowired
	private EspecialidadRepository especialidadRepository;
	@Autowired
	private PokemonService pokemonService;
	
	public Especialidad create(Especialidad nuevoGrupo) {
		
		try {
			return especialidadRepository.save(nuevoGrupo);
		}catch(Exception e) {
			e.getMessage();
			return null;
		}
	}
	
	/**
	 * 
	 * @return Lista de especialidades
	 */
	public Iterable <Especialidad> retriveAll(){
		return especialidadRepository.findAll();
	}
	
	/**
	 * 
	 * @param id
	 * @return especialidad
	 */
	public Optional<Especialidad> retrive(Integer id){
		
		Optional <Especialidad> grupoOpt = especialidadRepository.findById(id);
		
		if(grupoOpt.isPresent()) {
			return grupoOpt;
		}else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param especialidadActualizada
	 * @return Especialidad actualizada, null en caso de no haberse encontrado
	 */
	public Especialidad update(Especialidad especialidadActualizada){ 
		log.info("Llamado a update");
		Optional <Especialidad> especialidadOpt = especialidadRepository.findById(especialidadActualizada.getId());

		if(especialidadOpt.isPresent()) {
			return especialidadRepository.save(especialidadActualizada);
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
			especialidadRepository.deleteById(id);
			return true;
		}catch(Exception e){
			e.getMessage();
			return false;
		}
		
		
		
	}
	
	/**
	 * 
	 * @param matricula
	 * @return true si existe, false en caso contrario
	 */
	public boolean exist(Integer id){
		return especialidadRepository.existsById(id);
	}
	
	/**
	 * Permite agregar un alumno a un grupo
	 * 
	 * @param id del grupo
	 * @param pokemonId del alumno
	 * @return true si se agrego con exito, false en caso contrario
	 */
	public boolean addPokemonToSpecialty(Integer groupId, String pokemonId) {
		
		// 1.- Recuperamos primero al alumno
		Pokemon pokemon = pokemonService.retrive(pokemonId);
		
		// 2.- Recuperamos el grupo
		Optional <Especialidad> grupoOpt = especialidadRepository.findById(groupId);
		
		// 3.- Verificamos que el alumno y el grupo existan
		if(!grupoOpt.isPresent() || pokemon == null) {
			
			return false;
		}
		// 4.- Agrego el alumno al grupo
		Especialidad especialidad = grupoOpt.get();
		especialidad.addPokemon(pokemon);
		
		// 5.- Persistir el cambio
		especialidadRepository.save(especialidad);
		
		return true;
	}
}
