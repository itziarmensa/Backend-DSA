$(document).ready(function () {

    $('#back').click(function () {
        window.location.href='login.html';
    });

    $('#register').click(function () {
        var nameRegister = $('#nameRegister').val();
        var surnameRegister = $('#surnameRegister').val();
        var birthRegister = $('#birthRegister').val();
        var mailRegister = $('#mailRegister').val();
        var passwordRegister = $('#passwordRegister').val();
        var passwordRegister2 = $('#passwordRegister2').val();
        if (passwordRegister == passwordRegister2) {
            $.ajax({
                contentType: 'application/json',
                type: 'POST',
                url: 'dsaApp/gameManager/user',
                data: JSON.stringify({
                    'userName': nameRegister,
                    'userSurname': surnameRegister,
                    'userBirth': birthRegister,
                    'email': mailRegister,
                    'password': passwordRegister
                }),
                dataType: 'json',
                success: function (result) {
                    alert("Register successful");
                    window.location.href='login.html';
                },
                error: function (error) {
                    alert("Register failed");
                }
            });
        }
        else {
            alert("Password has to be the same");
        }
    });

});