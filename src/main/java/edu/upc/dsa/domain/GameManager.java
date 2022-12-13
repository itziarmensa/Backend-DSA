package edu.upc.dsa.domain;

import edu.upc.dsa.domain.entity.Characters;
import edu.upc.dsa.domain.entity.MyObjects;
import edu.upc.dsa.domain.entity.to.ObjectReg;
import edu.upc.dsa.domain.entity.vo.TypeObject;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.vo.Credentials;
import edu.upc.dsa.domain.entity.vo.Dice;

import java.util.List;

public interface GameManager {
    /**User*/
    public int size();
    public void registerUser(String userName, String userSurname, String birthDate, Credentials credentials) throws UserAlreadyExistsException;
    public Boolean login(Credentials credentials);

    /**Object*/
    public void addObject(ObjectReg o);
    public int getNumObject();
    public int numUsersRegistered();
    public List<MyObjects> getTienda();
    public MyObjects getObject(String idObject);
    public void deleteObject(String idObject);
    public List<MyObjects> getListObject(String type);
    public void deleteListObject(String type);
    public void addTypeObject(TypeObject typeObject);
    public List<TypeObject> getAllType();
    public double getCoinsObject(String nameObject);
    public String getDescriptionObject(String nameObject);

    /**Characters*/
    public List<Characters> getAllCharacters();
    public double getNumCharacters();
    public void addCharacter(Characters c);

    /**public void upDateCharacter();
    public void deleteCharacter(String idC);
    public Character getCharacter(String idC);
    public void changeDice(String idC);
    public double getCoinsCharacter(String idC);
    public String getDescriptionCharacter(String idC);*/

    /**Dice*/
    public List<Dice> getAllDice();
    public void addDice(Dice dice);
    public double getNumDice();
}
