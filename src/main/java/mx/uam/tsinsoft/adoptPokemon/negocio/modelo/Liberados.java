package mx.uam.tsinsoft.adoptPokemon.negocio.modelo;

import java.util.ArrayList;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity //Indica que hay que persistir en BD
@EqualsAndHashCode
public class Liberados {
	
	@ApiModelProperty(notes = "ID del centro de adopcion", required = true)
	@Id //Indica que es la llave primaria
	private String clave;
	
	@ApiModelProperty(notes = "Lista de pokemons liberados", required = true)
	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "pokemonsfree") //Para unificar las tablas de pokemon y liberados
	private List <Pokemon> pokemons = new ArrayList <> ();
	
	public boolean addPokemon(Pokemon pokemon) {
		return pokemons.add(pokemon);
	}
}
