/**
 * 
 */
package mx.uam.tsinsoft.adoptPokemon.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Especialidad;

/**
 * @author erick
 *
 */
public interface EspecialidadRepository extends CrudRepository <Especialidad, Integer> {//Entidad, Tipo de la llave primaria

}

