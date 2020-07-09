/**
 * 
 */
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
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Especialidad;
import mx.uam.tsinsoft.adoptPokemon.datos.EspecialidadRepository;

/**
 * @author erick
 *
 */
@ExtendWith(MockitoExtension.class) //Uso de mockito
public class EspecialidadServiceTest {
	
	@Mock
	private EspecialidadRepository especialidadRepositoryMock; //Mock generado por mockito
	
	@Mock
	private PokemonService pokemonServiceMock; 
	
	@InjectMocks //Se inyectan los mocks de arriba a la unidad a probar 
	private EspecialidadService especialidadService; //La unidad a provar
	
	//Crea caso de prueba
	
	/**
	 * PRUEBAS PARA ADD POKEMON TO ESPECIALTY
	 */
	
	@Test
	public void testSuccesfulAddPokemonToSpecialty (){
		
		Especialidad especialidad = new Especialidad();
		especialidad.setId(1);
		especialidad.setClave("SolinesSolitos");

		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("SquirtleDePruebas");
		pokemon.setStatus("SoloSolinSolito");
		
		when(pokemonServiceMock.retrive("SquirtleDePruebas")).thenReturn(pokemon);
		
		when(especialidadRepositoryMock.findById(especialidad.getId())).thenReturn(Optional.of(especialidad));
		
		boolean result = especialidadService.addPokemonToSpecialty(1, "SquirtleDePruebas");
		
		assertEquals(true,result);
		
		assertEquals(especialidad.getPokemons().get(0),pokemon);
		
	}
	
	@Test
	public void testUnsuccesfulAddPokemonToSpecialty (){
		
		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("SquirtleDePruebas");
		pokemon.setStatus("SoloSolinSolito");
		
		// Stubbing para el pokemonService
		when(pokemonServiceMock.retrive("SquirtleDePruebas")).thenReturn(pokemon);
		
		// Stubbing para especialidadRepository
		when(especialidadRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		boolean result = especialidadService.addPokemonToSpecialty(anyInt(), "SquirtleDePruebas");
		
		assertEquals(false,result);
	}
	
	/**
	 * PRUEBAS PARA CREATE
	 */
	
	@Test
	public void testSuccesfulCreate() {
		
		Especialidad especialidad = new Especialidad();
		especialidad.setId(1);
		especialidad.setClave("TST01");
		
		when(especialidadRepositoryMock.save(especialidad)).thenReturn(especialidad);

		especialidad = especialidadService.create(especialidad);
		
		assertNotNull(especialidad); 
	}
	
	/**
	 * PRUEBAS PARA RETRIEVE
	 */
	
	@Test
	public void testSuccesfulRetrive() {
		
		Especialidad especialidad = new Especialidad();
		especialidad.setId(1);
		especialidad.setClave("TST01");
		
		when(especialidadRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(especialidad));
		
		//Unidad que quiero probar
		Optional <Especialidad> grupoRetrived = especialidadService.retrive(1);
		
		//Compruwbo el resultado
		assertNotNull(grupoRetrived); 
	}
	
	@Test
	public void testUnsuccesfulRetrive() {
		
		@SuppressWarnings("unused")
		Especialidad especialidad = new Especialidad();

		when(especialidadRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(null));
		
		//Unidad que quiero probar
		Optional <Especialidad> grupoRetrived = especialidadService.retrive(1);
		
		//Compruwbo el resultado
		assertNull(grupoRetrived);
	}
	
	/**
	 * PRUEBAS PARA UPDATE
	 */

	@Test
	public void testSuccesfulUpdate() {
		
		Especialidad especialidad = new Especialidad();
		especialidad.setId(1);
		especialidad.setClave("TST01");
		
		Especialidad grupoActualizado = new Especialidad();
		grupoActualizado.setId(1);
		grupoActualizado.setClave("TST-UPD");
		
		when(especialidadRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(especialidad));

		when(especialidadRepositoryMock.save(grupoActualizado)).thenReturn(grupoActualizado);
		
		//Unidad que quiero probar
		especialidad = especialidadService.update(grupoActualizado);
		
		//Compruwbo el resultado
		assertEquals(especialidad,grupoActualizado);
	}

	@Test
	public void testUnsuccesfulUpdate() {
		
		Especialidad especialidad = new Especialidad();
		
		Especialidad grupoActualizado = new Especialidad();
		grupoActualizado.setId(1);
		grupoActualizado.setClave("TST01");

		when(especialidadRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(null));

		especialidad = especialidadService.update(grupoActualizado);
		
		//Compruwbo el resultado
		assertEquals(null,especialidad);
	}
	
	/**
	 * PRUEBAS PARA DELETE
	 */

	@Test
	public void testSuccesfulDelete() {
		
		//Unidad que quiero probar
		boolean result = especialidadService.delete(1);
		
		//Compruwbo el resultado
		assertEquals(true,result);
	}
	
	/**
	 * PRUEBAS PARA EXIST
	 */
	
	@Test
	public void testSuccesfulExist() {
		
		when(especialidadRepositoryMock.existsById(1)).thenReturn(true);
		
		//Unidad que quiero probar
		boolean result = especialidadService.exist(1);
		
		//Compruwbo el resultado
		assertEquals(true,result);
	}
	
	@Test
	public void testUnsuccesfulExist() {

		when(especialidadRepositoryMock.existsById(1)).thenReturn(false);
		
		//Unidad que quiero probar
		boolean result = especialidadService.exist(1);
		
		//Compruwbo el resultado
		assertEquals(false,result);
	}
}
