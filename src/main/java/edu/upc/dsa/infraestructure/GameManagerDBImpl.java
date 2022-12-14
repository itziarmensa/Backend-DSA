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
        this.session = FactorySession.openSession("jdbc:mariadb://localhost:3306/dsa","root", "Mazinger72");
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
        if (userExistsByEmail(email)) {
            throw new UserAlreadyExistsException();
        }
        User user = new User(userName, userSurname, birthDate, email, password);
        this.session.save(user);
    }

    @Override
    public Boolean login(Credentials credentials) {
        List<Object> usersList = this.session.findAll(User.class);
        for(Object user : usersList) {
            User user1 = (User) user;
            if(user1.getEmail().equals(credentials.getEmail()) && user1.getPassword().equals(credentials.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addObject(MyObjects myObject) {
        MyObjects o = new MyObjects(myObject.getObjectId(), myObject.getObjectName(), myObject.getObjectDescription(), myObject.getObjectCoins(), myObject.getObjectTypeId());
        this.session.save(o);
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
    }

    @Override
    public List<MyObjects> getListObject(String type) {
        return null;
    }

    @Override
    public void deleteListObject(String type) {

    }

    @Override
    public void addTypeObject(ObjectType objectType) {
        this.session.save(objectType);
    }

    @Override
    public List<ObjectType> getAllType() {
        List<Object> objectTypes= this.session.findAll(ObjectType.class);
        for(int i=0; i<objectTypes.size();i++){
            ObjectType objectType = (ObjectType) objectTypes.get(i);
            this.types.add(objectType);
        }
        return this.types;
    }

    @Override
    public double getCoinsObject(String nameObject) {
        return 0;
    }

    @Override
    public String getDescriptionObject(String nameObject) {
        return null;
    }

    @Override
    public void buyObject(String email, String objectId) throws UserNotExistsException {
        getUsers();
        Boolean found = false;
        for (User user : this.users.values()) {
            if (user.hasEmail(email)) {
                found = true;
                if (!userObjects.containsKey(email))
                {
                    List<MyObjects> myObjects = new ArrayList<>();
                    userObjects.put(email, myObjects);
                }
                MyObjects myObject = getObject(objectId);
                userObjects.get(user.getEmail()).add(myObject);
            }
        }
        if (!found) { throw new UserNotExistsException(); }
    }

    @Override
    public List<MyObjects> getObjectsByUser(String email) {
        return userObjects.get(email);
    }

    @Override
    public List<Characters> getAllCharacters() {
        return null;
    }

    @Override
    public double getNumCharacters() {
        return 0;
    }

    @Override
    public void addCharacter(Characters c) {

    }

    @Override
    public List<Dice> getAllDice() {
        return null;
    }

    @Override
    public void addDice(Dice dice) {

    }

    @Override
    public double getNumDice() {
        return 0;
    }
}
