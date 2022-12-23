$(document).ready(function () {

    if(sessionStorage.getItem('mailUsuario') != null){
        $.ajax({
            type: 'GET',
            url: 'dsaApp/gameManager/user/' + sessionStorage.getItem("mailUsuario") + '/myObjects',
            dataType: 'text',
            success: function (data) {
                var json = $.parseJSON(data);
                $('#results').empty();
                for (var i = 0; i < json.length; ++i) {
                    $('#results').append('<tr>' +
                        '<td>' + json[i].objectName + '</td>' +
                        '<td>' + json[i].objectDescription + '</td>' +
                        '<td>' + json[i].objectCoins + '</td>' +
                        '<td>' + json[i].objectTypeId + '</td>' +
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