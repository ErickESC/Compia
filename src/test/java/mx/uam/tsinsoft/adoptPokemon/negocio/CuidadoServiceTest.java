package mx.uam.tsinsoft.adoptPokemon.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Cuidado;
import mx.uam.tsinsoft.adoptPokemon.datos.CuidadoRepository;

/**
 * @author erick
 *
 */
@ExtendWith(MockitoExtension.class) //Uso de mockito
public class CuidadoServiceTest {
	
	@Mock
	private CuidadoRepository cuidadoRepositoryMock; //Mock generado por mockito
	
	@Mock
	private PokemonService pokemonServiceMock; 
	
	@InjectMocks //Se inyectan los mocks de arriba a la unidad a probar 
	private CuidadoService cuidadoService; //La unidad a provar
	
	//Crea caso de prueba
	
	/**
	 * PRUEBAS PARA ADD POKEMON TO CARE
	 */
	
	@Test
	public void testSuccesfulAddPokemonToAdoption (){
		
		Cuidado grupo = new Cuidado();
		grupo.setId(1);
		grupo.setClave("SolinesSolitos");

		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("SquirtleDePruebas");
		pokemon.setStatus("SoloSolinSolito");
		
		when(pokemonServiceMock.retrive("SquirtleDePruebas")).thenReturn(pokemon);
		
		when(cuidadoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.of(grupo));
		
		boolean result = cuidadoService.addPokemonToCare(1, "SquirtleDePruebas");
		
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
		when(cuidadoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		boolean result = cuidadoService.addPokemonToCare(anyInt(), "SquirtleDePruebas");
		
		assertEquals(false,result);
	}
	
	/**
	 * PRUEBAS PARA CREATE
	 */
	
	@Test
	public void testSuccesfulCreate() {
		
		Cuidado grupo = new Cuidado();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		when(cuidadoRepositoryMock.save(grupo)).thenReturn(grupo);

		grupo = cuidadoService.create(grupo);
		
		assertNotNull(grupo); 
	}
	
	/**
	 * PRUEBAS PARA RETRIEVE
	 */
	
	@Test
	public void testSuccesfulRetrive() {
		
		Cuidado grupo = new Cuidado();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		when(cuidadoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(grupo));
		
		//Unidad que quiero probar
		Optional <Cuidado> grupoRetrived = cuidadoService.retrive(1);
		
		//Compruwbo el resultado
		assertNotNull(grupoRetrived); 
	}
	
	@Test
	public void testUnsuccesfulRetrive() {
		
		@SuppressWarnings("unused")
		Cuidado grupo = new Cuidado();

		when(cuidadoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(null));
		
		//Unidad que quiero probar
		Optional <Cuidado> grupoRetrived = cuidadoService.retrive(1);
		
		//Compruwbo el resultado
		assertNull(grupoRetrived);
	}
	
	/**
	 * PRUEBAS PARA UPDATE
	 */

	@Test
	public void testSuccesfulUpdate() {
		
		Cuidado grupo = new Cuidado();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		Cuidado grupoActualizado = new Cuidado();
		grupo.setId(1);
		grupo.setClave("TS-00P2");
		
		//when(adopcionRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(grupo));

		//when(adopcionRepositoryMock.save(grupoActualizado)).thenReturn(grupoActualizado);
		
		//Unidad que quiero probar
		grupo = cuidadoService.update(grupoActualizado);
		
		//Compruwbo el resultado
		assertEquals(grupoActualizado,grupo);
	}

	@Test
	public void testUnsuccesfulUpdate() {
		
		Cuidado grupo = new Cuidado();
		
		Cuidado grupoActualizado = new Cuidado();
		grupoActualizado.setId(1);
		grupoActualizado.setClave("TST01");

		when(cuidadoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(null));

		grupo = cuidadoService.update(grupoActualizado);
		
		//Compruwbo el resultado
		assertEquals(null,grupo);
	}
	
	/**
	 * PRUEBAS PARA DELETE
	 */

	@Test
	public void testSuccesfulDelete() {
		
		//Unidad que quiero probar
		boolean result = cuidadoService.delete(1);
		
		//Compruwbo el resultado
		assertEquals(true,result);
	}
	
	/**
	 * PRUEBAS PARA EXIST
	 */
	
	@Test
	public void testSuccesfulExist() {
		
		when(cuidadoRepositoryMock.existsById(1)).thenReturn(true);
		
		//Unidad que quiero probar
		boolean result = cuidadoService.exist(1);
		
		//Compruwbo el resultado
		assertEquals(true,result);
	}
	
	@Test
	public void testUnsuccesfulExist() {

		when(cuidadoRepositoryMock.existsById(1)).thenReturn(false);
		
		//Unidad que quiero probar
		boolean result = cuidadoService.exist(1);
		
		//Compruwbo el resultado
		assertEquals(false,result);
	}
}