package edu.upc.dsa;

import edu.upc.dsa.domain.entity.Characters;
import edu.upc.dsa.domain.entity.MyObjects;
import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.ObjectType;
import edu.upc.dsa.domain.entity.exceptions.EmailAddressNotValidException;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.vo.Credentials;
import edu.upc.dsa.infraestructure.GameManagerImpl;
import edu.upc.dsa.domain.entity.vo.Dice;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class GameManagerImplTest {

    GameManager gameManager;


    @Before
    public void setUp() {
        this.gameManager=new GameManagerImpl();

        ObjectType t1 = new ObjectType("1","xxxx");
        gameManager.addTypeObject(t1);
        ObjectType t2 = new ObjectType("2","xxxx");
        gameManager.addTypeObject(t2);
        ObjectType t3 = new ObjectType("3","xxxx");
        gameManager.addTypeObject(t3);


        MyObjects o1 = new MyObjects("11","Espada", "Espada con poderes", 3.1,"1");
        gameManager.addObject(o1);
        MyObjects o2 = new MyObjects("22","Anillo", "Anillo teletransportador", 2.7, "2");
        gameManager.addObject(o2);
        MyObjects o3 = new MyObjects("33","Traje", "Traje invisible", 4.5, "3");
        gameManager.addObject(o3);
        MyObjects o4 = new MyObjects("44","Gafas", "Gafas visión del futuro", 5.25,"2");
        gameManager.addObject(o4);
        MyObjects o5 = new MyObjects("55","Pistola", "Pistola laser", 1.35, "2");
        gameManager.addObject(o5);
        MyObjects o6 = new MyObjects("66","Capa", "Capa voladora", 5, "1");
        gameManager.addObject(o6);


        Characters c1 = new Characters("c1","Mario","hombre","d1",100);
        gameManager.addCharacter(c1);
        Characters c2 = new Characters("c2","Donkey Kong","mono","d2",50);
        gameManager.addCharacter(c2);
        Characters c3 = new Characters("c3","Diddy Kong","mono","d3",40);
        gameManager.addCharacter(c3);
        Characters c4 = new Characters("c4","Yoshi","cocodrilo","d4",80);
        gameManager.addCharacter(c4);
        Characters c5 = new Characters("c5","Pum pum","tortuga","d5",20);
        gameManager.addCharacter(c5);
        Characters c6 = new Characters("c6","Huesitos","fantasma","d1",60);
        gameManager.addCharacter(c6);
        Characters c7 = new Characters("c7","Pum pum","tortuga","d5",20);
        gameManager.addCharacter(c7);


        Dice d1 = new Dice("d1","6-6-6-6");
        gameManager.addDice(d1);
        Dice d2 = new Dice("d2","0-0-0-10-10");
        gameManager.addDice(d2);
        Dice d3 = new Dice("d3","0-0-7-7-7");
        gameManager.addDice(d3);
        Dice d4 = new Dice("d4","0-1-3-3-5-7");
        gameManager.addDice(d4);
        Dice d5 = new Dice("d5","0-3-3-3-3-8");
        gameManager.addDice(d5);
    }

    @After
    public void tearDown() {
        this.gameManager = null;
    }


    @Test
    public void registerUserTest() throws UserAlreadyExistsException, EmailAddressNotValidException {
        Assert.assertEquals(0,this.gameManager.numUsersRegistered());
        this.gameManager.registerUser("Óscar", "Boullosa Dapena", "08/03/2001", "oscar.boullosa@estudiantat.upc.edu", "myPassword1");
        Assert.assertEquals(1,this.gameManager.numUsersRegistered());
        this.gameManager.registerUser("Itziar", "Mensa Minguito", "24/11/2001", "itziar.mensa@estudiantat.upc.edu", "myPassword2");
        Assert.assertEquals(2,this.gameManager.numUsersRegistered());
        this.gameManager.registerUser("Pau", "Feixa", "14/11/2001", "pau.feixa@estudiantat.upc.edu", "myPassword3");
        Assert.assertEquals(3,this.gameManager.numUsersRegistered());
    }
    @Test
    public void EmailAddressNotValidExceptionTest() {
        Assert.assertThrows(EmailAddressNotValidException.class,()->this.gameManager.registerUser("Lluc","Feixa","14/11/2001","lluc.feixa", "myPassword4"));
    }




    @Test
    public void addObjectTest()
    {
        Assert.assertEquals(6, this.gameManager.getNumObject());
        MyObjects o7 = new MyObjects("77","Pocima", "Pocima con veneno", 5,"1");
        gameManager.addObject(o7);
        Assert.assertEquals(7, this.gameManager.getNumObject());

        List<MyObjects> myObjects = this.gameManager.getTienda();
        Assert.assertEquals("Anillo", myObjects.get(1).getObjectName());
        Assert.assertEquals(5, myObjects.get(5).getObjectCoins(),0.5);

        gameManager.deleteObject("44");
        Assert.assertEquals(6, this.gameManager.getNumObject());
        gameManager.deleteObject("22");
        Assert.assertEquals(5, this.gameManager.getNumObject());
    }

    @Test
    public void addTypeTest(){
         List<ObjectType> myTypes = gameManager.getAllType();
        Assert.assertEquals(3, myTypes.size(),0.5);
        ObjectType t4 = new ObjectType("4","xxxx");
        gameManager.addTypeObject(t4);
        Assert.assertEquals(4, myTypes.size(),0.5);
    }
    @Test
    public void getObjectByIdTest(){
        gameManager.getObject("22");
        Assert.assertEquals("Anillo", gameManager.getTienda().get(1).getObjectName());
        Assert.assertEquals(2.7, gameManager.getTienda().get(1).getObjectCoins(),0.5);
        Assert.assertEquals("2", gameManager.getTienda().get(1).getObjectTypeId());

        gameManager.getObject("66");
        Assert.assertEquals("Capa", gameManager.getTienda().get(5).getObjectName());
        Assert.assertEquals(5, gameManager.getTienda().get(5).getObjectCoins(),0.5);
        Assert.assertEquals("1", gameManager.getTienda().get(5).getObjectTypeId());
    }

    @Test
    public void getDescriptionCoinsObjectTest(){
        Assert.assertEquals(2.7, gameManager.getCoinsObject("Anillo"),0.5);
        Assert.assertEquals("Gafas visión del futuro", gameManager.getDescriptionObject("Gafas"));
    }

    @Test
    public void getListObjectbyTypeTest(){
        List<MyObjects> l = gameManager.getListObject("2");
        Assert.assertEquals(3, l.size());
        Assert.assertEquals("Anillo", l.get(0).getObjectName());
        Assert.assertEquals("Gafas", l.get(1).getObjectName());
        Assert.assertEquals("Pistola", l.get(2).getObjectName());

        Assert.assertEquals(2.7, l.get(0).getObjectCoins(),0.5);
        Assert.assertEquals(5.25, l.get(1).getObjectCoins(),0.5);
        Assert.assertEquals(1.35, l.get(2).getObjectCoins(),0.5);
    }

    @Test
    public void deleteListObjectsByTypeTest(){
        gameManager.deleteListObject("3");
        Assert.assertEquals(5, this.gameManager.getNumObject());
        gameManager.deleteListObject("1");
        Assert.assertEquals(3, this.gameManager.getNumObject());
    }

    @Test
    public void getListCharacters(){
        List<Characters> c = gameManager.getAllCharacters();

        Assert.assertEquals(7, gameManager.getNumCharacters(),0.5);

        Assert.assertEquals("Mario", c.get(0).getNameCharacter());
        Assert.assertEquals("Yoshi", c.get(3).getNameCharacter());
        Assert.assertEquals("Pum pum", c.get(6).getNameCharacter());

        Assert.assertEquals("mono", c.get(1).getDescriptionCharacter());
        Assert.assertEquals("tortuga", c.get(4).getDescriptionCharacter());

        Assert.assertEquals(20, c.get(4).getCoinsCharacter(),0.5);
        Assert.assertEquals(60, c.get(5).getCoinsCharacter(),0.5);
    }
    @Test
    public void getListDice(){
        List<Dice> l = gameManager.getAllDice();

        Assert.assertEquals(5, gameManager.getNumDice(),0.5);

        Assert.assertEquals("d1", l.get(0).getIdD());
        Assert.assertEquals("d4", l.get(3).getIdD());

        Assert.assertEquals("0-0-7-7-7", l.get(2).getDescriptionD());
        Assert.assertEquals("0-3-3-3-3-8", l.get(4).getDescriptionD());
    }


}
