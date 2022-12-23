$(document).ready(function () {
    if(sessionStorage.getItem('mailUsuario')== null){
        window.location.href="index.html";
    }
});