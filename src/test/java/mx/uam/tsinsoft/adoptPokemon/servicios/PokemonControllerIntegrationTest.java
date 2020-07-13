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
import mx.uam.tsinsoft.adoptPokemon.datos.PokemonRepository;

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
public class PokemonControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private PokemonRepository pokemonRepository;
	
	@BeforeEach
	public void prepare() {
		
		// Aqui se puede hacer cosas para preparar sus casos de prueba, incluyendo
		// agregar datos a la BD
		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("SquirtleDePruebas");
		pokemon.setStatus("SoloSolinSolito");
		
		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Pokemon> request = new HttpEntity <> (pokemon, headers);
		
		restTemplate.exchange("/pokemons", HttpMethod.POST, request, Pokemon.class);
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
	public void testCreate201() {
		
		// Creo el alumno que voy a enviar con datos distintos al creado en prepare
		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("Ditto");
		pokemon.setStatus("Gordito");

		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Pokemon> request = new HttpEntity <> (pokemon, headers);
		
		ResponseEntity <Pokemon> responseEntity = restTemplate.exchange("/pokemons", HttpMethod.POST, request, Pokemon.class);

		log.info("Me regresó:"+responseEntity.getBody());
		
		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		
		// Corroboro que en la base de datos se guardó el alumno
		Optional <Pokemon> optAlumno = pokemonRepository.findById("Ditto");
		assertEquals(pokemon,optAlumno.get());
	}

	@Test
	public void testCreate400() {
		// Creo el alumno que voy a enviar pero sin matricula
		Pokemon pokemon = new Pokemon();
		
		pokemon.setStatus("Gordito");

		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Pokemon> request = new HttpEntity <> (pokemon, headers);
		
		ResponseEntity <Pokemon> responseEntity = restTemplate.exchange("/pokemons", HttpMethod.POST, request, Pokemon.class);
		
		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	/*
	 * PRUEBAS PARA RETRIVE ALL
	 */
	
	@Test
	public void testRetriveAll201() {

		Pokemon pokemon = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Pokemon> request = new HttpEntity <> (pokemon, headers);
		
		ResponseEntity<Pokemon> responseEntity = restTemplate.exchange("/pokemons", HttpMethod.GET, request, Pokemon.class);

		log.info("Me regresó:"+responseEntity.getBody());
		
		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
