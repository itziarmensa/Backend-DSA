package edu.upc.dsa.domain.entity.vo;

import java.util.Objects;

public class Credentials {
    EmailAddress email;
    String password;

    public Credentials() {}
    public Credentials(EmailAddress email,String password){
        this.email=email;
        this.password=password;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEqual(Credentials credentials){
        return (this.email.isEqual(credentials.getEmail())&& Objects.equals(this.password,credentials.getPassword()));
    }
}
