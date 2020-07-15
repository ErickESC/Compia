package mx.uam.tsinsoft.adoptPokemon.servicios;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Especialidad;
import mx.uam.tsinsoft.adoptPokemon.datos.PokemonRepository;
import mx.uam.tsinsoft.adoptPokemon.datos.EspecialidadRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * 
 * Prueba de integración para el endpoint alumnos
 * 
 * @author erick
 *
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EspecialidadControllerIntegrationTest {
	
	@Autowired
	private PokemonRepository pokemonRepository;
	
	@Autowired
	private EspecialidadRepository especialidadRepository;
	
	@BeforeEach
	public void prepare() {
		Especialidad grupo = new Especialidad();
		grupo.setId(10);
		grupo.setClave("TST");
		grupo.setNombre("Enfermitos");
		
		especialidadRepository.save(grupo);
	}
	
	@AfterEach
	public void destroy() {
		log.info("DESTRUYENDO");
		especialidadRepository.deleteAll();
		pokemonRepository.deleteAll();
	}
	
	/*
	 * PRUEBAS PARA CREATE
	 */
	

	
	/**
	 * PRUEBAS PARA ADD POKEMON TO SPECIALTY
	 */
}
