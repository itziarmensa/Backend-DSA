package edu.upc.dsa.utils;

import edu.upc.dsa.models.*;
import edu.upc.dsa.models.Object;

import java.util.List;

public interface GameManager {

    public void addObject(Object o);

    public int getNumObject();
    public List<Object> getTienda();
    public Object getObject(String name);
}
