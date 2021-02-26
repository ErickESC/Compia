
    //Variables globales
    
    var URL = "http://localhost:8080/entrenadores";
    
	var name="";
	var apellido = "";
	var mail="";
	var direccion="";
	var codipoPost="";
	var delegacion = "";
	var colonia = "";
	var calle = "";
	var numero = "";
	var trabajo="No reclutado";
    
    //guardar los imputs en variables
    
    function guarda(type) {

		 if (type == "Na") {
		  	name = document.getElementById("nombres").value;
	    	console.log(this.name);
	     }
	     if (type == "apell") {
		  	apellido = document.getElementById("apellidos").value;
	    	console.log(this.apellido);
	  	 }
		 if (type == "Em") {
				mail = document.getElementById("email").value;
			    console.log(this.mail);
		 }
		 if (type == "calle") {
			    calle = document.getElementById("calle").value;
		        console.log(this.calle);
		 }
		 if (type == "col") {
			    colonia = document.getElementById("colonia").value;
		        console.log(this.colonia);
		 }
		 if (type == "numero") {
			    numero = document.getElementById("numeroCalle").value;
		        console.log(this.numero);
		 }
		 if (type == "pass") {
				password = document.getElementById("password").value;
			    console.log(this.password);
		 }
	}
    
    //Metodos para Rellenar la colonia dado el codigo postal
    
    var documento;
	
    $(document).ready(function(){
    	
    	$.ajaxSetup({contentType: "application/json; charset=utf-8"});
	    
	    var URL2 = URL + "/document";
	    
        $.get(URL2,cargaDoc);
       		
	    function cargaDoc(Doc){				
		      documento = Doc;	
		}
   
    });
    
    function rellenaDireccion() {
		
		$('#colonia').empty();
		
		codigoPost = document.getElementById("zip").value;
		console.log(this.codigoPost);
		
		var i;
		
		for(i=1; i<documento.length;i++){
			element = documento[i].split("|");
			
			if(codigoPost == element[0]){
				delegacion = element[3];
				
				var objOption = new Option(element[1], element[1]);
                
                document.getElementById("colonia").add( objOption );
			}
		}
		
		
	}

    
    //Codigo para guardar un Entrenador en la BD
    
    function registra(){
    	
    		$.ajaxSetup({contentType: "application/json; charset=utf-8"});
       		
       		name = name + apellido;
       		
       		direccion = delegacion + "," + colonia + "," + calle + "," + numero;
       		
       		var entrenador = JSON.stringify({ 
       										nombre: this.name,
       										mail: this.mail, 
       										direccion: this.direccion,
       										psswrd: this.password,  
       										soyTrabajador :{ 
       													   rank: this.trabajo
       													   } 
       									    });
   				
   			//Imprimimos en consola el json generado
   			console.log(entrenador);
   				
   			$.ajax({url: URL, 
 		    	method: 'POST',
 		    	data: entrenador,
 		    	success: imprimeID()
 		    	});
    }
    
    //Imprime el ID del entrenador
    
    function imprimeID(trainer) {

		
	}
