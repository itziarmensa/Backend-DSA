package edu.upc.dsa;

import edu.upc.dsa.domain.entity.MyObjects;
import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.to.ObjectReg;
import edu.upc.dsa.domain.entity.vo.TypeObject;
import edu.upc.dsa.domain.entity.exceptions.EmailAddressNotValidException;
import edu.upc.dsa.domain.entity.exceptions.UserAlreadyExistsException;
import edu.upc.dsa.domain.entity.vo.Credentials;
import edu.upc.dsa.domain.entity.vo.EmailAddress;
import edu.upc.dsa.infraestructure.GameManagerImpl;
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

        TypeObject t1 = new TypeObject("1","xxxx");
        gameManager.addTypeObject(t1);
        TypeObject t2 = new TypeObject("2","xxxx");
        gameManager.addTypeObject(t2);
        TypeObject t3 = new TypeObject("3","xxxx");
        gameManager.addTypeObject(t3);


        ObjectReg o1 = new ObjectReg("11","Espada", "Espada con poderes","1", 3.1);
        gameManager.addObject(o1);
        ObjectReg o2 = new ObjectReg("22","Anillo", "Anillo teletransportador","2", 2.7);
        gameManager.addObject(o2);
        ObjectReg o3 = new ObjectReg("33","Traje", "Traje invisible", "3",4.5);
        gameManager.addObject(o3);
        ObjectReg o4 = new ObjectReg("44","Gafas", "Gafas visión del futuro","2", 5.25);
        gameManager.addObject(o4);
        ObjectReg o5 = new ObjectReg("55","Pistola", "Pistola laser", "2",1.35);
        gameManager.addObject(o5);
        ObjectReg o6 = new ObjectReg("66","Capa", "Capa voladora", "1",5);
        gameManager.addObject(o6);
    }

    @After
    public void tearDown() {
        this.gameManager = null;
    }


    @Test
    public void registerUserTest() throws UserAlreadyExistsException, EmailAddressNotValidException {
        Assert.assertEquals(0,this.gameManager.numUsersRegistered());
        Credentials credentials1 = new Credentials(new EmailAddress("oscar.boullosa@estudiantat.upc.edu"), "myPassword1");
        this.gameManager.registerUser("Óscar", "Boullosa Dapena", "08/03/2001", credentials1);
        Assert.assertEquals(1,this.gameManager.numUsersRegistered());
        Credentials credentials2 = new Credentials(new EmailAddress("itziar.mensa@estudiantat.upc.edu"), "myPassword2");
        this.gameManager.registerUser("Itziar", "Mensa Minguito", "24/11/2001", credentials2);
        Assert.assertEquals(2,this.gameManager.numUsersRegistered());
        Credentials credentials3 = new Credentials(new EmailAddress("pau.feixa@estudiantat.upc.edu"), "myPassword3");
        this.gameManager.registerUser("Pau", "Feixa", "14/11/2001", credentials3);
        Assert.assertEquals(3,this.gameManager.numUsersRegistered());
    }
    @Test
    public void EmailAddressNotValidExceptionTest(){
        Assert.assertThrows(EmailAddressNotValidException.class,()->this.gameManager.registerUser("Lluc","Feixa","14/11/2001",new Credentials((new EmailAddress("lluc.feixa")), "myPassword4")));
    }




    @Test
    public void addObjectTest()
    {
        Assert.assertEquals(6, this.gameManager.getNumObject());
        ObjectReg o7 = new ObjectReg("77","Pocima", "Pocima con veneno","1", 5);
        gameManager.addObject(o7);
        Assert.assertEquals(7, this.gameManager.getNumObject());

        List<MyObjects> myObjects = this.gameManager.getTienda();
        Assert.assertEquals("Anillo", myObjects.get(1).getName());
        Assert.assertEquals(5, myObjects.get(5).getCoins(),0.5);

        gameManager.deleteObject("44");
        Assert.assertEquals(6, this.gameManager.getNumObject());
        gameManager.deleteObject("22");
        Assert.assertEquals(5, this.gameManager.getNumObject());
    }

    @Test
    public void addTypeTest(){
         List<TypeObject> myTypes = gameManager.getAllType();
        Assert.assertEquals(3, myTypes.size(),0.5);
        TypeObject t4 = new TypeObject("4","xxxx");
        gameManager.addTypeObject(t4);
        Assert.assertEquals(4, myTypes.size(),0.5);
    }
    @Test
    public void getObjectByIdTest(){
        gameManager.getObject("22");
        Assert.assertEquals("Anillo", gameManager.getTienda().get(1).getName());
        Assert.assertEquals(2.7, gameManager.getTienda().get(1).getCoins(),0.5);
        Assert.assertEquals("2", gameManager.getTienda().get(1).getTypeObject().getIdType());

        gameManager.getObject("66");
        Assert.assertEquals("Capa", gameManager.getTienda().get(5).getName());
        Assert.assertEquals(5, gameManager.getTienda().get(5).getCoins(),0.5);
        Assert.assertEquals("1", gameManager.getTienda().get(5).getTypeObject().getIdType());
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
        Assert.assertEquals("Anillo", l.get(0).getName());
        Assert.assertEquals("Gafas", l.get(1).getName());
        Assert.assertEquals("Pistola", l.get(2).getName());

        Assert.assertEquals(2.7, l.get(0).getCoins(),0.5);
        Assert.assertEquals(5.25, l.get(1).getCoins(),0.5);
        Assert.assertEquals(1.35, l.get(2).getCoins(),0.5);
    }

    @Test
    public void deleteListObjectsByTypeTest(){
        gameManager.deleteListObject("3");
        Assert.assertEquals(5, this.gameManager.getNumObject());
        gameManager.deleteListObject("1");
        Assert.assertEquals(3, this.gameManager.getNumObject());
    }

}
