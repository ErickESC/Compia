package mx.uam.tsinsoft.adoptPokemon.negocio.modelo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Trabajador {
	
	@ApiModelProperty(notes = "clave del entrenador", required = true)
	@Id
	@GeneratedValue
	private Integer id;
	
	@ApiModelProperty(notes = "clave del entrenador", required = true)
	private String clave;
	
	@ApiModelProperty(notes = "Nombre dela especialidad", required = true)
	@NotBlank
	@NotNull
	private String rank;
}
