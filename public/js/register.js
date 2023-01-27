$(document).ready(function () {
    $("#errorEmptyRegister").hide();
    $("#succesRegister").hide();
    $("#errorRegister").hide();
    $("#errorPasswordRegister").hide();

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
                    //alert("Register successful");
                    if (nameRegister == "" || surnameRegister == "" || birthRegister == "" || mailRegister == "" || passwordRegister == "" || passwordRegister2 == "" )
                        $("#errorEmptyRegister").show();
                    else{
                        $("#succesRegister").show();
                        window.location.href='login.html';
                    }
                },
                error: function (error) {
                    if (nameRegister == "" || surnameRegister == "" || birthRegister == "" || mailRegister == "" || passwordRegister == "" || passwordRegister2 == "" )
                        $("#errorEmptyRegister").show();
                    else
                        //alert("Register failed");
                        $("#errorRegister").show();
                }
            });
        }
        else {
            //alert("Password has to be the same");
            $("#errorPasswordRegister").show();
        }
    });
});