package edu.upc.dsa.domain;

import edu.upc.dsa.domain.entity.Object;

import java.util.List;

public interface GameManager {

    public void addObject(Object o);

    public int getNumObject();
    public List<Object> getTienda();
    public Object getObject(String name);
}
