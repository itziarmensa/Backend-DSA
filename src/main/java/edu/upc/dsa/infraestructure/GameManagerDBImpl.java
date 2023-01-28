package edu.upc.dsa.infraestructure;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.*;
import edu.upc.dsa.domain.entity.exceptions.NotEnoughCoinsException;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.vo.Credentials;
import edu.upc.dsa.domain.entity.Information;
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
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        List<Object> usersList= this.session.findAll(User.class);
        for (Object o : usersList) {
            User user = (User) o;
            users.add(user);
        }
        logger.info("All Users returned");
        return users;
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
        logger.info("All Objects returned");
        return myObjects;
    }

    @Override
    public MyObjects getObject(String objectId) {
        logger.info("Object with id " + objectId + " returned");
        return (MyObjects) this.session.get(MyObjects.class, objectId);
    }

    @Override
    public void deleteObject(String objectId) {
        List<MyObjects> myObjects = getTienda();
        for (MyObjects o : myObjects) {
            if (o.getObjectId().equals(objectId)) {
                this.session.delete(o);
                logger.info("The Object " + objectId + " has been successfully removed");
                break;
            }
        }
    }

    @Override
    public void addTypeObject(ObjectType objectType) {
        this.session.save(objectType);
        logger.info("The Type " + objectType + " has been successfully added");
    }

    @Override
    public List<ObjectType> getAllType() {
        List<ObjectType> types = new ArrayList<>();
        List<Object> objectTypes = this.session.findAll(ObjectType.class);
        for (Object type : objectTypes) {
            ObjectType objectType = (ObjectType) type;
            types.add(objectType);
        }
        logger.info("All Types returned");
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
            logger.info("User " + email + " has not enough coins to buy the Object with id " + objectId);
            throw new NotEnoughCoinsException();
        }
        UserMyObjects userMyObjects = new UserMyObjects(user.getUserId(), objectId);
        user.setCoins(user.getCoins()-myObject.getObjectCoins());
        this.session.update(user);
        this.session.save(userMyObjects);
        logger.info("User " + email + " has bought the Object with id " + objectId + " successfully");
    }

    @Override
    public List<MyObjects> getObjectsByUser(String email) {
        User user = (User) this.session.getObject(User.class, email);
        List<Object> myObjects1 = this.session.userMyObjects(MyObjects.class, user.getUserId());
        List<MyObjects> myObjects = new ArrayList<>();
        for (Object o : myObjects1) {
            MyObjects myObject = (MyObjects) o;
            myObjects.add(myObject);
        }
        logger.info("User with email " + email + " has requested for his/her objects");
        return myObjects;
    }

    @Override
    public List<Characters> getAllCharacters() {
        List<Characters> characters = new ArrayList<>();
        List<Object> characters1 = this.session.findAll(Characters.class);
        for (Object o : characters1) {
            Characters character = (Characters) o;
            characters.add(character);
        }
        logger.info("All Characters returned");
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
        logger.info("The Character " + character.getCharacterId() + " has been successfully added");
    }

    @Override
    public Characters getCharacter(String characterId) {
        logger.info("Character with id " + characterId + " returned");
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
        logger.info("The Character " + characterId + " has been successfully removed");
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
    public void createPartida(Partida partida) {
        this.session.save(partida);
        logger.info("The Partida by " + partida.getEmail() + " has been successfully created");
    }

    @Override
    public void updatePartida(Partida partida) {
        this.session.update(partida);
        logger.info("The Partida by " + partida.getEmail() + " has been successfully updated");
    }

    @Override
    public int numPartidas() {
        int ret = this.session.count(Partida.class);
        logger.info("Partidas size is " + ret);
        return ret;
    }

    @Override
    public List<Partida> getAllPartidas() {
        List<Partida> partidas = new ArrayList<>();
        List<Object> partidas1 = this.session.findAll(Partida.class);
        for (Object o : partidas1) {
            Partida partida = (Partida) o;
            partidas.add(partida);
        }
        logger.info("All Partidas returned");
        return partidas;
    }

    @Override
    public void addFaqs(Faqs faqs) {
        logger.info("New FAQ added. Question:" + faqs.getQuestion() + " Answer: " + faqs.getAnswer());
        this.session.save(faqs);
    }

    @Override
    public List<Faqs> getFaqs() {
        List<Faqs> faqsList = new ArrayList<>();
        List<Object> faqs1 = this.session.findAll(Faqs.class);
        for (Object o : faqs1) {
            Faqs faqs = (Faqs) o;
            faqs1.add(faqs);
        }
        logger.info("All the " + faqsList.size() + " FAQs have been returned");
        return faqsList;
    }

    @Override
    public void addIssue(Issue issue){
        this.session.save(issue);
        logger.info("The user " + issue.getInformer() + " has send the issue: " + issue.getMessage());
    }

    @Override
    public List<Issue> getListIssues(){
        List<Issue> issues = new ArrayList<>();
        List<Object> listIssues = this.session.findAll(Issue.class);
        for (Object o : listIssues) {
            Issue issue = (Issue) o;
            issues.add(issue);
        }
        logger.info("All issues returned");
        return issues;
    }

    @Override
    public void addInformation(Information information){
        logger.info("A new information has been added with the date " + information.getDate() + " and the title " +  information.getTitle() + " by " + information.getSender());
        this.session.save(information);
    }

    @Override
    public List<Information> getInformation() {
        List<Information> informationList = new ArrayList<>();
        List<Object> information1 = this.session.findAll(Issue.class);
        for (Object o : information1) {
            Information information = (Information) o;
            informationList.add(information);
        }
        logger.info("All information returned");
        return informationList;
    }

    @Override
    public void buyCharacter(String email, String characterId) throws NotEnoughCoinsException {
        User user = (User) this.session.getObject(User.class, email);
        Characters character = (Characters) this.session.get(Characters.class, characterId);
        if (user.getCoins() < character.getCharacterCoins()) {
            logger.info("User " + email + " has not enough coins to buy the Character with id " + characterId);
            throw new NotEnoughCoinsException();
        }
        UserCharacters userCharacters = new UserCharacters(user.getUserId(), characterId);
        user.setCoins(user.getCoins()-character.getCharacterCoins());
        this.session.update(user);
        this.session.save(userCharacters);
        logger.info("User " + email + " has bought the Character with id " + characterId + " successfully");
    }

    @Override
    public List<Characters> getCharactersByUser(String email) {
        User user = (User) this.session.getObject(User.class, email);
        List<Object> characters1 = this.session.userCharacters(Characters.class, user.getUserId());
        List<Characters> characters = new ArrayList<>();
        for (Object c : characters1) {
            Characters character = (Characters) c;
            characters.add(character);
        }
        logger.info("User with email " + email + " has requested for his/her characters");
        return characters;
    }

    @Override
    public List<Partida> getPartidasByUser(String email) {
        List<Object> partidas1 = this.session.userPartidas(Partida.class, email);
        List<Partida> partidas = new ArrayList<>();
        for (Object o : partidas1) {
            Partida partida = (Partida) o;
            partidas.add(partida);
        }
        logger.info("User with email " + email + " has requested for his/her partidas");
        return partidas;
    }

    @Override
    public void updateUser(User user) {
        this.session.update(user);
        logger.info("The User with email " + user.getEmail() + " has been successfully updated");
    }

    @Override
    public List<User> getUsersByPoints() {
        List<User> users = new ArrayList<>();
        List<Object> usersList= this.session.userByPoints(User.class);
        for (Object o : usersList) {
            User user = (User) o;
            users.add(user);
        }
        logger.info("All Users ordered by points");
        return users;
    }
}
