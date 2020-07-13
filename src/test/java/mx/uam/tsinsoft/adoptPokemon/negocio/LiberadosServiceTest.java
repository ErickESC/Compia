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

import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Liberados;
import mx.uam.tsinsoft.adoptPokemon.datos.LiberadosRepository;

/**
 * @author erick
 *
 */
@ExtendWith(MockitoExtension.class) //Uso de mockito
public class LiberadosServiceTest {
	
	@Mock
	private LiberadosRepository liberadosRepositoryMock; //Mock generado por mockito
	
	@Mock
	private PokemonService pokemonServiceMock; 
	
	@InjectMocks //Se inyectan los mocks de arriba a la unidad a probar 
	private LiberadosService liberadosService; //La unidad a provar
	
	//Crea caso de prueba
	
	/**
	 * PRUEBAS PARA ADD POKEMON TO FREEDOM
	 */
	
	@Test
	public void testSuccesfulAddPokemonToAdoption (){
		
		Liberados grupo = new Liberados();
		grupo.setClave("prueba");

		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("SquirtleDePruebas");
		pokemon.setStatus("SoloSolinSolito");
		
		when(pokemonServiceMock.retrive("SquirtleDePruebas")).thenReturn(pokemon);
		
		when(liberadosRepositoryMock.findById(grupo.getClave())).thenReturn(Optional.of(grupo));
		
		boolean result = liberadosService.addPokemonToFreedom("prueba", "SquirtleDePruebas");
		
		assertEquals(true,result);
		
		assertEquals(grupo.getPokemons().get(0),pokemon);
		
	}
	
	@Test
	public void testUnsuccesfulAddPokemonToAdoption (){
		
		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("SquirtleDePruebas");
		pokemon.setStatus("SoloSolinSolito");
		
		// Stubbing para el pokemonService
		when(pokemonServiceMock.retrive("SquirtleDePruebas")).thenReturn(pokemon);
		
		// Stubbing para especialidadRepository
		when(liberadosRepositoryMock.findById("noExists")).thenReturn(Optional.ofNullable(null));
		
		boolean result = liberadosService.addPokemonToFreedom("noExists", "SquirtleDePruebas");
		
		assertEquals(false,result);
	}
	
	/**
	 * PRUEBAS PARA CREATE
	 */
	
	@Test
	public void testSuccesfulCreate() {
		
		Liberados grupo = new Liberados();
		grupo.setClave("prueba");
		
		when(liberadosRepositoryMock.save(grupo)).thenReturn(grupo);

		grupo = liberadosService.create(grupo);
		
		assertNotNull(grupo); 
	}
	
	/**
	 * PRUEBAS PARA RETRIEVE
	 */
	
	@Test
	public void testSuccesfulRetrive() {
		
		Liberados grupo = new Liberados();
		grupo.setClave("prueba");
		
		when(liberadosRepositoryMock.findById("prueba")).thenReturn(Optional.ofNullable(grupo));
		
		//Unidad que quiero probar
		Optional <Liberados> grupoRetrived = liberadosService.retrive("prueba");
		
		//Compruwbo el resultado
		assertNotNull(grupoRetrived); 
	}
	
	@Test
	public void testUnsuccesfulRetrive() {
		
		@SuppressWarnings("unused")
		Liberados grupo = new Liberados();

		when(liberadosRepositoryMock.findById("prueba")).thenReturn(Optional.ofNullable(null));
		
		//Unidad que quiero probar
		Optional <Liberados> grupoRetrived = liberadosService.retrive("prueba");
		
		//Compruwbo el resultado
		assertNull(grupoRetrived);
	}
	
	/**
	 * PRUEBAS PARA UPDATE
	 */

	@Test
	public void testSuccesfulUpdate() {
		
		Liberados grupo = new Liberados();
		grupo.setClave("TST01");
		
		Liberados grupoActualizado = new Liberados();
		grupo.setClave("TS-00P2");
		
		//when(adopcionRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(grupo));

		//when(adopcionRepositoryMock.save(grupoActualizado)).thenReturn(grupoActualizado);
		
		//Unidad que quiero probar
		grupo = liberadosService.update(grupoActualizado);
		
		//Compruwbo el resultado
		assertEquals(grupoActualizado,grupo);
	}

	@Test
	public void testUnsuccesfulUpdate() {
		
		Liberados grupo = new Liberados();
		
		Liberados grupoActualizado = new Liberados();
		grupoActualizado.setClave("TST01");

		when(liberadosRepositoryMock.findById("prueba")).thenReturn(Optional.ofNullable(null));

		grupo = liberadosService.update(grupoActualizado);
		
		//Compruwbo el resultado
		assertEquals(null,grupo);
	}
	
	/**
	 * PRUEBAS PARA DELETE
	 */

	@Test
	public void testSuccesfulDelete() {
		
		//Unidad que quiero probar
		boolean result = liberadosService.delete("prueba");
		
		//Compruwbo el resultado
		assertEquals(true,result);
	}
	
	/**
	 * PRUEBAS PARA EXIST
	 */
	
	@Test
	public void testSuccesfulExist() {
		
		when(liberadosRepositoryMock.existsById("prueba")).thenReturn(true);
		
		//Unidad que quiero probar
		boolean result = liberadosService.exist("prueba");
		
		//Compruwbo el resultado
		assertEquals(true,result);
	}
	
	@Test
	public void testUnsuccesfulExist() {

		when(liberadosRepositoryMock.existsById("prueba")).thenReturn(false);
		
		//Unidad que quiero probar
		boolean result = liberadosService.exist("prueba");
		
		//Compruwbo el resultado
		assertEquals(false,result);
	}
}