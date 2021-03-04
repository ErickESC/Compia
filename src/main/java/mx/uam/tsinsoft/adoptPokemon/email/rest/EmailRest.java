package mx.uam.tsinsoft.adoptPokemon.email.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.negocio.EntrenadorService;
import mx.uam.tsinsoft.adoptPokemon.negocio.PokemonService;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Entrenador;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;

@RestController
@Slf4j
public class EmailRest {
	
	@Autowired
	private EntrenadorService entrenadorService;
	
	@Autowired
	private PokemonService pokemonService;
	
	@Autowired
	private EmailService emailService;
	
	/**
	 * 
	 * @param entrenadorGrupo
	 * @return status creado y Especialidad creado, status bad request en caso contrario
	 */
	@ApiOperation(
			value = "Enviar un correo al usuario que adopto un pokemon",
			notes = "Permite enviar un correo al entrenador que adopto a un pokemon"
			)
	@GetMapping(path = "/send/email/adopcion/{trainerid}/{pokemonId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> sendMailAdoption(@PathVariable("trainerid") @Valid Integer trainerid, @PathVariable("pokemonId") @Valid String pokemonId) {
		
		log.info("Recibi llamada a send email para entrenador con id: "+ trainerid);
		
		Entrenador entrenador = entrenadorService.retrive(trainerid);
		
		Pokemon pokemon = pokemonService.retrive(pokemonId);
		
		if((entrenador != null) || (pokemon != null)) {
			
			String trainerEmail = entrenador.getMail();
			
			String trainerName = entrenador.getNombre();
			
			String pokemonName = pokemon.getInformation().getName();
			
			boolean result = emailService.sendAdoptionEmail(trainerName, trainerEmail, pokemonName);
			
			if(result) {
				return ResponseEntity.status(HttpStatus.OK).body("Correo enviado con exito");
			}else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Algo salio mal");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro entrenador o pokemon");
		}
	}
	
	/**
	 * 
	 * @param entrenadorGrupo
	 * @return status creado y Especialidad creado, status bad request en caso contrario
	 */
	@ApiOperation(
			value = "Enviar un correo al usuario que adopto un pokemon",
			notes = "Permite enviar un correo al entrenador que adopto a un pokemon"
			)
	@GetMapping(path = "/send/email/recruit/{trainerid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> sendMailRecruitment(@PathVariable("trainerid") @Valid Integer trainerid) {
		
		log.info("Recibi llamada a send email para entrenador con id: "+ trainerid);
		
		Entrenador entrenador = entrenadorService.retrive(trainerid);
		
		if(entrenador != null) {
			
			String trainerEmail = entrenador.getMail();
			
			String trainerName = entrenador.getNombre();
			
			boolean result = emailService.sendRecruitEmail(trainerName, trainerEmail);
			
			if(result) {
				return ResponseEntity.status(HttpStatus.OK).body("Correo enviado con exito");
			}else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Algo salio mal");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro entrenador");
		}
	}
	
}
