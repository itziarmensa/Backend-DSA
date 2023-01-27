$(document).ready(function () {
    $("#errorLogIn").hide();
    $("#logInSucces").hide();
    $("#errorEmptyLogIn").hide();

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
                $("#logInSucces").show();
                window.location.href="usuario.html";
            },
            error: function (error) {
                if (mailLogin == "" || passwordLogin == "")
                    //alert("You left something blank, please try again!");
                    $("#errorEmptyLogIn").show();
                else
                    //alert("Wrong mail or password, please try again!");
                    $("#errorLogIn").show();
            }
        });
    });
});