$(document).ready(function () {
    if(sessionStorage.getItem('mailUsuario')!= null){
        const objetoElegido = localStorage.getItem('objetoElegido');
        //const personajeElegido = localStorage.getItem('personajeElegido');
        document.getElementById('objeto').innerHTML = "Vas a jugar con el objeto: " + objetoElegido;
        //document.getElementById('personaje').innerHTML = "Vas a jugar con el personaje: " + personajeElegido;
    }else {
        window.location.href="index.html";
    }

});