/**
 * 
 */
package mx.uam.tsinsoft.adoptPokemon.negocio.modelo;

import lombok.Builder;
import lombok.Data;

/**
 * @author erick
 *
 */
@Builder
@Data
public class Alumno {
	
	private Integer matricula;
	
	private String nombre;
	
	private String carrera;

}
