package edu.upc.dsa.domain;

import edu.upc.dsa.domain.entity.Object;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.vo.Credentials;

import java.util.List;

public interface GameManager {
    public int size();
    public void registerUser(String userName, String userSurname, String birthDate, Credentials credentials) throws UserAlreadyExistsException;

    public void addObject(Object o);

    public int getNumObject();
    public int numUsersRegistered();
    public List<Object> getTienda();
    public Object getObject(String name);
}
