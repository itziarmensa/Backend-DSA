package edu.upc.dsa.domain.entity.to;

public class UserRegister {
    String userName;
    String userSurname;
    String userBirth;
    String email;
    String password;
    public UserRegister(){}
    public UserRegister(String userName,String userSurname,String userBirth,String email,String password) {
        this.userName=userName;
        this.userSurname=userSurname;
        this.userBirth=userBirth;
        this.email=email;
        this.password=password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
