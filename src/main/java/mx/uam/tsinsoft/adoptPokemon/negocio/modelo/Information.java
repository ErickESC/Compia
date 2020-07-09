package mx.uam.tsinsoft.adoptPokemon.negocio.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class Information {
	
	@ApiModelProperty(notes = "ID de la Informacion", required = true)
	@Id //Indica que es la llave primaria
	@GeneratedValue //Genera la clave de manera automatica
	private Integer id;
	
	@ApiModelProperty(notes = "Nombre del Pokemon", required = true)
	private String name;

	@ApiModelProperty(notes = "Imagen del Pokemon", required = true)
	@NotBlank
	private String image;

	@ApiModelProperty(notes = "Nivel del Pokemon", required = true)
	@NotBlank
	private String level;

	@ApiModelProperty(notes = "Genero del Pokemon", required = true)
	@NotBlank
	private String gender;

	@ApiModelProperty(notes = "Objeto del Pokemon", required = true)
	private String objeto;

	@ApiModelProperty(notes = "Naturaleza del Pokemon", required = true)
	private String nature;

	@ApiModelProperty(notes = "Descripcion del Pokemon", required = true)
	@NotBlank
	private String description;

	@ApiModelProperty(notes = "Numero en la podekex", required = true)
	@NotBlank
	private String pokedex;

	@ApiModelProperty(notes = "Region del Pokemon", required = true)
	@NotBlank
	private String region;

	@ApiModelProperty(notes = "Tipo primario del Pokemon", required = true)
	@NotBlank
	private String first;

	@ApiModelProperty(notes = "Tipo secundario del Pokemon", required = true)
	private String second;
}
