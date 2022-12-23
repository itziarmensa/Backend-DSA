$(document).ready(function () {



    $('#login').click(function () {
        var mailLogin = $('#mailLogin').val();
        var passwordLogin = $('#passwordLogin').val();
        $.ajax({
            contentType: 'application/json',
            type: 'POST',
            url: 'dsaApp/gameManager/user/login',
            data: JSON.stringify({'email': mailLogin, 'password': passwordLogin}),
            success: function (result) {
                sessionStorage.setItem('mailUsuario',mailLogin);
                window.location.href="usuario.html";
            },
            error: function (error) {
                if (mailLogin == "" || passwordLogin == "")
                    alert("You left something blank, please try again!");
                else
                    alert("Wrong mail or password, please try again!");
            }
        });
    });
});