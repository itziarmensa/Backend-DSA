package edu.upc.dsa.infraestructure;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.Characters;
import edu.upc.dsa.domain.entity.MyObjects;
import edu.upc.dsa.domain.entity.ObjectType;
import edu.upc.dsa.domain.entity.User;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.exceptions.UserNotExistsException;
import edu.upc.dsa.domain.entity.vo.Credentials;
import edu.upc.dsa.domain.entity.vo.Dice;
import edu.upc.dsa.domain.entity.vo.UserMyObjects;
import edu.upc.eetac.dsa.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManagerDBImpl implements GameManager {
    Session session;
    private static GameManager instance;

    private List<MyObjects> tienda;
    private HashMap<String, User> users;
    private List<User> registeredUsers;
    private List<ObjectType> types;

    private List<Dice> dados;
    private List<Characters> characters;
    private HashMap<String, List<MyObjects>> userObjects;

    final static Logger logger = Logger.getLogger(GameManagerDBImpl.class);

    public GameManagerDBImpl(){
        this.session = FactorySession.openSession("jdbc:mariadb://localhost:3306/dsa","root", "1234");
        this.tienda = new ArrayList<>();
        this.users = new HashMap<>();
        this.registeredUsers = new ArrayList<>();
        this.types = new ArrayList<>();
        this.characters = new ArrayList<>();
        this.dados = new ArrayList<>();
        this.userObjects = new HashMap<>();
    }

    public static GameManager getInstance() {
        if (instance == null) instance = new GameManagerDBImpl();
        return instance;
    }

    @Override
    public int size() {
        getUsers();
        int ret = this.users.size();
        logger.info("size " + ret);
        return ret;
    }

    public void getUsers() {
        this.users.clear();
        List<Object> usersList= this.session.findAll(User.class);
        for(int i=0; i<usersList.size();i++) {
            User user = (User) usersList.get(i);
            this.users.put(user.getUserId(), user);
        }
    }

    public Boolean userExistsByEmail(String email) {
        getUsers();
        for (User user : this.users.values()) {
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
    public void addObject(MyObjects myObject) {
        MyObjects o = new MyObjects(myObject.getObjectId(), myObject.getObjectName(), myObject.getObjectDescription(), myObject.getObjectCoins(), myObject.getObjectTypeId());
        this.session.save(o);
        logger.info("Object " + o.getObjectName() + " has been successfully added to the store");
    }

    @Override
    public int getNumObject() {
        return this.session.findAll(MyObjects.class).size();
    }

    @Override
    public int numUsersRegistered() {
        return this.session.findAll(User.class).size();
    }

    @Override
    public List<MyObjects> getTienda() {
        this.tienda.clear();
        List<Object> myobjects= this.session.findAll(MyObjects.class);
        for(int i=0; i<myobjects.size();i++){
            MyObjects myobject = (MyObjects) myobjects.get(i);
            this.tienda.add(myobject);
        }
        return this.tienda;
    }

    @Override
    public MyObjects getObject(String objectId) {
        return (MyObjects) this.session.get(MyObjects.class, objectId);
    }

    @Override
    public void deleteObject(String objectId) {
        getTienda();
        for (MyObjects o : this.tienda) {
            if (o.getObjectId().equals(objectId)) {
                this.session.delete(o);
                getTienda();
                break;
            }
        }
        logger.info("The Object " + objectId + " has been successfully removed!");
    }

    @Override
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

    @Override
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

    @Override
    public void addTypeObject(ObjectType objectType) {
        this.session.save(objectType);
    }

    @Override
    public List<ObjectType> getAllType() {
        this.types.clear();
        List<Object> objectTypes= this.session.findAll(ObjectType.class);
        for(int i=0; i<objectTypes.size();i++){
            ObjectType objectType = (ObjectType) objectTypes.get(i);
            this.types.add(objectType);
        }
        return this.types;
    }

    @Override
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

    @Override
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

    @Override
    public void buyObject(String email, String objectId) throws UserNotExistsException {
        getUsers();
        Boolean found = false;
        for (User user : this.users.values()) {
            if (user.hasEmail(email)) {
                found = true;
                String userId = user.getUserId();
                if (!userObjects.containsKey(email))
                {
                    List<MyObjects> myObjects = new ArrayList<>();
                    userObjects.put(userId, myObjects);
                }
                MyObjects myObject = getObject(objectId);
                userObjects.get(user.getUserId()).add(myObject);
                UserMyObjects userMyObjects = new UserMyObjects(userId, objectId);
                this.session.save(userMyObjects);
                logger.info("The Object " + objectId + " has been successfully bought by the user with email " + email + "!");
            }
        }
        if (!found) {
            logger.info("User with email " + email + " does not exist!");
            throw new UserNotExistsException();
        }
    }

    @Override
    public List<MyObjects> getObjectsByUser(String email) {
        logger.info("User with email " + email + " has requested for his/her objects");
        return userObjects.get(email);
    }

    @Override
    public List<Characters> getAllCharacters() {
        return this.characters;
    }

    @Override
    public double getNumCharacters() {
        logger.info("We have " + this.characters.size() + " of Characters");
        return this.characters.size();
    }

    @Override
    public void addCharacter(Characters character) {
        this.characters.add(character);
        logger.info("The Character " + character.getCharacterId() + " has been successfully added!");
    }

    @Override
    public List<Dice> getAllDice() {
        return this.dados;
    }

    @Override
    public void addDice(Dice dice) {
        this.dados.add(dice);
        logger.info("The dice " + dice.getDiceId() + " has been successfully added!");
    }

    @Override
    public double getNumDice() {
        logger.info("We have " + this.dados.size() + " of dice");
        return this.dados.size();
    }
}
