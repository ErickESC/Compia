/**
 * 
 */
package mx.uam.tsinsoft.adoptPokemon.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Information;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;
import mx.uam.tsinsoft.adoptPokemon.datos.PokemonRepository;

/**
 * @author erick
 *
 */
@ExtendWith(MockitoExtension.class) //Uso de mockito

public class EntrenadorServiceTest {
	
	//Variable para guardar el ID generado
	
	@Mock
	private PokemonRepository pokemonRepositoryMock; //Mock generado por mockito
	
	@InjectMocks //Se inyectan los mocks de arriba a la unidad a probar 
	private PokemonService pokemonService; //La unidad a provar
	
	//Crea caso de prueba
	
	/**
	 * PRUEBAS PARA CREATE
	 */
	
	@Test
	public void testSuccesfulCreate() {
		
		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("SquirtleDePruebas");
		pokemon.setStatus("SoloSolinSolito");
		
		//Simula lo que haria el alumnoRepository real cuando se le pasa una matricula de alumno
		//que no ha sido guardado
		when(pokemonRepositoryMock.findById("SquirtleDePruebas")).thenReturn(Optional.ofNullable(null));
		
		//Simula lo que haria el alumnoRepository real cuando se le pasa un nuevo alumno para guardarlo
		when(pokemonRepositoryMock.save(pokemon)).thenReturn(pokemon);
		
		//Unidad que quiero probar
		pokemon = pokemonService.create(pokemon);
		
		//Compruwbo el resultado
		assertNotNull(pokemon); //Probar que la referencia a alumno es no nula
	}
	
	@Test
	public void testUnsuccesfulCreate() {
		
		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("SquirtleDePruebas");
		pokemon.setStatus("SoloSolinSolito");
		
		//Simula lo que haria el alumnoRepository real cuando se le pasa una matricula de alumno
		//que ya ha sido guardado
		when(pokemonRepositoryMock.findById("SquirtleDePruebas")).thenReturn(Optional.ofNullable(pokemon));
		
		//Unidad que quiero probar
		pokemon = pokemonService.create(pokemon);
		
		//Compruwbo el resultado
		assertNull(pokemon); //Probar que la referencia a alumno es nula porque el alumno ya existia
	}
	
	/**
	 * PRUEBAS PARA RETRIEVE
	 */
	
	@Test
	public void testSuccesfulRetrive() {
		
		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("SquirtleDePruebas");
		pokemon.setStatus("SoloSolinSolito");
		
		when(pokemonRepositoryMock.findById("SquirtleDePruebas")).thenReturn(Optional.ofNullable(pokemon));
		
		//Unidad que quiero probar
		pokemon = pokemonService.retrive("SquirtleDePruebas");
		
		//Compruwbo el resultado
		assertNotNull(pokemon); //Probar que la referencia a alumno es no nula
	}
	
	@Test
	public void testUnsuccesfulRetrive() {
		
		Pokemon pokemon = new Pokemon();

		when(pokemonRepositoryMock.findById("SquirtleDePruebas")).thenReturn(Optional.ofNullable(null));
		
		//Unidad que quiero probar
		pokemon = pokemonService.retrive("SquirtleDePruebas");
		
		//Compruwbo el resultado
		assertNull(pokemon); //Probar que la referencia a alumno es no nula
	}
	
	/**
	 * PRUEBAS PARA UPDATE
	 */

	@Test
	public void testSuccesfulUpdate() {
		
		Information informacion = new Information();
		informacion.setId(1);
		informacion.setDescription("efw");
		
		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("SquirtleDePruebas");
		pokemon.setStatus("SoloSolinSolito");
		
		Pokemon pokemonActualizado = new Pokemon();
		pokemon.setPokemonId("SquirtleDePruebas");
		pokemon.setStatus("Acompañadito");
		pokemon.setInformation(informacion);
		
		when(pokemonRepositoryMock.findById("SquirtleDePruebas")).thenReturn(Optional.ofNullable(pokemon));
		
		when(pokemonRepositoryMock.save(pokemonActualizado)).thenReturn(pokemonActualizado);
		
		//Unidad que quiero probar
		pokemon = pokemonService.update(pokemonActualizado);
		
		//Compruwbo el resultado
		assertEquals(pokemon,pokemonActualizado);
	}

	@Test
	public void testUnsuccesfulUpdate() {
		
		Pokemon pokemon = new Pokemon();
		
		Pokemon alumnoActualizado = new Pokemon();
		alumnoActualizado.setPokemonId("SquirtleDePruebas");
		alumnoActualizado.setStatus("SoloSolinSolito");
		
		//Simula lo que haria el alumnoRepository real cuando se le pasa una matricula de alumno
		//que no ha sido guardado
		when(pokemonRepositoryMock.findById("SquirtleDePruebas")).thenReturn(Optional.ofNullable(null));
		
		//Unidad que quiero probar
		pokemon = pokemonService.update(alumnoActualizado);
		
		//Compruwbo el resultado
		assertEquals(null,pokemon);
	}
	
	/**
	 * PRUEBAS PARA DELETE
	 */

	@Test
	public void testSuccesfulDelete() {
		
		//Unidad que quiero probar
		boolean result = pokemonService.delete("SquirtleDePruebas");
		
		//Compruwbo el resultado
		assertEquals(true,result);
	}
	
	/**
	 * PRUEBAS PARA EXIST
	 */
	
	@Test
	public void testSuccesfulExist() {
		
		when(pokemonRepositoryMock.existsById("SquirtleDePruebas")).thenReturn(true);
		
		//Unidad que quiero probar
		boolean result = pokemonService.exist("SquirtleDePruebas");
		
		//Compruwbo el resultado
		assertEquals(true,result);
	}
	
	@Test
	public void testUnsuccesfulExist() {

		when(pokemonRepositoryMock.existsById("SquirtleDePruebas")).thenReturn(false);
		
		//Unidad que quiero probar
		boolean result = pokemonService.exist("SquirtleDePruebas");
		
		//Compruwbo el resultado
		assertEquals(false,result);
	}
}

