function comprar(idObjeto) {
    $.ajax({
        contentType: 'application/json',
        type: 'PUT',
        url: 'dsaApp/gameManager/user/' + sessionStorage.getItem("mailUsuario") + '/' + idObjeto,
        dataType: 'text',
        success: function (result) {
            alert("Object bought successfully");
        },
        error: function (error) {
            alert("Not enough coins to buy the object");
        }
    });
}

$(document).ready(function () {

    if(sessionStorage.getItem('mailUsuario') != null){
        $('#back').click(function () {
            window.location.href='usuario.html';
        });

        $.ajax({
            type: 'GET',
            url: 'dsaApp/gameManager/myObjects',
            dataType: 'text',
            success: function (data) {
                var json = $.parseJSON(data);
                $('#results').empty();
                for (var i = 0; i < json.length; ++i) {
                    $('#results').append('<tr id="elementos">' +
                        '<td>' + json[i].objectName + '</td>' +
                        '<td>' + json[i].objectDescription + '</td>' +
                        '<td>' + json[i].objectCoins + '</td>' +
                        '<td>' + json[i].objectTypeId + '</td>' +
                        '<td><a href="#" onclick="comprar('+json[i].objectId+')">Comprar</a></td>' +
                        '</tr>');

                }
            },
            error: function (error) {
                alert("Not able to create the table. Try again");
            }
        });
    }else {
        window.location.href="index.html";
    }
});