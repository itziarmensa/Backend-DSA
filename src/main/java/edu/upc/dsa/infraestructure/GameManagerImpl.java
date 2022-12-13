package edu.upc.dsa.infraestructure;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.Characters;
import edu.upc.dsa.domain.entity.MyObjects;
import edu.upc.dsa.domain.entity.ObjectType;
import edu.upc.dsa.domain.entity.User;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.vo.Credentials;
import edu.upc.dsa.domain.entity.vo.Dice;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManagerImpl implements GameManager {

    private static GameManager instance;

    private List<MyObjects> tienda;
    private Map<String, User> users;
    private List<User> registeredUsers;
    private List<ObjectType> types;

    private List<Dice> dados;
    private List<Characters> characters;

    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    public GameManagerImpl() {
        this.tienda = new ArrayList<>();
        this.users = new HashMap<>();
        this.registeredUsers = new ArrayList<>();
        this.types = new ArrayList<>();
        this.characters = new ArrayList<>();
        this.dados = new ArrayList<>();
    }

    public static GameManager getInstance() {
        if (instance == null) instance = new GameManagerImpl();
        return instance;
    }

    /**User**/
    public int size() {
        int ret = this.users.size();
        logger.info("size " + ret);
        return ret;
    }

    public Boolean userExistsByEmail(String email) {
        for (User user : this.users.values()) {
            if (user.hasEmail(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int numUsersRegistered() {
        return registeredUsers.size();
    }

    @Override
    public void registerUser(String userName, String userSurname, String birthDate, String email, String password) throws UserAlreadyExistsException {
        logger.info("Trying to register the user with information: (" + userName + ", " + userSurname + ", " + birthDate + ", {" + email + ", " + password + "})");
        if (userExistsByEmail(email)) {
            logger.warn("Register not possible, user already exists!");
            throw new UserAlreadyExistsException();
        }
        User user = new User(userName, userSurname, birthDate, email, password);
        this.users.put(user.getUserId(), user);
        this.registeredUsers.add(user);
        logger.info("Register of user with email " + user.getEmail() + " has been successfully done!");
    }

    @Override
    public Boolean login(Credentials credentials) {
        for (User user : this.users.values()) {
            if (user.getEmail().equals(credentials.getEmail()) && user.getPassword().equals(credentials.getPassword())) {
                logger.info("Login of user with email " + user.getEmail() + " has been successfully done!");
                return true;
            }
        }
        logger.info("Login of user failed");
        return false;
    }

    /**Objects**/
    public void addObject(MyObjects myObject) {
        MyObjects o = new MyObjects(myObject.getObjectId(), myObject.getObjectName(), myObject.getObjectDescription(), myObject.getObjectCoins(), myObject.getObjectTypeId());
        tienda.add(o);
        logger.info("Object " + o.getObjectName() + " has been successfully added to the store");
    }

    public void deleteObject(String idObject) {
        for (MyObjects o : this.tienda) {
            if (o.getObjectId().equals(idObject)) {
                tienda.remove(o);
                break;
            }
        }
        logger.info("The Object " + idObject + " has been successfully removed!");
    }

    public List<MyObjects> getListObject(String type) {
        List<MyObjects> myListObjectsbyType = new ArrayList<>();

        for (MyObjects o : this.tienda) {
            if (o.getObjectTypeId().equals(type)) {
                myListObjectsbyType.add(o);
                logger.info("The Objects that are of type " + type + " are:  " + myListObjectsbyType);
            }
        }
        logger.info("There are no objects in the store of type " + type);
        return myListObjectsbyType;
    }

    public void deleteListObject(String type) {
        List<MyObjects> myListObjectsbyTypeRem = new ArrayList<>();

        for (MyObjects o : this.tienda) {
            if (o.getObjectTypeId().equals(type)) {
                myListObjectsbyTypeRem.add(o);
                logger.info("Type " + type + " objects that have been removed are:  " + myListObjectsbyTypeRem);
            }
        }
        for (MyObjects oRem : myListObjectsbyTypeRem) {
            tienda.remove(oRem);
        }
        logger.info("There are no objects in the store of type " + type);
    }

    public void addTypeObject(ObjectType objectType) {
        this.types.add(objectType);
        logger.info("Added a new type of Object (" + objectType.getIdType() + ")");
    }

    public List<ObjectType> getAllType() {
        logger.info("We have " + types.size() + " of Objects");
        return this.types;
    }

    public double getCoinsObject(String nameObject) {
        for (MyObjects o : this.tienda) {
            if (o.getObjectName().equals(nameObject)) {
                logger.info("The Object " + nameObject + " costs " + o.getObjectCoins() + " coins");
                return o.getObjectCoins();
            }
        }
        logger.info("The Object is not in the Store");
        return 0.0;
    }

    public String getDescriptionObject(String nameObject) {
        for (MyObjects o : this.tienda) {
            if (o.getObjectName().equals(nameObject)) {
                logger.info("The description of Object " + nameObject + " is: " + o.getObjectDescription());
                return o.getObjectDescription();
            }
        }
        logger.info("The Object is not in the store");
        return null;
    }


    public int getNumObject() {
        int numObject = this.tienda.size();
        logger.info("size " + numObject);
        return numObject;
    }

    public List<MyObjects> getTienda() {
        logger.info("tienda " + tienda);
        return this.tienda;
    }

    public MyObjects getObject(String idObject) {
        for (MyObjects o : tienda) {
            if (o.getObjectId().equals(idObject)) {
                return o;
            }
            logger.info("getObject(" + idObject + "): " + o);
        }
        return null;
    }

    /**Characters**/
    public List<Characters> getAllCharacters(){
        return this.characters;
    }
    public double getNumCharacters(){
        logger.info("We have " + this.characters.size() + " of Characters");
        return this.characters.size();
    }
    public void addCharacter(Characters character){
        Dice myDice = new Dice();

        for (Dice d:this.dados) {
            if(d.getIdD().equals(character.getIdDice())){
                myDice = d;
                break;
            }
        }

        character.setMyDice(myDice);
        this.characters.add(character);
        logger.info("The Character " + character.getIdCharacter() + " has been successfully added!");
    }

    /**Dice**/
    public List<Dice> getAllDice(){
        return this.dados;
    }
    public void addDice(Dice dice){
        this.dados.add(dice);
        logger.info("The Character " + dice.getIdD() + " has been successfully added!");
    }
    public double getNumDice(){
        logger.info("We have " + this.dados.size() + " of dice");
        return this.dados.size();
    }
}
