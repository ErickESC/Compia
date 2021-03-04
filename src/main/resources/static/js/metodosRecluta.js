
    //Variables globales
    
    var URL = "http://localhost:8080/entrenadores";
    
	var idTrainer="";
	var trabajo="";
    
    //guardar los imputs en variables
    
    function guarda(type) {

		 if (type == "ID") {
		  	idTrainer = document.getElementById("idTrainer").value;
	    	console.log(this.idTrainer);
	     }
	     if (type == "trabajo") {
		  	trabajo = document.getElementById("trabajo").value;
	    	console.log(this.trabajo);
	  	 }
	}
    
    //Codigo para actualizar un Entrenador en la BD
    
    function registra(){
    	
    		$.ajaxSetup({contentType: "application/json; charset=utf-8"});
       		
       		var entrenador = JSON.stringify({ 
       										id: this.idTrainer,
       										nombre: "rand",
       										mail: "rand", 
       										direccion: "rand",
       										psswrd: "rand",  
       										soyTrabajador :{
       													   id: null,
       													   rank: this.trabajo
       													   } 
       									    });
   			
   			var URL2 = URL + "/" + this.idTrainer;
   			
   			$.ajax({url: URL2, 
 		    	method: 'PUT',
 		    	data: entrenador,
 		    	success: alert("Te has reclutado exitosamente")
 		    	});
    }