package edu.upc.dsa.infraestructure;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.Object;
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

    private List<Object> tienda;
    private Map<String, User> users;
    private List<User> registeredUsers;

    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    public GameManagerImpl() {
        this.tienda = new ArrayList<>();
        this.users=new HashMap<>();
        this.registeredUsers=new ArrayList<>();
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
        User user=new User(userName,userSurname,birthDate,credentials);
        this.users.put(user.getUserId(),user);
        this.registeredUsers.add(user);
        logger.info("Register of user"+user+" was done!");
    }


    public void addObject(Object o){
        tienda.add(o);
    }


    public int getNumObject(){
        int numObject = this.tienda.size();
        logger.info("size " + numObject);
        return numObject;
    }

    public List<Object> getTienda(){
        logger.info("tienda " + tienda);
        return this.tienda;
    }

    public Object getObject(String name){
        for (Object o : tienda) {
            if (o.getName().equals(name)) {
                return o;
            }
            logger.info("getObject(" + name + "): " + o);
        }
        return null;
    }



}
