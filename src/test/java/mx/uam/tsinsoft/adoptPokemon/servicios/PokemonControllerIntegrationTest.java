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
		
		pokemonRepository.save(pokemon);
	}
	
	@AfterEach
	public void destroy() {
		pokemonRepository.deleteAll();
	}
	
	/*
	 * PRUEBAS PARA CREATE
	 */
	
	@Test
	public void testCreate201() {
		
		// Creo el alumno que voy a enviar con datos distintos al creado en prepare
		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("SquirtleDePruebas");
		pokemon.setStatus("SoloSolinSolito");

		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Pokemon> request = new HttpEntity <> (pokemon, headers);
		
		ResponseEntity <Pokemon> responseEntity = restTemplate.exchange("/pokemons", HttpMethod.POST, request, Pokemon.class);

		log.info("Me regresó:"+responseEntity.getBody());
		
		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}

	@Test
	public void testCreate500() {
		
		Pokemon pokemon = new Pokemon();
		pokemon.setStatus("Gordito");

		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Pokemon> request = new HttpEntity <> (pokemon, headers);
		
		ResponseEntity <Pokemon> responseEntity = restTemplate.exchange("/pokemons", HttpMethod.POST, request, Pokemon.class);
		
		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	}
	
	/*
	 * PRUEBAS PARA RETRIEVE
	 */
	
	@Test
	public void testRetrieve200() {
		
		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("SquirtleDePruebas");
		pokemon.setStatus("Acompañadito");

		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Pokemon> request = new HttpEntity <> (pokemon, headers);
		
		ResponseEntity <Pokemon> responseEntity = restTemplate.exchange("/pokemons/"+pokemon.getPokemonId(), HttpMethod.POST, request, Pokemon.class);

		log.info("Me regresó:"+responseEntity.getBody());
		
		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}



}
