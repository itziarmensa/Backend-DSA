package edu.upc.dsa.domain;

import edu.upc.dsa.domain.entity.Characters;
import edu.upc.dsa.domain.entity.MyObjects;
import edu.upc.dsa.domain.entity.ObjectType;
import edu.upc.dsa.domain.entity.exceptions.NotEnoughCoinsException;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.exceptions.UserNotExistsException;
import edu.upc.dsa.domain.entity.vo.Credentials;

import java.util.List;

public interface GameManager {
    /**User*/
    public int numUsers();
    public void registerUser(String userName, String userSurname, String birthDate, String email, String password) throws UserAlreadyExistsException;
    public Boolean login(Credentials credentials);
    public double getUserCoins(String email);

    /**Object*/
    public void addObject(MyObjects myObject);
    public int numObject();
    public List<MyObjects> getTienda();
    public MyObjects getObject(String idObject);
    public void deleteObject(String idObject);
    public List<MyObjects> getListObject(String type);
    public void deleteListObject(String type);
    public void addTypeObject(ObjectType objectType);
    public List<ObjectType> getAllType();
    public double getCoinsObject(String nameObject);
    public String getDescriptionObject(String nameObject);
    public void buyObject(String email, String objectId) throws NotEnoughCoinsException;

    public List<MyObjects> getObjectsByUser(String email);

    /**Characters*/
    public List<Characters> getAllCharacters();
    public int numCharacters();
    public void addCharacter(Characters c);
    public void buyCharacter(String email, String characterId) throws NotEnoughCoinsException;

    /**public void upDateCharacter();
    public void deleteCharacter(String idC);
    public Character getCharacter(String idC);
    public void changeDice(String idC);
    public double getCoinsCharacter(String idC);
    public String getDescriptionCharacter(String idC);*/
}
