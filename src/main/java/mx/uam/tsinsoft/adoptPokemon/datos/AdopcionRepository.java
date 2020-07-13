package mx.uam.tsinsoft.adoptPokemon.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Adopcion;

public interface AdopcionRepository extends CrudRepository <Adopcion, Integer>{
}
