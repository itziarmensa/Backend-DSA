function comprarObject(idObjeto) {
    $.ajax({
        contentType: 'application/json',
        type: 'PUT',
        url: 'dsaApp/gameManager/user/buyObject/' + sessionStorage.getItem("mailUsuario") + '/' + idObjeto,
        dataType: 'text',
        success: function (result) {
            userCoins();
            alert("Object bought successfully");
        },
        error: function (error) {
            alert("Not enough coins to buy the object");
        }
    });
}

function comprarCharacter(idCharacter) {
    $.ajax({
        contentType: 'application/json',
        type: 'PUT',
        url: 'dsaApp/gameManager/user/buyCharacter/' + sessionStorage.getItem("mailUsuario") + '/' + idCharacter,
        dataType: 'text',
        success: function (result) {
            userCoins();
            alert("Character bought successfully");
        },
        error: function (error) {
            alert("Not enough coins to buy the object");
        }
    });
}

function userCoins() {
    $.ajax({
        type: 'GET',
        url: 'dsaApp/gameManager/user/' + sessionStorage.getItem("mailUsuario") + '/coins',
        data: {},
        dataType: 'text',
        success: function (data) {
            var json = $.parseJSON(data);
            $('#text1').val("Saldo: " + json + " coins")
        },
        error: function (error) {
            alert("Not able to get your coins");
        }
    });
}

$(document).ready(function () {

    if(sessionStorage.getItem('mailUsuario') != null){
        $('#back').click(function () {
            window.location.href='usuario.html';
        });
        userCoins();
        $.ajax({
            type: 'GET',
            url: 'dsaApp/gameManager/myObjects',
            dataType: 'text',
            success: function (data) {
                var json = $.parseJSON(data);
                $('#resultsObjects').empty();
                for (var i = 0; i < json.length; ++i) {
                    $('#resultsObjects').append('<tr id="elementosObjects">' +
                        '<td>' + json[i].objectName + '</td>' +
                        '<td>' + json[i].objectDescription + '</td>' +
                        '<td>' + json[i].objectCoins + '</td>' +
                        '<td>' + json[i].objectTypeId + '</td>' +
                        '<td><a href="#" onclick="comprarObject('+json[i].objectId+')">Comprar</a></td>' +
                        '</tr>');
                }
            },
            error: function (error) {
                alert("Not able to create the objects table. Try again");
            }
        });

        $.ajax({
            type: 'GET',
            url: 'dsaApp/gameManager/characters',
            dataType: 'text',
            success: function (data) {
                var json = $.parseJSON(data);
                $('#resultsCharacters').empty();
                for (var i = 0; i < json.length; ++i) {
                    $('#resultsCharacters').append('<tr id="elementosCharacters">' +
                        '<td>' + json[i].characterName + '</td>' +
                        '<td>' + json[i].characterDescription + '</td>' +
                        '<td>' + json[i].characterCoins + '</td>' +
                        '<td><a href="#" onclick="comprarCharacter('+json[i].characterId+')">Comprar</a></td>' +
                        '</tr>');
                }
            },
            error: function (error) {
                alert("Not able to create the characters table. Try again");
            }
        });
    }else {
        window.location.href="index.html";
    }
});