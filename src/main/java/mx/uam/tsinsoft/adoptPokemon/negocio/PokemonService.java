/**
 * 
 */
package mx.uam.tsinsoft.adoptPokemon.negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsinsoft.adoptPokemon.datos.PokemonRepository;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Information;
import mx.uam.tsinsoft.adoptPokemon.negocio.modelo.Pokemon;

/**
 * @author erick
 *
 */
@Service
@Slf4j
public class PokemonService {
	
	@Autowired
	private PokemonRepository pokemonRepository;
	
	/**
	 * 
	 * @param nuevoPokemon
	 * @return El alumno recien creado, null de lo contrario
	 */
	public Pokemon create(Pokemon nuevoPokemon) {
		log.info("Creando pokemon con matricula "+nuevoPokemon.getPokemonId());
		//Regla de negocio: no se puede crear mas de un alumno con la misma matricula
		Optional <Pokemon> pokemonOpt = pokemonRepository.findById(nuevoPokemon.getPokemonId());
		
		if(!pokemonOpt.isPresent()) {
			log.info("Creado el pokemon con matricula "+nuevoPokemon.getPokemonId());
			return pokemonRepository.save(nuevoPokemon);
		}else {
			log.info("El pokemon ya existe");
			return null;
		}
	}
	
	/**
	 * 
	 * @return Lista de los alumnos
	 */
	public Iterable <Pokemon> retriveAll(){
		log.info("Regresando arreglo con pokemons");
		return pokemonRepository.findAll();
	}
	
	/**
	 * 
	 * @param Id
	 * @return Pokemon al que le pertenece la matricula
	 */
	public Pokemon retrive(String Id){
		log.info("Llamado a regresar al a pokemon con matricula "+Id);
		
		Optional <Pokemon> pokemonOpt = pokemonRepository.findById(Id);
		
		if(pokemonOpt.isPresent()) {
			return pokemonOpt.get();
		}else {
			return null;
		}
	}
	
	/**
	 * 
	 * @param pokemonActualizado
	 * @return Pokemon actualizado, null en caso de no haberse encontrado
	 */
	public Pokemon update(Pokemon pokemonActualizado){ 
		
		log.info("Actualizando al pokemon con matricula "+pokemonActualizado.getPokemonId());
		
		Optional <Pokemon> pokemonOpt = pokemonRepository.findById(pokemonActualizado.getPokemonId());
		
		if(pokemonOpt.isPresent()) {
			
			if((pokemonActualizado.getInformation() == null) && (pokemonOpt.get().getInformation() != null)) {
				pokemonActualizado.setInformation(pokemonOpt.get().getInformation());
			}
			if((pokemonActualizado.getEntrenador() == null) && (pokemonOpt.get().getEntrenador() != null)) {
				pokemonActualizado.setEntrenador(pokemonOpt.get().getEntrenador());
			}
			
			return pokemonRepository.save(pokemonActualizado);
			
		}else {
			log.info("El pokemon no existe");
			return null;
		}
	}
	
	/**
	 * 
	 * @param Id
	 * @return verdadero si se borro bien o falso en caso contrario
	 */
	public boolean delete(String Id){
		
		log.info("Borrando pokemon con matricula "+Id);
		try {
			pokemonRepository.deleteById(Id);
			return true;
		}catch(Exception e){
			log.info("Algo salio mal");
			e.getMessage();
			return false;
		}	
	}
	
	/**
	 * 
	 * @param Id
	 * @return true si existe, false en caso contrario
	 */
	public boolean exist(String Id){
		log.info("Revisando existencia del pokemon con matricula "+Id);
		return pokemonRepository.existsById(Id);
	}
	
	/**
	 * RELLENAR BASE DE DATOS
	 * 
	 */
	
	public List<Pokemon> rellena(){
		log.info("Rellenando");
		List <Pokemon> pokemons = new ArrayList<>();
		
		Information information = new Information();
		
		Pokemon pokemon = new Pokemon();
		pokemon.setPokemonId("BUKA001200201");
		pokemon.setStatus("adopcion");
		pokemon.setInformation(information);
		pokemon.getInformation().setName("Bulbasaur");
		pokemon.getInformation().setPokedex("001");
		pokemon.getInformation().setImage("https://i.pinimg.com/originals/d6/61/96/d66196beb60d306a966ea39ed11c2b3d.png");
		pokemon.getInformation().setDescription("Puede sobrevivir largo tiempo sin probar bocado gracias a los nutrientes que guarda en el bulbo del lomo");
		pokemon.getInformation().setGender("masculino");
		pokemon.getInformation().setLevel("5");
		pokemon.getInformation().setNature("Hurania");
		pokemon.getInformation().setObjeto("carameloraro");
		pokemon.getInformation().setRegion("Kanto");
		pokemon.getInformation().setFirst("Planta");
		pokemon.getInformation().setSecond("Veneno");
		
		pokemons.add(pokemon);
		
		Information information1 = new Information();
		Pokemon pokemon1 = new Pokemon();
		pokemon1.setPokemonId("GEKA074200312");
		pokemon1.setStatus("adopcion");
		pokemon1.setInformation(information1);
		pokemon1.getInformation().setName("Geodude");
		pokemon1.getInformation().setPokedex("074");
		pokemon1.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/1/12/latest/20161018163121/Geodude.png");
		pokemon1.getInformation().setDescription("Los Geodude habitan en las montañas que escalan a pulso con sus fuertes brazos. Cuando quieren dormir, se entierran a medias en el suelo, y por mucho que lo pisen mientras duerme, no se despertará.");
		pokemon1.getInformation().setGender("macho");
		pokemon1.getInformation().setLevel("32");
		pokemon1.getInformation().setNature("Seria");
		pokemon1.getInformation().setObjeto("ninguno");
		pokemon1.getInformation().setRegion("kanto");
		pokemon1.getInformation().setFirst("Roca");
		pokemon1.getInformation().setSecond("Tierra");
		
		pokemons.add(pokemon1);	

		Information information2 = new Information();
		Pokemon pokemon2 = new Pokemon();
		pokemon2.setPokemonId("SNKA142194502");
		pokemon2.setStatus("cuidado");
		pokemon2.setInformation(information2);
		pokemon2.getInformation().setName("Snorlax");
		pokemon2.getInformation().setPokedex("143");
		pokemon2.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/0/0b/latest/20160904204605/Snorlax.png");
		pokemon2.getInformation().setDescription("Snorlax come muchísimos kilos de comida al día, lo único que hará será echarse a dormir hasta que vuelva a despertarse por el hambre");
		pokemon2.getInformation().setGender("macho");
		pokemon2.getInformation().setLevel("15");
		pokemon2.getInformation().setNature("Placido");
		pokemon2.getInformation().setObjeto("ninguno");
		pokemon2.getInformation().setRegion("kanto");
		pokemon2.getInformation().setFirst("Normal");
		pokemon2.getInformation().setSecond("none");
		
		pokemons.add(pokemon2);

		Information information3 = new Information();
		Pokemon pokemon3 = new Pokemon();
		pokemon3.setPokemonId("WOJO194200605");
		pokemon3.setStatus("adopcion");
		pokemon3.setInformation(information3);
		pokemon3.getInformation().setName("Wooper");
		pokemon3.getInformation().setPokedex("194");
		pokemon3.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/4/42/latest/20160703191603/Wooper.png");
		pokemon3.getInformation().setDescription("No tiene manos, tiene una cola parecida al de un castor que utiliza para nadar. Las estructuras a lado y lado de su cabeza que parecen flechas son en realidad sus branquias, las cuales usa para poder respirar mientras se encuentra en el agua.");
		pokemon3.getInformation().setGender("hembra");
		pokemon3.getInformation().setLevel("21");
		pokemon3.getInformation().setNature("Cauta");
		pokemon3.getInformation().setObjeto("ninguno");
		pokemon3.getInformation().setRegion("Johto");
		pokemon3.getInformation().setFirst("Agua");
		pokemon3.getInformation().setSecond("Tierra");
		
		pokemons.add(pokemon3);	

		Information information4 = new Information();
		Pokemon pokemon4 = new Pokemon();
		pokemon4.setPokemonId("DRJO149200714");
		pokemon4.setStatus("liberado");
		pokemon4.setInformation(information4);
		pokemon4.getInformation().setName("Dragonite");
		pokemon4.getInformation().setPokedex("149");
		pokemon4.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/a/a6/latest/20151017110809/Dragonite.png");
		pokemon4.getInformation().setDescription("Dragonite es un Pokémon dragón, conocido como el Avatar del Mar. Posee un amplio set de movimientos, puede volar, lanzar fuego, agua, hielo y/o electricidad. Tiene una inteligencia cercana a la humana.");
		pokemon4.getInformation().setGender("Macho");
		pokemon4.getInformation().setLevel("45");
		pokemon4.getInformation().setNature("Gosera");
		pokemon4.getInformation().setObjeto("ninguno");
		pokemon4.getInformation().setRegion("Johto");
		pokemon4.getInformation().setFirst("Dragon");
		pokemon4.getInformation().setSecond("Volador");
		
		pokemons.add(pokemon4);	
		
		Information information5 = new Information();
		Pokemon pokemon5 = new Pokemon();
		pokemon5.setPokemonId("MAKA18320190603");
		pokemon5.setStatus("liberado");
		pokemon5.setInformation(information5);
		pokemon5.getInformation().setName("Marill");
		pokemon5.getInformation().setPokedex("183");
		pokemon5.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/5/55/latest/20160309224636/Marill.png");
		pokemon5.getInformation().setDescription("Marill tiene la apariencia de un ratón de agua azul. Es un Pokémon amistoso, redondo y azul. Este Pokémon de agua es ideal para principiantes por su fácil crianza.");
		pokemon5.getInformation().setGender("Hembra");
		pokemon5.getInformation().setLevel("30");
		pokemon5.getInformation().setNature("Amistoso");
		pokemon5.getInformation().setObjeto("ninguno");
		pokemon5.getInformation().setRegion("Kalos");
		pokemon5.getInformation().setFirst("Agua");
		pokemon5.getInformation().setSecond("Hada");
		
		pokemons.add(pokemon5);	
		
		Information information6 = new Information();
		Pokemon pokemon6 = new Pokemon();
		pokemon6.setPokemonId("LUKA448200807");
		pokemon6.setStatus("cuidado");
		pokemon6.setInformation(information6);
		pokemon6.getInformation().setName("Lucario");
		pokemon6.getInformation().setPokedex("448");
		pokemon6.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/d/d0/latest/20150621180604/Lucario.png");
		pokemon6.getInformation().setDescription("Es un Pokémon fiel a su entrenador, se preocupa por él, pero también tiene un carácter duro y serio. Por ello, puede no resultar un pokemon amigable a primera vista. Aun así, los Lucario tienen un gran sentido de la justicia.");
		pokemon6.getInformation().setGender("Macho");
		pokemon6.getInformation().setLevel("60");
		pokemon6.getInformation().setNature("Serio");
		pokemon6.getInformation().setObjeto("ninguno");
		pokemon6.getInformation().setRegion("Kalos");
		pokemon6.getInformation().setFirst("Luchador");
		pokemon6.getInformation().setSecond("Acero");
		
		pokemons.add(pokemon6);	
		
		Information information7 = new Information();
		Pokemon pokemon7 = new Pokemon();
		pokemon7.setPokemonId("LIAL725200606");
		pokemon7.setStatus("adopcion");
		pokemon7.setInformation(information7);
		pokemon7.getInformation().setName("Litten");
		pokemon7.getInformation().setPokedex("725");
		pokemon7.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/6/6e/latest/20170823184241/Litten.png");
		pokemon7.getInformation().setDescription("Litten está basado en un gato, su pelaje es muy graso y altamente inflamable. Se dice que el abrigo que cubre su piel puede crecer hasta dos veces al añ");
		pokemon7.getInformation().setGender("Macho");
		pokemon7.getInformation().setLevel("26");
		pokemon7.getInformation().setNature("Amistoso");
		pokemon7.getInformation().setObjeto("ninguno");
		pokemon7.getInformation().setRegion("Alola");
		pokemon7.getInformation().setFirst("Fuego");
		pokemon7.getInformation().setSecond("none");
		
		pokemons.add(pokemon7);	
		
		Information information8 = new Information();
		Pokemon pokemon8 = new Pokemon();
		pokemon8.setPokemonId("MAAL081200701");
		pokemon8.setStatus("liberado");
		pokemon8.setInformation(information8);
		pokemon8.getInformation().setName("Magnemite");
		pokemon8.getInformation().setPokedex("081");
		pokemon8.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/8/80/latest/20080822131212/Magnemite.png");
		pokemon8.getInformation().setDescription("Magnemite es un Pokémon de carácter inorgánico. Su cuerpo está construido de materiales superconductores de campos magnéticos así como almacenes de energía.");
		pokemon8.getInformation().setGender("Indefinido");
		pokemon8.getInformation().setLevel("23");
		pokemon8.getInformation().setNature("Serio");
		pokemon8.getInformation().setObjeto("ninguno");
		pokemon8.getInformation().setRegion("Alola");
		pokemon8.getInformation().setFirst("Eléctrico");
		pokemon8.getInformation().setSecond("Acero");
		
		pokemons.add(pokemon8);	
		Information information9 = new Information();
		Pokemon pokemon9 = new Pokemon();
		pokemon9.setPokemonId("PAGA674190208");
		pokemon9.setStatus("adopcion");
		pokemon9.setInformation(information9);
		pokemon9.getInformation().setName("Pancham");
		pokemon9.getInformation().setPokedex("674");
		pokemon9.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/c/c9/latest/20190425222516/Pancham.png");
		pokemon9.getInformation().setDescription("Pancham está basado en una cría de panda gigante, además muestra lo que parece ser una hoja de bambú en su boca, principal alimento del oso panda.");
		pokemon9.getInformation().setGender("Hembra");
		pokemon9.getInformation().setLevel("08");
		pokemon9.getInformation().setNature("Picaro");
		pokemon9.getInformation().setObjeto("ninguno");
		pokemon9.getInformation().setRegion("Galar");
		pokemon9.getInformation().setFirst("Lucha");
		pokemon9.getInformation().setSecond("none");
		
		pokemons.add(pokemon9);	
		Information information10 = new Information();
		Pokemon pokemon10 = new Pokemon();
		pokemon10.setPokemonId("WOGA202200609");
		pokemon10.setStatus("adopcion");
		pokemon10.setInformation(information10);
		pokemon10.getInformation().setName("Wobbuffet");
		pokemon10.getInformation().setPokedex("202");
		pokemon10.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/c/cf/latest/20160625152946/Wobbuffet.png");
		pokemon10.getInformation().setDescription(" Parece estar basado en un punching-ball o saco de boxeo, debido a su forma y su costumbre de aguantar golpes, y es un Pokémon algo torpe.");
		pokemon10.getInformation().setGender("Macho");
		pokemon10.getInformation().setLevel("05");
		pokemon10.getInformation().setNature("Curioso");
		pokemon10.getInformation().setObjeto("ninguno");
		pokemon10.getInformation().setRegion("Galar");
		pokemon10.getInformation().setFirst("Psíquico");
		pokemon10.getInformation().setSecond("none");
		
		pokemons.add(pokemon10);
		Information information11 = new Information();
		Pokemon pokemon11 = new Pokemon();
		pokemon11.setPokemonId("SQKA007200201");
		pokemon11.setStatus("adopcion");
		pokemon11.setInformation(information11);
		pokemon11.getInformation().setName("Squirtle");
		pokemon11.getInformation().setPokedex("007");
		pokemon11.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/e/e3/latest/20160309230820/Squirtle.png");
		pokemon11.getInformation().setDescription("Cuando se esconde en el caparazón, dispara agua a una presión increíble.");
		pokemon11.getInformation().setGender("Macho");
		pokemon11.getInformation().setLevel("9");
		pokemon11.getInformation().setNature("mansa");
		pokemon11.getInformation().setObjeto("bayamarga");
		pokemon11.getInformation().setRegion("Kanto");
		pokemon11.getInformation().setFirst("agua");
		pokemon11.getInformation().setSecond("none");

		pokemons.add(pokemon11);	
		Information information12 = new Information();
	Pokemon pokemon12 = new Pokemon();
	pokemon12.setPokemonId("TOHO255200201");
	pokemon12.setStatus("cuidado");
	pokemon12.setInformation(information12);
	pokemon12.getInformation().setName("Torchic");
	pokemon12.getInformation().setPokedex("255");
	pokemon12.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/4/4f/latest/20140612153748/Torchic.png");
	pokemon12.getInformation().setDescription("En su interior, guarda una llama que arde sin cesar. Si se le abraza, se nota que tiene una temperatura muy alta.");
	pokemon12.getInformation().setGender("Hembra");
	pokemon12.getInformation().setLevel("3");
	pokemon12.getInformation().setNature("huraña");
	pokemon12.getInformation().setObjeto("none");
	pokemon12.getInformation().setRegion("Hoenn");
	pokemon12.getInformation().setFirst("fuego");
	pokemon12.getInformation().setSecond("none");

	pokemons.add(pokemon12);
	Information information13 = new Information();
	Pokemon pokemon13 = new Pokemon();
	pokemon13.setPokemonId("CHSI390200201");
	pokemon13.setStatus("adopcion");
	pokemon13.setInformation(information13);
	pokemon13.getInformation().setName("Chimchar");
	pokemon13.getInformation().setPokedex("390");
	pokemon13.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/9/9f/latest/20120927233211/Chimchar.png");
	pokemon13.getInformation().setDescription("El gas de su panza alimenta el fuego de su parte trasera, que ni la lluvia puede extinguir.");
	pokemon13.getInformation().setGender("Macho");
	pokemon13.getInformation().setLevel("4");
	pokemon13.getInformation().setNature("agitada");
	pokemon13.getInformation().setObjeto("carameloraro");
	pokemon13.getInformation().setRegion("Sinnoh");
	pokemon13.getInformation().setFirst("fuego");
	pokemon13.getInformation().setSecond("none");

	pokemons.add(pokemon13);	
	Information information14 = new Information();
	Pokemon pokemon14 = new Pokemon();
	pokemon14.setPokemonId("OSTE501200201");
	pokemon14.setStatus("liberado");
	pokemon14.setInformation(information14);
	pokemon14.getInformation().setName("Oshawott");
	pokemon14.getInformation().setPokedex("501");
	pokemon14.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/9/90/Oshawott_(2010).png");
	pokemon14.getInformation().setDescription("La vieira de su ombligo está hecha del mismo material que sus garras. Puede sacarla y usarla de daga.");
	pokemon14.getInformation().setGender("Macho");
	pokemon14.getInformation().setLevel("4");
	pokemon14.getInformation().setNature("activa");
	pokemon14.getInformation().setObjeto("concha");
	pokemon14.getInformation().setRegion("Teselia");
	pokemon14.getInformation().setFirst("agua");
	pokemon14.getInformation().setSecond("none");

	pokemons.add(pokemon14);
	Information information15 = new Information();
	Pokemon pokemon15 = new Pokemon();
	pokemon15.setPokemonId("FEKA653200201");
	pokemon15.setStatus("adopcion");
	pokemon15.setInformation(information15);
	pokemon15.getInformation().setName("Fennekin");
	pokemon15.getInformation().setPokedex("653");
	pokemon15.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/thumb/1/11/latest/20190430161933/Fennekin.png/200px-Fennekin.png");
	pokemon15.getInformation().setDescription("Tras masticar e ingerir pequeñas ramas se siente pletórico y expulsa aire caliente por sus grandes orejas a temperaturas superiores a los 200 C.");
	pokemon15.getInformation().setGender("Hembra");
	pokemon15.getInformation().setLevel("4");
	pokemon15.getInformation().setNature("agitada");
	pokemon15.getInformation().setObjeto("none");
	pokemon15.getInformation().setRegion("Kalos");
	pokemon15.getInformation().setFirst("fuego");
	pokemon15.getInformation().setSecond("none");

	pokemons.add(pokemon15);	
	Information information16 = new Information();
	Pokemon pokemon16 = new Pokemon();
pokemon16.setPokemonId("LIAL725200201");
pokemon16.setStatus("especialidad");
pokemon16.setInformation(information16);
pokemon16.getInformation().setName("Litten");
pokemon16.getInformation().setPokedex("725");
pokemon16.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/thumb/6/6e/latest/20170823184241/Litten.png/200px-Litten.png");
pokemon16.getInformation().setDescription("Prende las bolas de pelo que se forman en su estómago tras acicalarse. Las llamas que escupe tienen formas variopintas.");
pokemon16.getInformation().setGender("Macho");
pokemon16.getInformation().setLevel("10");
pokemon16.getInformation().setNature("ingenua");
pokemon16.getInformation().setObjeto("carameloraro");
pokemon16.getInformation().setRegion("Alola");
pokemon16.getInformation().setFirst("fuego");
pokemon16.getInformation().setSecond("none");

pokemons.add(pokemon16);	
Information information17 = new Information();
Pokemon pokemon17 = new Pokemon();
pokemon17.setPokemonId("SCGA813200201");
pokemon17.setStatus("adopcion");
pokemon17.setInformation(information17);
pokemon17.getInformation().setName("Scorbunny");
pokemon17.getInformation().setPokedex("813");
pokemon17.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/thumb/c/cc/latest/20190816174326/Scorbunny.png/200px-Scorbunny.png");
pokemon17.getInformation().setDescription("Se pone a correr para elevar su temperatura corporal y propagar la energía ígnea por todo el cuerpo. Desata así su verdadera fuerza.");
pokemon17.getInformation().setGender("Macho");
pokemon17.getInformation().setLevel("9");
pokemon17.getInformation().setNature("afable");
pokemon17.getInformation().setObjeto("none");
pokemon17.getInformation().setRegion("Galar");
pokemon17.getInformation().setFirst("fuego");
pokemon17.getInformation().setSecond("none");

pokemons.add(pokemon17);	
Information information18 = new Information();
Pokemon pokemon18 = new Pokemon();
pokemon18.setPokemonId("LIKA667200201");
pokemon18.setStatus("cuidado");
pokemon18.setInformation(information18);
pokemon18.getInformation().setName("Litleo");
pokemon18.getInformation().setPokedex("667");
pokemon18.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/thumb/c/c7/latest/20130614124212/Litleo.png/200px-Litleo.png");
pokemon18.getInformation().setDescription("Cuanto más poderoso es el enemigo al que se enfrenta, más calor desprende su mechón y más energía recorre todo su cuerpo.");
pokemon18.getInformation().setGender("Macho");
pokemon18.getInformation().setLevel("11");
pokemon18.getInformation().setNature("juguetona");
pokemon18.getInformation().setObjeto("none");
pokemon18.getInformation().setRegion("Kalos");
pokemon18.getInformation().setFirst("fuego");
pokemon18.getInformation().setSecond("normal");

pokemons.add(pokemon18);	
Information information19 = new Information();
Pokemon pokemon19 = new Pokemon();
pokemon19.setPokemonId("ROAL722200201");
pokemon19.setStatus("adopcion");
pokemon19.setInformation(information19);
pokemon19.getInformation().setName("Rowlett");
pokemon19.getInformation().setPokedex("722");
pokemon19.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/thumb/f/ff/latest/20170823183914/Rowlet.png/200px-Rowlet.png");
pokemon19.getInformation().setDescription("Es cauteloso, desconfiado y de naturaleza nocturna. Durante el día acumula energía mediante la fotosíntesis.");
pokemon19.getInformation().setGender("Macho");
pokemon19.getInformation().setLevel("12");
pokemon19.getInformation().setNature("agitada");
pokemon19.getInformation().setObjeto("bayamarga");
pokemon19.getInformation().setRegion("Alola");
pokemon19.getInformation().setFirst("planta");
pokemon19.getInformation().setSecond("volador");

pokemons.add(pokemon19);	
Information information20 = new Information();
Pokemon pokemon20 = new Pokemon();
pokemon20.setPokemonId("MUHO258200201");
pokemon20.setStatus("adopcion");
pokemon20.setInformation(information20);
pokemon20.getInformation().setName("Mudkip");
pokemon20.getInformation().setPokedex("258");
pokemon20.getInformation().setImage("https://images.wikidexcdn.net/mwuploads/wikidex/a/ae/Mudkip.png");
pokemon20.getInformation().setDescription("Tiene una larga cola que le propulsa por el agua con mucha potencia. A pesar de ser pequeño, tiene mucha fuerza.");
pokemon20.getInformation().setGender("Macho");
pokemon20.getInformation().setLevel("3");
pokemon20.getInformation().setNature("jugueton");
pokemon20.getInformation().setObjeto("none");
pokemon20.getInformation().setRegion("Hoenn");
pokemon20.getInformation().setFirst("agua");
pokemon20.getInformation().setSecond("none");

pokemons.add(pokemon20);
Information information21 = new Information();
Pokemon pokemon21 = new Pokemon();
pokemon21.setPokemonId("GEKA094200201");
pokemon21.setStatus("adopcion");
pokemon21.setInformation(information21);
pokemon21.getInformation().setName("Gengar");
pokemon21.getInformation().setPokedex("094");
pokemon21.getInformation().setImage("https://pm1.narvii.com/6148/b648df6e769945f1a4b06cee52f0d892e92ac883_hq.jpg");
pokemon21.getInformation().setDescription("Se dice que por las noches sale a espantar y perder a los viajeros para robar su alma");
pokemon21.getInformation().setGender("Hembra");
pokemon21.getInformation().setLevel("49");
pokemon21.getInformation().setNature("Agresiva");
pokemon21.getInformation().setObjeto("Restos");
pokemon21.getInformation().setRegion("Kanto");
pokemon21.getInformation().setFirst("Fantasma");
pokemon21.getInformation().setSecond("Veneno");

pokemons.add(pokemon21);

Information information22 = new Information();
Pokemon pokemon22 = new Pokemon();
pokemon22.setPokemonId("AEKA142200201");
pokemon22.setStatus("adopcion");
pokemon22.setInformation(information22);
pokemon22.getInformation().setName("Aerodactyl");
pokemon22.getInformation().setPokedex("142");
pokemon22.getInformation().setImage("https://assets.pokemon.com/assets/cms2/img/pokedex/full/142.png");
pokemon22.getInformation().setDescription("Sus alas tienen potentes músculos para poder volar, sin embargo, no era muy buen volador por lo que le resultaba difícil atrapar Pokémon al vuelo");
pokemon22.getInformation().setGender("Macho");
pokemon22.getInformation().setLevel("35");
pokemon22.getInformation().setNature("Hostil");
pokemon22.getInformation().setObjeto("Bayaframbu");
pokemon22.getInformation().setRegion("Kanto");
pokemon22.getInformation().setFirst("Roca");
pokemon22.getInformation().setSecond("Volador");

pokemons.add(pokemon22);
Information information23 = new Information();
Pokemon pokemon23 = new Pokemon();
pokemon23.setPokemonId("TOJO158200201");
pokemon23.setStatus("adopcion");
pokemon23.setInformation(information23);
pokemon23.getInformation().setName("Totodile");
pokemon23.getInformation().setPokedex("158");
pokemon23.getInformation().setImage("https://assets.pokemon.com/assets/cms2/img/pokedex/full/158.png");
pokemon23.getInformation().setDescription("Totodile tiene una gran y prominente quijada, llena de muchos dientes agudos, con la que puede incluso romper rocas pequeñas");
pokemon23.getInformation().setGender("Macho");
pokemon23.getInformation().setLevel("12");
pokemon23.getInformation().setNature("Alegre");
pokemon23.getInformation().setObjeto("Bayapiña");
pokemon23.getInformation().setRegion("Johto");
pokemon23.getInformation().setFirst("Agua");
pokemon23.getInformation().setSecond("none");

pokemons.add(pokemon23);
Information information24 = new Information();
Pokemon pokemon24= new Pokemon();
pokemon24.setPokemonId("SNTE495200201");
pokemon24.setStatus("adopcion");
pokemon24.setInformation(information24);
pokemon24.getInformation().setName("Snivy");
pokemon24.getInformation().setPokedex("495");
pokemon24.getInformation().setImage("https://assets.pokemon.com/assets/cms2/img/pokedex/full/495.png");
pokemon24.getInformation().setDescription("Su cuerpo es predominantemente de color verde. Sobre sus brazos cuenta con dos estructuras curvadas de un color amarillo más vivo que el de la franja que recorre su cuerpo");
pokemon24.getInformation().setGender("Hembra");
pokemon24.getInformation().setLevel("8");
pokemon24.getInformation().setNature("Hostil");
pokemon24.getInformation().setObjeto("Pañuelo elegido");
pokemon24.getInformation().setRegion("Teselia");
pokemon24.getInformation().setFirst("Planta");
pokemon24.getInformation().setSecond("none");

pokemons.add(pokemon24);


Information information25 = new Information();

Pokemon pokemon25= new Pokemon();
pokemon25.setPokemonId("MEKA150200909");
pokemon25.setStatus("adopcion");
pokemon25.setInformation(information25);
pokemon25.getInformation().setName("Mewtwo");
pokemon25.getInformation().setPokedex("150");
pokemon25.getInformation().setImage("https://img2.wikia.nocookie.net/__cb20121223053851/sonicpokemon/images/d/d3/Mewtwo.png");
pokemon25.getInformation().setDescription("Mewtwo es el resultado de las ambiciones de los seres humanos en la creación de una reproducción genéticamente realzada (o más bien mejorada ) de Mew");
pokemon25.getInformation().setGender("Macho");
pokemon25.getInformation().setLevel("60");
pokemon25.getInformation().setNature("Agresivo");
pokemon25.getInformation().setObjeto("Gema");
pokemon25.getInformation().setRegion("kanto");
pokemon25.getInformation().setFirst("Psiquico");
pokemon25.getInformation().setSecond("none");

pokemons.add(pokemon25);
Information information26 = new Information();
Pokemon pokemon26= new Pokemon();
pokemon26.setPokemonId("DEHO386200201");
pokemon26.setStatus("adopcion");
pokemon26.setInformation(information26);
pokemon26.getInformation().setName("Deoxys");
pokemon26.getInformation().setPokedex("386");
pokemon26.getInformation().setImage("https://assets.pokemon.com/assets/cms2/img/pokedex/full/386_f2.png");
pokemon26.getInformation().setDescription("Deoxys es una mutacion de un virus espacial.La esfera en el cuerpo de este es en realidad su cerebro, de donde lanza descargas eléctricas");
pokemon26.getInformation().setGender("Macho");
pokemon26.getInformation().setLevel("60");
pokemon26.getInformation().setNature("Agresivo");
pokemon26.getInformation().setObjeto("Pañuelo elegido");
pokemon26.getInformation().setRegion("Hoenn");
pokemon26.getInformation().setFirst("Psiquico");
pokemon26.getInformation().setSecond("none");

log.info("Agregando a lista");
pokemons.add(pokemon26);
		
log.info("Persistiendo");
		for(int i=0; i<pokemons.size(); i++) {
			pokemonRepository.save(pokemons.get(i));
		}
		
		return pokemons;
	}
	
}
