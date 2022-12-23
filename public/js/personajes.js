$(document).ready(function () {

    if(sessionStorage.getItem('mailUsuario') != null){
        $.ajax({
            type: 'GET',
            url: 'dsaApp/gameManager/characters',
            data: {},
            dataType: 'text',
            success: function (data) {
                var json = $.parseJSON(data);
                $('#results').empty();
                for (var i = 0; i < json.length; ++i) {
                    $('#results').append('<tr>' +
                        '<td>' + json[i].nameCharacter + '</td>' +
                        '<td>' + json[i].descriptionCharacter + '</td>' +
                        '<td>' + json[i].coinsCharacter + '</td>' +
                        '</tr>');
                }

            },
            error: function (error) {
                alert("Not able to create the table. Try again");
            }
        });

        const tableCells = document.querySelectorAll("#tablePersonajes");

        tableCells.forEach(function (cell){
            cell.addEventListener("click", function (){
                // obtenemos la celda en la que se hizo clic
                var celda = event.target;

                // obtenemos el contenido de la celda
                var contenidoPersonajes = celda.innerHTML;


                localStorage.setItem('personajeElegido',contenidoPersonajes);

                window.location.href="objetos.html";
            });
        });
    }else {
        window.location.href="index.html";
    }



});