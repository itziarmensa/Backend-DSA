package edu.upc.dsa.infraestructure;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.Characters;
import edu.upc.dsa.domain.entity.MyObjects;
import edu.upc.dsa.domain.entity.ObjectType;
import edu.upc.dsa.domain.entity.User;
import edu.upc.dsa.domain.entity.exceptions.NotEnoughCoinsException;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.vo.Credentials;
import edu.upc.dsa.domain.entity.vo.UserCharacters;
import edu.upc.dsa.domain.entity.vo.UserMyObjects;
import edu.upc.eetac.dsa.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameManagerDBImpl implements GameManager {
    Session session;
    private static GameManager instance;

    final static Logger logger = Logger.getLogger(GameManagerDBImpl.class);

    public GameManagerDBImpl(){
        this.session = FactorySession.openSession("jdbc:mariadb://localhost:3306/dsa","root", "Mazinger72");
    }

    public static GameManager getInstance() {
        if (instance == null) instance = new GameManagerDBImpl();
        return instance;
    }

    @Override
    public int numUsers() {
        int ret = this.session.count(User.class);
        logger.info("User size is " + ret);
        return ret;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        List<Object> usersList= this.session.findAll(User.class);
        for (Object o : usersList) {
            User user = (User) o;
            users.add(user);
        }
        return users;
    }

    public Boolean userExistsByEmail(String email) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.hasEmail(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void registerUser(String userName, String userSurname, String birthDate, String email, String password) throws UserAlreadyExistsException {
        logger.info("Trying to register the user with information: (" + userName + ", " + userSurname + ", " + birthDate + ", {" + email + ", " + password + "})");
        if (userExistsByEmail(email)) {
            logger.warn("Register not possible, user already exists!");
            throw new UserAlreadyExistsException();
        }
        User user = new User(userName, userSurname, birthDate, email, password);
        this.session.save(user);
        logger.info("Register of user with email " + user.getEmail() + " has been successfully done!");
    }

    @Override
    public Boolean login(Credentials credentials) {
        List<Object> usersList = this.session.findAll(User.class);
        for(Object user : usersList) {
            User user1 = (User) user;
            if(user1.getEmail().equals(credentials.getEmail()) && user1.getPassword().equals(credentials.getPassword())) {
                logger.info("Login of user with email " + user1.getEmail() + " has been successfully done!");
                return true;
            }
        }
        logger.info("Login of user failed");
        return false;
    }

    @Override
    public double getUserCoins(String email) {
        User user = (User) this.session.getObject(User.class, email);
        double coins = user.getCoins();
        logger.info("User " + email + " has " + coins + " coins");
        return coins;
    }

    @Override
    public void addObject(MyObjects myObject) {
        this.session.save(myObject);
        logger.info("Object " + myObject.getObjectName() + " has been successfully added to the store");
    }

    @Override
    public int numObject() {
        int ret = this.session.count(MyObjects.class);
        logger.info("MyObjects size is " + ret);
        return ret;
    }

    @Override
    public List<MyObjects> getTienda() {
        List<MyObjects> myObjects = new ArrayList<>();
        List<Object> myObjects1= this.session.findAll(MyObjects.class);
        for (Object o : myObjects1) {
            MyObjects myObject = (MyObjects) o;
            myObjects.add(myObject);
        }
        return myObjects;
    }

    @Override
    public MyObjects getObject(String objectId) {
        return (MyObjects) this.session.get(MyObjects.class, objectId);
    }

    @Override
    public void deleteObject(String objectId) {
        List<MyObjects> myObjects = getTienda();
        for (MyObjects o : myObjects) {
            if (o.getObjectId().equals(objectId)) {
                this.session.delete(o);
                break;
            }
        }
        logger.info("The Object " + objectId + " has been successfully removed!");
    }

    @Override
    public void addTypeObject(ObjectType objectType) {
        this.session.save(objectType);
    }

    @Override
    public List<ObjectType> getAllType() {
        List<ObjectType> types = new ArrayList<>();
        List<Object> objectTypes = this.session.findAll(ObjectType.class);
        for (Object type : objectTypes) {
            ObjectType objectType = (ObjectType) type;
            types.add(objectType);
        }
        return types;
    }

    @Override
    public double getCoinsObject(String objectId) {
        double coins = 0.0;
        List<MyObjects> myObjects = getTienda();
        for (MyObjects myObject : myObjects) {
            if (myObject.getObjectId().equals(objectId)) {
                coins = myObject.getObjectCoins();
                logger.info("The Object " + objectId + " costs " + coins + " coins");
                return coins;
            }
        }
        logger.info("The Object is not in the Store");
        return coins;
    }

    @Override
    public void buyObject(String email, String objectId) throws NotEnoughCoinsException {
        User user = (User) this.session.getObject(User.class, email);
        MyObjects myObject = (MyObjects) this.session.get(MyObjects.class, objectId);
        if (user.getCoins() < myObject.getObjectCoins()) {
            throw new NotEnoughCoinsException();
        }
        UserMyObjects userMyObjects = new UserMyObjects(user.getUserId(), objectId);
        user.setCoins(user.getCoins()-myObject.getObjectCoins());
        this.session.update(user);
        this.session.save(userMyObjects);
    }

    @Override
    public List<MyObjects> getObjectsByUser(String email) {
        logger.info("User with email " + email + " has requested for his/her objects");
        return null;
    }

    @Override
    public List<Characters> getAllCharacters() {
        List<Characters> characters = new ArrayList<>();
        List<Object> characters1 = this.session.findAll(Characters.class);
        for (Object o : characters1) {
            Characters character = (Characters) o;
            characters.add(character);
        }
        return characters;
    }

    @Override
    public int numCharacters() {
        int ret = this.session.count(Characters.class);
        logger.info("Characters size is " + ret);
        return ret;
    }

    @Override
    public void addCharacter(Characters character) {
        this.session.save(character);
        logger.info("The Character " + character.getCharacterId() + " has been successfully added!");
    }

    @Override
    public Characters getCharacter(String characterId) {
        return (Characters) this.session.get(Characters.class, characterId);
    }

    @Override
    public void deleteCharacter(String characterId) {
        List<Characters> characters = getAllCharacters();
        for (Characters c : characters) {
            if (c.getCharacterId().equals(characterId)) {
                this.session.delete(c);
                break;
            }
        }
        logger.info("The Character " + characterId + " has been successfully removed!");
    }

    @Override
    public double getCoinsCharacter(String characterId) {
        double coins = 0.0;
        List<Characters> characters = getAllCharacters();
        for (Characters character : characters) {
            if (character.getCharacterId().equals(characterId)) {
                coins = character.getCharacterCoins();
                logger.info("The Character " + characterId + " costs " + coins + " coins");
                return coins;
            }
        }
        logger.info("The Character is not in the Store");
        return coins;
    }

    @Override
    public void buyCharacter(String email, String characterId) throws NotEnoughCoinsException {
        User user = (User) this.session.getObject(User.class, email);
        Characters character = (Characters) this.session.get(Characters.class, characterId);
        if (user.getCoins() < character.getCharacterCoins()) {
            throw new NotEnoughCoinsException();
        }
        UserCharacters userCharacters = new UserCharacters(user.getUserId(), characterId);
        user.setCoins(user.getCoins()-character.getCharacterCoins());
        this.session.update(user);
        this.session.save(userCharacters);
    }

    @Override
    public List<Characters> getCharactersByUser(String email) {
        logger.info("User with email " + email + " has requested for his/her characters");
        return null;
    }
}
