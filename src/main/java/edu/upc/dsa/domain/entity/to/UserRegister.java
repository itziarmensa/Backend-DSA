package edu.upc.dsa.domain.entity.to;

import edu.upc.dsa.domain.entity.vo.Credentials;

public class UserRegister {
    String userName;
    String userSurname;
    String birthDate;
    Credentials credentials;
    public UserRegister(){}
    public UserRegister(String userName,String userSurname,String birthDate,Credentials credentials){
        this.userName=userName;
        this.userSurname=userSurname;
        this.birthDate=birthDate;
        this.credentials=credentials;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
