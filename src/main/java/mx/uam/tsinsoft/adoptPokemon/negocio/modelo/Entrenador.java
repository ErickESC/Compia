package mx.uam.tsinsoft.adoptPokemon.negocio.modelo;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class Entrenador {
	
	@ApiModelProperty(notes = "ID del entrenador", required = true)
	@Id //Indica que es la llave primaria
	@GeneratedValue //Genera la clave de manera automatica
	private Integer id;
	
	@ApiModelProperty(notes = "Nombre del entrenador", required = true)
	@NotBlank
	@NotNull
	private String nombre;
	
	@ApiModelProperty(notes = "Email del entrenador", required = true)
	@NotBlank
	@NotNull
	private String mail;
	
	@ApiModelProperty(notes = "direccion del entrenador", required = true)
	@NotBlank
	@NotNull
	private String direccion;
	
	@ApiModelProperty(notes = "contraseña del entrenador", required = true)
	@NotBlank
	@NotNull
	private String psswrd;
	
	@ApiModelProperty(notes = "En caso de ser trabajador se le asignan datos, null en caso contrario", required = true)
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "esTrabajador")
	private Trabajador soyTrabajador;
	
	public boolean toTrabajador(Trabajador trabajador) {
		soyTrabajador = trabajador;
		return true;
	}
	
	@ApiModelProperty(notes = "Lista de pokemons adoptados por un entrenador", required = true)
	@Builder.Default
	@OneToMany(mappedBy= "entrenador", cascade = CascadeType.ALL)
	private List <Pokemon> pokemons = new ArrayList <> ();
	
	public boolean addPokemon(Pokemon pokemon) {
		return pokemons.add(pokemon);
	}
	
	public boolean quitPokemon(Pokemon pokemon) {
		return pokemons.remove(pokemon);
	}
}
