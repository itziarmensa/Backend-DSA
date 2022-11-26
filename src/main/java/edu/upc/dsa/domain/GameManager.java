package edu.upc.dsa.domain;

import edu.upc.dsa.domain.entity.MyObjects;
import edu.upc.dsa.domain.entity.TypeObject;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.vo.Credentials;

import java.util.List;

public interface GameManager {
    public int size();
    public void registerUser(String userName, String userSurname, String birthDate, Credentials credentials) throws UserAlreadyExistsException;
    public void addObject(MyObjects o);
    public int getNumObject();
    public int numUsersRegistered();
    public List<MyObjects> getTienda();
    public MyObjects getObject(String idObject);
    public void deleteObject(String idObject);
    public List<MyObjects> getListObject(String type);
    public void deleteListObject(String type);
    public void addTypeObject(TypeObject typeObject);
    public List<TypeObject> getTypeObject();
    public double getCoinsObject(String nameObject);
    public String getDescriptionObject(String nameObject);

}
