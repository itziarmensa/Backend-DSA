package edu.upc.dsa.domain.entity;

import edu.upc.dsa.domain.entity.vo.RandomId;

public class User {
    String userId;
    String userName;
    String userSurname;
    String userBirth;
    double coins;
    int points;
    String email;
    String password;
    String language;
    public User()
    {

    }

    public User(String userName, String userSurname, String userBirth, String email, String password) {
        this.userId= RandomId.getId();
        this.userName = userName;
        this.userSurname = userSurname;
        this.userBirth = userBirth;
        this.coins = 50.0;
        this.points = 0;
        this.email = email;
        this.password = password;
        this.language = "es";
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() { return this.userName; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return this.userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserBirth() {
        return this.userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public double getCoins() {
        return this.coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean hasEmail(String email){
        return this.email.equals(email);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
