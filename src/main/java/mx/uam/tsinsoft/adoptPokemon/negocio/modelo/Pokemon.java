package mx.uam.tsinsoft.adoptPokemon.negocio.modelo;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
public class Pokemon {
	
	@ApiModelProperty(notes = "Id del Pokemon", required = true)
	@Id//Indica que es la llave primaria
	private String pokemonId;
	
	@ApiModelProperty(notes = "Status del Pokemon", required = true)
	@NotBlank
	private String status;
	
	@ApiModelProperty(notes = "Entrenador del Pokemon. Si es null, aun no es adoptado", required = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pokemonAdopter")
	private Entrenador entrenador;
	
	@ApiModelProperty(notes = "Informacion del Pokemon", required = true)
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "pokemonInfo")
	private Information information;
}
