package mx.uam.tsinsoft.adoptPokemon.negocio.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

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
public class Adopcion {
	
	@ApiModelProperty(notes = "ID del centro de adopcion", required = true)
	@Id //Indica que es la llave primaria
	private Integer id;
	
	@ApiModelProperty(notes = "Nombre del zona de adopcion", required = true)
	@NotBlank
	private String nombre;
	
	@ApiModelProperty(notes = "Clave de la zona de adopcion", required = true)
	@NotBlank
	private String clave;
	
	@ApiModelProperty(notes = "Lista de pokemons en adopcion", required = true)
	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "pokemonsAdc") //Para unificar las tablas de pokemon y adopcion
	private List <Pokemon> pokemons = new ArrayList <> ();
	
	public boolean addPokemon(Pokemon pokemon) {
		return pokemons.add(pokemon);
	}
	
	public boolean quitPokemon(Pokemon pokemon) {
		return pokemons.remove(pokemon);
	}
}
