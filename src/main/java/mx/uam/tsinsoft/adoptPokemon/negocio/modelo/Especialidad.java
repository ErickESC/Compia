package mx.uam.tsinsoft.adoptPokemon.negocio.modelo;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
public class Especialidad {
	
	@ApiModelProperty(notes = "ID dela especialidad", required = true)
	@Id //Indica que es la llave primaria
	@GeneratedValue //Genera la clave de manera automatica
	private Integer id;
	
	@ApiModelProperty(notes = "Clave dela especialidad", required = true)
	@NotBlank
	private String clave;
	
	@ApiModelProperty(notes = "Nombre dela especialidad", required = true)
	@NotBlank
	private String nombre;
	
	@ApiModelProperty(notes = "Lista de pokemons que requieren cuidados especiales", required = true)
	@Builder.Default
	@OneToMany(targetEntity = Pokemon.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "pokemonsInt") //Para unificar las tablas de pokemon y especialidad
	private List <Pokemon> pokemons = new ArrayList <> ();
	
	public boolean addPokemon(Pokemon pokemon) {
		return pokemons.add(pokemon);
	}
}
