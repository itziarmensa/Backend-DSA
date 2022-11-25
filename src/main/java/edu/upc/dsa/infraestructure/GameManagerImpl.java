package edu.upc.dsa.infraestructure;

import edu.upc.dsa.domain.GameManager;
import edu.upc.dsa.domain.entity.Object;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameManagerImpl implements GameManager {

    private static GameManager instance;

    private List<Object> tienda;

    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    public GameManagerImpl() {
        this.tienda = new ArrayList<>();
    }

    public static GameManager getInstance() {
        if (instance == null) instance = new GameManagerImpl();
        return instance;
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
