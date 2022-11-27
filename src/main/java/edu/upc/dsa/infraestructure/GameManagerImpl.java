package edu.upc.dsa.infraestructure;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.MyObjects;
import edu.upc.dsa.domain.entity.to.ObjectReg;
import edu.upc.dsa.domain.entity.vo.TypeObject;
import edu.upc.dsa.domain.entity.User;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.vo.Credentials;
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
    private List<TypeObject> types;

    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    public GameManagerImpl() {
        this.tienda = new ArrayList<>();
        this.users=new HashMap<>();
        this.registeredUsers=new ArrayList<>();
        this.types = new ArrayList<>();
    }

    public static GameManager getInstance() {
        if (instance == null) instance = new GameManagerImpl();
        return instance;
    }

    public int size(){
        int ret=this.users.size();
        logger.info("size"+ret);
        return ret;
    }

    public Boolean userExistsByCredentials(Credentials credentials){
        for(User user:this.users.values()){
            if(user.hasEmail(credentials)){
                return true;
            }
        }
        return false;
    }
    @Override
    public int numUsersRegistered(){
        return registeredUsers.size();
    }

    @Override
    public void registerUser(String userName, String userSurname, String birthDate, Credentials credentials) throws UserAlreadyExistsException {
        logger.info("Trying to register the user with information: ("+userName+", "+userSurname+", "+birthDate+", {credentials})");
        if(userExistsByCredentials(credentials)){
            logger.warn("Register not possible, user already exists!");
            throw new UserAlreadyExistsException();
        }
        User user = new User(userName,userSurname,birthDate,credentials);
        this.users.put(user.getUserId(),user);
        this.registeredUsers.add(user);
        logger.info("Register of user"+user+" was done!");
    }

    @Override
    public Boolean login(Credentials credentials) {
        for(User user:this.users.values()){
            if(user.getCredentials().isEqual(credentials)){
                return true;
            }
        }
        return false;
    }

    public void addObject(ObjectReg objectReg){
        MyObjects o = new MyObjects(objectReg.getIdObjectReg(),objectReg.getNameReg(),objectReg.getDescriptionObjectReg(),objectReg.getIdTypeObjectReg(),objectReg.getCoinsReg());
        tienda.add(o);
        logger.info("Object " + o.getName() +" has been successfully added to the store");
        for(TypeObject to : this.types){
            if(to.getIdType().equals(o.getIdTypeObject())){
                o.setTypeObject(to);
                logger.info("Object " + o.getName() +" has been correctly assigned type "+o.getIdTypeObject());
            }
        }
    }
    public void deleteObject(String idObject){

        for (MyObjects o : this.tienda) {
            if(o.getIdObject().equals(idObject)){
                tienda.remove(o);
                break;
            }
        }
        logger.info("The Object"+idObject+" was been removed!");
    }

    public List<MyObjects> getListObject(String type){
        List<MyObjects> myListObjectsbyType = new ArrayList<>();

        for (MyObjects o : this.tienda) {
            if(o.getTypeObject().getIdType().equals(type)){
                myListObjectsbyType.add(o);
                logger.info("The Objects that are of type "+ type +" are:  "+ myListObjectsbyType);
            }
        }
        logger.info("There are no objects in the store of type " + type);
        return myListObjectsbyType;
    }
    public void deleteListObject(String type){
        List<MyObjects> myListObjectsbyTypeRem = new ArrayList<>();

        for (MyObjects o : this.tienda) {
            if(o.getTypeObject().getIdType().equals(type)){
                myListObjectsbyTypeRem.add(o);
                logger.info("Type "+ type +" bjects that have been removed are:  "+ myListObjectsbyTypeRem);
            }
        }
        for(MyObjects oRem:myListObjectsbyTypeRem){
            tienda.remove(oRem);
        }
        logger.info("There are no objects in the store of type " + type);
    }

    public void addTypeObject(TypeObject typeObject){
        this.types.add(typeObject);
        logger.info("Added a new type of Object (" + typeObject.getIdType() + ")");
    }

    public List<TypeObject> getAllType(){
        logger.info("We have " + types.size() +"of Objects");
        return this.types;
    }

    public double getCoinsObject(String nameObject){

        for (MyObjects o : this.tienda) {
            if(o.getName().equals(nameObject)){
                logger.info("The Object "+nameObject+" costs "+ o.getCoins() + "coins");
                return o.getCoins();
            }
        }
        logger.info("The Object is not in the Store");
        return 0.0;
    }
    public String getDescriptionObject(String nameObject){
        for (MyObjects o : this.tienda) {
            if(o.getName().equals(nameObject)){
                logger.info("The description of Object "+ nameObject +" is: "+ o.getDescriptionObject());
                return o.getDescriptionObject();
            }
        }
        logger.info("The Object is not in the Store");
        return null;
    }


    public int getNumObject(){
        int numObject = this.tienda.size();
        logger.info("size " + numObject);
        return numObject;
    }

    public List<MyObjects> getTienda(){
        logger.info("tienda " + tienda);
        return this.tienda;
    }

    public MyObjects getObject(String idObject){
        for (MyObjects o : tienda) {
            if (o.getIdObject().equals(idObject)) {
                return o;
            }
            logger.info("getObject(" + idObject + "): " + o);
        }
        return null;
    }

}
