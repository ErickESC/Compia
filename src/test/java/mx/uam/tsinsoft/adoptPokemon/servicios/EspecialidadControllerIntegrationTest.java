package mx.uam.tsinsoft.adoptPokemon.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Especialidad;
import mx.uam.tsinsoft.adoptPokemon.datos.PokemonRepository;
import mx.uam.tsinsoft.adoptPokemon.datos.EspecialidadRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
	private TestRestTemplate restTemplate;
	
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
