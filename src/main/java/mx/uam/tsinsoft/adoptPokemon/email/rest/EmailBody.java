package mx.uam.tsinsoft.adoptPokemon.email.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmailBody {
	
	private String subject;
	
	private String email;
	
	private String content;
	
}
