package edu.upc.dsa.domain.entity.vo;

import edu.upc.dsa.domain.entity.exceptions.EmailAddressNotValidException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class Credentials {
    String email;
    String password;

    public Credentials() {}
    public Credentials(String email,String password){
        this.email=email;
        this.password=password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws EmailAddressNotValidException {
        if(!EmailValidator.getInstance().isValid(email)){
            throw new EmailAddressNotValidException();
        }
        this.email=email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
