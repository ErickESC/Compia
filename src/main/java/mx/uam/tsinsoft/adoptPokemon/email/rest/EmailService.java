package mx.uam.tsinsoft.adoptPokemon.email.rest;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService{

	@Autowired
	private JavaMailSender sender;

	public boolean sendAdoptionEmail(String trainerName, String trainerEmail, String pokemonName)  {
		
		String content = "Muchas gracias "+trainerName+" por adoptar a nuestro querido "+pokemonName+" lo puedes venir a recoger a la direccion Colonia OddishSmell, calle Alakasam en Pueblo Paleta";
		
		String subject = "AdoptPokemon-Adopcion";
		
		EmailBody emailBody = new EmailBody();
		emailBody.setContent(content);
		emailBody.setEmail(trainerEmail);
		emailBody.setSubject(subject);
		
		log.info("EmailBody: {}", emailBody.toString());
		return sendEmailTool(emailBody.getContent(),emailBody.getEmail(), emailBody.getSubject());
	}
	
	public boolean sendRecruitEmail(String trainerName, String trainerEmail)  {
		
		String content = "Muchas gracias "+trainerName+" por haberte unido a nuestra causa, necesitamos que te presentes a la direccion Colonia OddishSmell, calle Alakasam en Pueblo Paleta lo mas pronto posible para que recibas tu capacitacion";
		
		String subject = "AdoptPokemon-Recluta";
		
		EmailBody emailBody = new EmailBody();
		emailBody.setContent(content);
		emailBody.setEmail(trainerEmail);
		emailBody.setSubject(subject);
		
		log.info("EmailBody: {}", emailBody.toString());
		return sendEmailTool(emailBody.getContent(),emailBody.getEmail(), emailBody.getSubject());
	}
	

	private boolean sendEmailTool(String textMessage, String email,String subject) {
		boolean send = false;
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);		
		try {
			helper.setTo(email);
			helper.setText(textMessage, true);
			helper.setSubject(subject);
			sender.send(message);
			send = true;
			log.info("Mail enviado!");
		} catch (MessagingException e) {
			log.error("Hubo un error al enviar el mail: {}", e);
		}
		return send;
	}
}
