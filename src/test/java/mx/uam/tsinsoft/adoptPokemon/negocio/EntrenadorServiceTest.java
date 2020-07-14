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

import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Entrenador;
import mx.uam.tsinsoft.adoptPokemon.datos.EntrenadorRepository;

/**
 * @author erick
 *
 */
@ExtendWith(MockitoExtension.class) //Uso de mockito

public class EntrenadorServiceTest {
	
	//Variable para guardar el ID generado
	
	@Mock
	private EntrenadorRepository entrenadorRepositoryMock; //Mock generado por mockito
	
	@Mock
	private PokemonService pokemonServiceMock; //Mock generado por mockito
	
	@InjectMocks
	private EntrenadorService entrenadorService;
	
	//Crea caso de prueba
	
	/**
	 * PRUEBAS PARA CREATE
	 */
	
	@Test
	public void testSuccesfulCreate() {
		
		Entrenador entrenador = new Entrenador();
		entrenador.setId(1);
		
		//que no ha sido guardado
		when(entrenadorRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(null));
		
		//Simula lo que haria el alumnoRepository real cuando se le pasa un nuevo alumno para guardarlo
		when(entrenadorRepositoryMock.save(entrenador)).thenReturn(entrenador);
		
		//Unidad que quiero probar
		entrenador = entrenadorService.create(entrenador);
		
		//Compruwbo el resultado
		assertNotNull(entrenador); //Probar que la referencia a alumno es no nula
	}
	
	@Test
	public void testUnsuccesfulCreate() {
		
		Entrenador entrenador = new Entrenador();
		entrenador.setId(1);
		
		//Simula lo que haria el alumnoRepository real cuando se le pasa una matricula de alumno
		//que ya ha sido guardado
		when(entrenadorRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(entrenador));
		
		//Unidad que quiero probar
		entrenador = entrenadorService.create(entrenador);
		
		//Compruwbo el resultado
		assertNull(entrenador); //Probar que la referencia a alumno es nula porque el alumno ya existia
	}
	
	/**
	 * PRUEBAS PARA RETRIEVE
	 */
	
	@Test
	public void testSuccesfulRetrive() {
		
		Entrenador entrenador = new Entrenador();
		entrenador.setId(1);
		
		when(entrenadorRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(entrenador));
		
		//Unidad que quiero probar
		entrenador = entrenadorService.retrive(1);
		
		//Compruwbo el resultado
		assertNotNull(entrenador); //Probar que la referencia a alumno es no nula
	}
	
	@Test
	public void testUnsuccesfulRetrive() {
		
		Entrenador entrenador = new Entrenador();

		when(entrenadorRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(null));
		
		//Unidad que quiero probar
		entrenador = entrenadorService.retrive(1);
		
		//Compruwbo el resultado
		assertNull(entrenador); //Probar que la referencia a alumno es no nula
	}
	
	/**
	 * PRUEBAS PARA UPDATE
	 */

	@Test
	public void testSuccesfulUpdate() {
		
		Entrenador entrenador = new Entrenador();
		entrenador.setId(1);
		entrenador.setNombre("Ash Ketchup");
		
		Entrenador entrenadorActualizado = new Entrenador();
		entrenadorActualizado.setId(1);
		entrenadorActualizado.setNombre("Gary Oak");
		
		when(entrenadorRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(entrenador));
		
		when(entrenadorRepositoryMock.save(entrenadorActualizado)).thenReturn(entrenadorActualizado);
		
		//Unidad que quiero probar
		entrenador = entrenadorService.update(entrenadorActualizado);
		
		//Compruwbo el resultado
		assertEquals(entrenador,entrenadorActualizado);
	}

	@Test
	public void testUnsuccesfulUpdate() {
		
		Entrenador entrenador = new Entrenador();
		
		Entrenador entrenadorActualizado = new Entrenador();
		entrenadorActualizado.setId(1);
		entrenadorActualizado.setNombre("Gary Oak");
		
		//Simula lo que haria el alumnoRepository real cuando se le pasa una matricula de alumno
		//que no ha sido guardado
		when(entrenadorRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(null));
		
		//Unidad que quiero probar
		entrenador = entrenadorService.update(entrenadorActualizado);
		
		//Compruwbo el resultado
		assertEquals(null,entrenador);
	}
	
	/**
	 * PRUEBAS PARA DELETE
	 */

	@Test
	public void testSuccesfulDelete() {
		
		//Unidad que quiero probar
		boolean result = entrenadorService.delete(1);
		
		//Compruwbo el resultado
		assertEquals(true,result);
	}
	
	/**
	 * PRUEBAS PARA EXIST
	 */
	
	@Test
	public void testSuccesfulExist() {
		
		when(entrenadorRepositoryMock.existsById(1)).thenReturn(true);
		
		//Unidad que quiero probar
		boolean result = entrenadorService.exist(1);
		
		//Compruwbo el resultado
		assertEquals(true,result);
	}
	
	@Test
	public void testUnsuccesfulExist() {

		when(entrenadorRepositoryMock.existsById(1)).thenReturn(false);
		
		//Unidad que quiero probar
		boolean result = entrenadorService.exist(1);
		
		//Compruwbo el resultado
		assertEquals(false,result);
	}
}

