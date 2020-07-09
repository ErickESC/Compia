/**
 * 
 */
package mx.uam.tsinsoft.adoptPokemon.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;

/**
 * @author erick
 *
 */
public interface PokemonRepository extends CrudRepository <Pokemon, String> {//Entidad, Tipo de la llave primaria

}
