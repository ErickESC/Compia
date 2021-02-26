
    //Variables globales
    
    var URL = "http://localhost:8080/pokemons";
    
    var pokemonId="";
	var status="";
	var name="";
	var gender="";
	var nature="";
	var first="";
	var second="";
	var region="";
	
	var image="";
	var level="";
	var pokedex="";
	var description="";
	var objeto="";
	
	var entrenador = null;
	
	var information = [];
    
    //guardar los imputs en variables
    
    function guarda(type) {
		  
		 if (type == "Id") {
			pokemonId = document.getElementById("idPokemonE").value;
		    console.log(this.pokemonId);
		 }
		 if (type == "Np") {
		  	name = document.getElementById("nombrePokemonE").value;
	    	console.log(this.name);
	  	 }
		 if (type == "St") {
				status = document.getElementById("estatusPokemonE").value;
			    console.log(this.status);
			 }
		 if (type == "Sx") {
			    gender = document.getElementById("sexoPokemonE").value;
		        console.log(this.gender);
		 }
		 if (type == "Na") {
				nature = document.getElementById("naturalezaPokemonE").value;
			    console.log(this.nature);
		 }
		 if (type == "Ft") {
				first = document.getElementById("primerTipoE").value;
			    console.log(this.first);
		 }
		 if (type == "St") {
				second = document.getElementById("segundoTipoE").value;
			    console.log(this.second);
		 }
		 if (type == "Re") {
				region = document.getElementById("regionPokemonE").value;
			    console.log(this.region);
		 }
		 if (type == "Im") {
				image = document.getElementById("imagePokemonE").value;
			    console.log(this.image);
		 }
		 if (type == "lev") {
				level = document.getElementById("levelPokemonE").value;
			    console.log(this.level);
		 }
		 if (type == "Pkx") {
				pokedex = document.getElementById("pokedex").value;
			    console.log(this.pokedex);
		 }
         if (type == "Desc") {
				description = document.getElementById("descripcionPokemonE").value;
			    console.log(this.description);
		 }
         if (type == "Ob") {
				objeto = document.getElementById("objetoPokemonE").value;
			    console.log(this.objeto);
		 }         
	}
    
    //Codigo para guardar un pokemon en la BD
    function registraPokemon(){
    	
    		$.ajaxSetup({contentType: "application/json; charset=utf-8"});
       		
       		var pokemon = JSON.stringify({ 
       										pokemonId: this.pokemonId, 
       										status: this.status,
       										entrenador: this.entrenador, 
       										information :{ name: this.name,
       													   image: this.image,
       													   level: this.level,
       													   gender: this.gender,
       													   objeto: this.objeto,
       													   description: this.description,
       													   nature: this.nature,
       													   pokedex: this.pokedex,
       													   region: this.region,
       													   first: this.first,
       													   second: this.second
       													  } 
       									  });
   				
   				//Imprimimos en consola el json generado
   				console.log(pokemon);
   				
   				$.ajax({url: URL, 
 		    		method: 'POST',
 		    		data: pokemon,
 		    		success: alert("Se guardo con exito")
 		    		});
    }
    
    //Comienza codigo para regresar a todos los pokemones
    
    function cargaTodos(){
    	
    		$.ajaxSetup({contentType: "application/json; charset=utf-8"});
       		
       		console.log("Ready");
	       	
       		$.get(URL,cargaGrupo);
       		
	       	function cargaGrupo(Grupos){
	          					
		          muestraPokes(Grupos);
		          		
		    }
    }
    
    //Busca pokemones de acuerdo a los filtros
    
    function busca(){
    	
		$.ajaxSetup({contentType: "application/json; charset=utf-8"});

   		var lista=[];
   		
   		console.log("Ready");
       	
   		$.get(URL,aplicaFiltro);
   		
       	function aplicaFiltro(Grupos){
          	
       		for (i=0;i<Grupos.length;i++) {
         		var element = Grupos[i].information;
        		var pokemon = Grupos[i];
        		
        		//Los pasamos por los filtros
        		if(pokemonId != "" && pokemonId != null){
        			if(pokemonId == pokemon.pokemonId){
        				lista.push(Grupos[i]);
        				
        			}
        		}
        		if(status != "" && status != null){
        			//Entro entonces se esta buscando por estatus
        			if(status == pokemon.pokemonId){
        				lista.push(Grupos[i]);
        				
        			}
        		}else if(name != "" && name != null){//no se esta buscando por estatus
        			//Entro entonces se esta buscando por nombre
        			if(name == element.name){
        				lista.push(Grupos[i]);
        				
        			}
        		}else if(gender != "" && gender != null){//no se esta buscando por nombre
        			//Entro entonces se esta buscando por sexo
        			if(gender == element.gender){
        				lista.push(Grupos[i]);
        				
        			}
        		}else if(nature != "" && nature != null){//no se esta buscando por sexo
        			//Entro entonces se esta buscando por naturaleza
        			if(nature == element.nature){
        				lista.push(Grupos[i]);
        				
        			}
        		}else if(region != "" && region != null){//no se esta buscando por naturaleza
        			//Entro entonces se esta buscando por region
        			if(region == element.region){
        				lista.push(Grupos[i]);
        				
        			}
        		}else if(first != "" && first != null){//no se esta buscando por region
        			//Entro entonces se esta buscando por primer tipo
        			if(first == element.first){
        				lista.push(Grupos[i]);
        				
        			}
        		}else if(second != "" && second != null){//no se esta buscando por primer tipo
        			//Entro entonces se esta buscando por segundo tipo
        			if(second == element.second){
        				lista.push(Grupos[i]);
        				
        			}
        		}
       		
       		}
       		
       		
	        muestraPokes(lista);
	          		
	     }
    	
    }
    
    //Codigo para imprimir pokemones en pantalla
    
    function muestraPokes(lista){
    	
    	$('#contenido').empty();
    	$('#entrenadores').empty();
    	
    	// Recorre los objetos
    	for (i=0;i<lista.length;i++) {
     		var element = lista[i].information;
    		var pokemon = lista[i];
     		
     		console.log(element);
     		
     		$("#contenido").append($("<h4 class=\"centrar-texto\">Pokemon</h4>"
     		  +"<div class=\"pokemonAdmin\" id=\""+i+"\">"));
$("#"+i).append($("<div class=\"imagenPokemon\">"
                  +"<img src=\""+element.image+"\" alt=\"\">"
              +"</div>"
              +"<div class=\"datosPokemonE\">"
                  +"<h4 class=\"centrar-texto\">Información del Pokemon</h4>"
                  +"<ul class=\"datosE\">"
                  	+"<li>Status: "+pokemon.status+"</li>"
                  	+"<li>ID: "+pokemon.pokemonId+"</li>"
                  	+"<li>Nombre: "+element.name+"</li>"
                  	+"<li>Sexo: "+element.gender+"</li>"
                  	+"<li>Objeto: "+element.objeto+"</li>"
                  	+"<li>Naturaleza: "+element.nature+"</li>"
                  	+"<li>Descripción: "+element.description+"</li>"
                  	+"<li>Pokedex: "+element.pokedex+"</li>"
                  	+"<li>Región:</li> "+element.region+""
                  	+"<li>Primer tipo: "+element.first+"</li>"
                  	+"<li>Segundo tipo: "+element.second+"</li>"

                  	+"</ul>"
                  	+"</div>"
          +"</div>"));
  		} 
    }
    
    //
    ///////Metodos para busqueda de entrenador
    //
    
    //Variables
    
    var nombre="";
	var mail="";
	var trainerId="";
	var description="";
	var trainerStatus="";
    
    //Guardar inputs en variables js
    
    function guardaTrainerData(type) {
		  
		 if (type == "Tid") {
			trainerId = document.getElementById("idEntrenadorE").value;
		    console.log(this.trainerId);
		 }
		 if (type == "Nom") {
		  	nombre = document.getElementById("nombreEntrenadorE").value;
	    	console.log(this.nombre);
	  	 }
		 if (type == "Mail") {
				mail = document.getElementById("emailEntrenadorE").value;
			    console.log(this.mail);
			 }
		 if (type == "Est") {
			    trainerStatus = document.getElementById("estatusTrabajadorE").value;
		        console.log(this.trainerStatus);
		 }
	}
	
	//Imprime alos entrenadores
	    
	function muestraTrainers(lista){
    	
    	$('#contenido').empty();
    	$('#entrenadores').empty();
    	
    	// Recorre los objetos
    	for (i=0;i<lista.length;i++) {
     		var Estatus = lista[i].soyTrabajador;
     		var pokemons = lista[i].pokemons;
    		var trainer = lista[i];
    		
    		var numPokes;
    		
    		if(pokemons != null){
    			numPokes = pokemons.length;
    		}
     		
     		$("#entrenadores").append($("<div class=\"datosPokemonE\">"
                  +"<h4 class=\"centrar-texto\">Información del Entrenador</h4>"
                  +"<ul class=\"datosE\">"
                  	+"<li>Nombre: "+trainer.nombre+"</li>"
                  	+"<li>ID: "+trainer.id+"</li>"
                  	+"<li>Email: "+trainer.mail+"</li>"
                  	+"<li>Direccion: "+trainer.direccion+"</li>"
                  	+"<li>Estatus: "+Estatus.rank+"</li>"
                  	+"<li>Num. Pokemons: "+numPokes+"</li>"

                  	+"</ul>"
                  	+"</div>"
          +"</div>"));
  		} 
    }
    
    //Busca pokemones de acuerdo a los filtros
    
    function buscaTrainers(){
    	
		$.ajaxSetup({contentType: "application/json; charset=utf-8"});

   		var lista=[];
   		
   		var URLTrainer = "http://localhost:8080/entrenadores";
   		
   		console.log("Ready");
       	
   		$.get(URLTrainer,aplicaFiltro);
   		
       	function aplicaFiltro(Grupos){
          	
       		for (i=0;i<Grupos.length;i++) {
         		var Estatus = Grupos[i].soyTrabajador;
    			var trainer = Grupos[i];
        		
        		//Los pasamos por los filtros
        		if(trainerId != "" && trainerId != null){
        			if(trainerId == trainer.id){
        				lista.push(Grupos[i]);
        			}
        		}else if(nombre != "" && nombre != null){
        			//Entro entonces se esta buscando por nombre
        			if(nombre == trainer.nombre){
        				lista.push(Grupos[i]);
        				
        			}
        		}else if(mail != "" && mail != null){//no se esta buscando por nombre
        			//Entro entonces se esta buscando por mail
        			if(mail == trainer.mail){
        				lista.push(Grupos[i]);
        				
        			}
        		}else if(trainerStatus != "" && trainerStatus != null){//no se esta buscando por mail
        			//Entro entonces se esta buscando por estatus
        			if(trainerStatus == Estatus.rank || Estatus.rank == null){
        				lista.push(Grupos[i]);
        				
        			}
        		}
       		
       		}

	        muestraTrainers(lista);
	          		
	     }
    	
    }
