package edu.upc.dsa.models;

public class Object {

    private String idObject;
    private String name;
    private String description;
    private double coins;

    public Object() {
    }

    public Object(String idObject, String name, String description, double coins) {
        this.idObject = idObject;
        this.name = name;
        this.description = description;
        this.coins = coins;
    }

    public String getIdObject() {
        return idObject;
    }

    public void setIdObject(String idObject) {
        this.idObject = idObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }
}
