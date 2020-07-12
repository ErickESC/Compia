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

import java.util.Optional;

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
		log.info("COMIENZA PREPARE");
		// Aqui se puede hacer cosas para preparar sus casos de prueba, incluyendo
		// agregar datos a la BD
		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("SquirtleDePruebas");
		pokemon.setStatus("SoloSolinSolito");

		Especialidad especialidad = new Especialidad();
		especialidad.setId(1);
		especialidad.setClave("Enfermitos");
		
		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Pokemon> request = new HttpEntity <> (pokemon, headers);

		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Especialidad> request2 = new HttpEntity <> (especialidad, headers);
		
		restTemplate.exchange("/pokemons", HttpMethod.POST, request, Pokemon.class);
		
		restTemplate.exchange("/especialidades", HttpMethod.POST, request2, Especialidad.class);
	}
	
	@AfterEach
	public void destroy() {
		// Aqui se puede hacer cosas para deshacer lo que se realizo antes de los casos de prueba
		// Elimino al al alumno
		String pokemonId = "SquirtleDePruebas";
		
		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <String> request = new HttpEntity <> (pokemonId, headers);
		
		restTemplate.exchange("/pokemons/"+pokemonId, HttpMethod.DELETE, request, String.class);
	}
	
	/*
	 * PRUEBAS PARA CREATE
	 */
	
	@Test
	public void testaddPokemonToSpecialty200() {
		
		String pokemonId = "SquirtleDePruebas";
		
		int id = 1;

		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		HttpEntity <String> request = new HttpEntity <> (pokemonId, headers);
		
		ResponseEntity <String> responseEntity = restTemplate.exchange("/especialidades/"+id+"/pokemons/"+pokemonId, HttpMethod.POST, request, String.class);
		
		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		// Corroboro que en la base de datos se actualizo el grupo con el nuevo alumno
		Optional <Especialidad> optEspecialidad = especialidadRepository.findById(id);
		Optional <Pokemon> optPokemon = pokemonRepository.findById(pokemonId);
		
		//Si la lista de alumnos del grupo contiene al alumno entonces la prueba es exitosa
		// NOTA: Se tuvo que utilizar una recuperacion EAGER ya que si se ocupaba LAZY no funcionaria
		assertEquals(true,optEspecialidad.get().getPokemons().contains(optPokemon.get()));
	}
	
	@Test
	public void testaddPokemonToSpecialty204() {
		/**
		 * CAMBIANDO CUALQUIERA DE LOS VALORES FUNCIONA
		 */
		String pokemonId = "SquirtleDePruebas";
		// Escribo el id distinto al del grupo creado en prepare()
		int id = 10;
		
		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
				
		HttpEntity <String> request = new HttpEntity <> (pokemonId, headers);
				
		ResponseEntity <String> responseEntity = restTemplate.exchange("/especialidades/"+id+"/pokemons/"+pokemonId, HttpMethod.POST, request, String.class);
				
		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		
		// Corroboro que en la base de datos se actualizo el grupo con el nuevo alumno
		Optional <Especialidad> optEspecialidad = especialidadRepository.findById(id);
		Optional <Pokemon> optPokemon = pokemonRepository.findById(pokemonId);
		
		//Si la lista de alumnos del grupo no contiene al alumno, entonces la prueba es exitosa
		// NOTA: Se tuvo que utilizar una recuperacion EAGER ya que si se ocupaba LAZY no funcionaria
		assertEquals(false,optEspecialidad.get().getPokemons().contains(optPokemon.get()));
	}
}
