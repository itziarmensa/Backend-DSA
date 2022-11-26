package edu.upc.dsa;

import edu.upc.dsa.domain.entity.Object;
import edu.upc.dsa.domain.GameManager;
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
        Object o1 = new Object("11","Espada", "Espada con poderes", 3.1);
        gameManager.addObject(o1);
        Object o2 = new Object("22","Anillo", "Anillo teletransportador", 2.7);
        gameManager.addObject(o2);
        Object o3 = new Object("33","Traje", "Traje invisible", 4.5);
        gameManager.addObject(o3);
        Object o4 = new Object("44","Gafas", "Gafas visión del futuro", 5.25);
        gameManager.addObject(o4);
        Object o5 = new Object("55","Pistola", "Pistola laser", 1.35);
        gameManager.addObject(o5);
        Object o6 = new Object("66","Capa", "Capa voladora", 5);
        gameManager.addObject(o6);
    }

    @After
    public void tearDown() {
        this.gameManager = null;
    }


    @Test
    public void addObjectTest()
    {
        Assert.assertEquals(6, this.gameManager.getNumObject());
        Object o7 = new Object("77","Pocima", "Pocima con veneno", 5);
        gameManager.addObject(o7);
        Assert.assertEquals(7, this.gameManager.getNumObject());

        List<Object> myObjects = this.gameManager.getTienda();
        Assert.assertEquals("Anillo", myObjects.get(1).getName());
        Assert.assertEquals(5, myObjects.get(5).getCoins(),0.5);
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
}
