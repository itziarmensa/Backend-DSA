package edu.upc.dsa.domain;

import edu.upc.dsa.domain.entity.*;
import edu.upc.dsa.domain.entity.exceptions.NotEnoughCoinsException;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.vo.Credentials;
import edu.upc.dsa.domain.entity.Information;

import java.util.List;

public interface GameManager {
    /**User*/
    public int numUsers();
    public void registerUser(String userName, String userSurname, String birthDate, String email, String password) throws UserAlreadyExistsException;
    public Boolean login(Credentials credentials);
    public List<User> getUsers();
    public double getUserCoins(String email);
    public void buyObject(String email, String objectId) throws NotEnoughCoinsException;
    public List<MyObjects> getObjectsByUser(String email);
    public void buyCharacter(String email, String characterId) throws NotEnoughCoinsException;
    public List<Characters> getCharactersByUser(String email);
    public List<Partida> getPartidasByUser(String email);
    public void updateUser(User user);
    public List<User> getUsersByPoints();

    /**Object*/
    public void addObject(MyObjects myObject);
    public int numObject();
    public List<MyObjects> getTienda();
    public MyObjects getObject(String objectId);
    public void deleteObject(String objectId);
    public void addTypeObject(ObjectType objectType);
    public List<ObjectType> getAllType();
    public double getCoinsObject(String objectId);

    /**Characters*/
    public List<Characters> getAllCharacters();
    public int numCharacters();
    public void addCharacter(Characters c);
    public Characters getCharacter(String characterId);
    public void deleteCharacter(String characterId);
    public double getCoinsCharacter(String characterId);

    /**Partida*/
    public void createPartida(Partida partida);
    public void updatePartida(Partida partida);
    public int numPartidas();
    public List<Partida> getAllPartidas();

    /**FAQs*/
    public void addFaqs(Faqs faqs);
    public List<Faqs> getFaqs();

    /**Issue*/
    public void addIssue(Issue issue);
    public List<Issue> getListIssues();

    /**Information*/
    public void addInformation(Information information);
    public List<Information> getInformation();
}
