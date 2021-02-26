package mx.uam.tsinsoft.adoptPokemon.negocio.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Trabajador {
	
	@ApiModelProperty(notes = "id de la ficha de trabajador del entrenador", required = true)
	@Id
	@GeneratedValue
	private Integer id;
	
	@ApiModelProperty(notes = "Nombre de la especialidad", required = true)
	private String rank;
}
