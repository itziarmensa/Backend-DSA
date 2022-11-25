

import edu.upc.dsa.models.Object;
import edu.upc.dsa.utils.GameManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class GameManagerImplTest {

    GameManager gj;


    @Before
    public void setUp() {

        Object o1 = new Object("11","Espada", "Espada con poderes", 3.1);
        gj.addObject(o1);
        Object o2 = new Object("22","Anillo", "Anillo teletransportador", 2.7);
        gj.addObject(o2);
        Object o3 = new Object("33","Traje", "Traje invisible", 4.5);
        gj.addObject(o3);
        Object o4 = new Object("44","Gafas", "Gafas visión del futuro", 5.25);
        gj.addObject(o4);
        Object o5 = new Object("55","Pistola", "Pistola laser", 1.35);
        gj.addObject(o5);
        Object o6 = new Object("66","Capa", "Capa voladora", 5);
        gj.addObject(o6);
    }

    @After
    public void tearDown() {
        this.gj = null;
    }


    @Test
    public void addObject()
    {
        Assert.assertEquals(6, this.gj.getNumObject());
        Object o7 = new Object("77","Pocima", "Pocima con veneno", 5);
        gj.addObject(o7);
        Assert.assertEquals(7, this.gj.getNumObject());

        List<Object> myObjects = this.gj.getTienda();
        Assert.assertEquals("Anillo", myObjects.get(1).getName());
        Assert.assertEquals(5, myObjects.get(5).getCoins(),0.5);
    }
}
