
    //Variables globales
    
    var URL = "http://localhost:8080/pokemons";
    
    var pokemonId="";
	var status="";
	var nombre="";
	var sexo="";
	var nature="";
	var firstType="";
	var secondType="";
	var region="";
    
    //Comienza codigo para regresar a todos los pokemones
    
    function cargaTodos(){
    	
    		$.ajaxSetup({contentType: "application/json; charset=utf-8"});
       		
       		console.log("Ready");
	       	
       		$.get(URL,cargaGrupo);
       		
	       	function cargaGrupo(Grupos){
	          					
		          muestraPokes(Grupos);
		          		
		     }
    }
    
    //Busca pokemones de acuero a los filtros
    
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
        		}else if(nombre != "" && nombre != null){//no se esta buscando por estatus
        			//Entro entonces se esta buscando por nombre
        			if(nombre == element.name){
        				lista.push(Grupos[i]);
        				
        			}
        		}else if(sexo != "" && sexo != null){//no se esta buscando por nombre
        			//Entro entonces se esta buscando por sexo
        			if(nombre == element.gender){
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
        		}else if(firstType != "" && firstType != null){//no se esta buscando por region
        			//Entro entonces se esta buscando por primer tipo
        			if(firstType == element.first){
        				lista.push(Grupos[i]);
        				
        			}
        		}else if(secondType != "" && secondType != null){//no se esta buscando por primer tipo
        			//Entro entonces se esta buscando por segundo tipo
        			if(secondType == element.second){
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
    
    //Comienza codigo para buscar pokemones
	
	function guarda(type) {
		  
		 if (type == "Id") {
			pokemonId = document.getElementById("idPokemonE").value;
		    console.log(this.pokemonId);
		 }
		 if (type == "Np") {
		  	nombre = document.getElementById("nombrePokemonE").value;
	    	console.log(this.nombre);
	  	 }
		 if (type == "St") {
				status = document.getElementById("estatusPokemonE").value;
			    console.log(this.status);
			 }
		 if (type == "Sx") {
			sexo = document.getElementById("sexoPokemonE").value;
		    console.log(this.sexo);
		 }
		 if (type == "Na") {
				nature = document.getElementById("naturalezaPokemonE").value;
			    console.log(this.nature);
		 }
		 if (type == "Ft") {
				firstType = document.getElementById("primerTipoE").value;
			    console.log(this.firstType);
		 }
		 if (type == "St") {
				secondType = document.getElementById("segundoTipoE").value;
			    console.log(this.secondType);
		 }
		 if (type == "Re") {
				region = document.getElementById("regionPokemonE").value;
			    console.log(this.region);
		 }
	}
