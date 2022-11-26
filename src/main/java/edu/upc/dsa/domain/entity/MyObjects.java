package edu.upc.dsa.domain.entity;

import edu.upc.dsa.domain.entity.vo.TypeObject;

public class MyObjects {

    private String idObject;
    private String name;
    private String descriptionObject;
    private double coins;
    private TypeObject typeObject;
    private String idTypeObject;

    public MyObjects() {
    }

    public MyObjects(String idObject, String name, String descriptionObject, String IdTypeObject, double coins) {
        this.idObject = idObject;
        this.name = name;
        this.descriptionObject = descriptionObject;
        this.idTypeObject = IdTypeObject;
        this.typeObject = null;
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

    public String getDescriptionObject() {
        return descriptionObject;
    }

    public void setDescriptionObject(String descriptionObject) {
        this.descriptionObject = descriptionObject;
    }

    public TypeObject getTypeObject() {
        return typeObject;
    }

    public void setTypeObject(TypeObject typeObject) {
        this.typeObject = typeObject;
    }

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }

    public String getIdTypeObject() {
        return idTypeObject;
    }

    public void setIdTypeObject(String idTypeObject) {
        this.idTypeObject = idTypeObject;
    }

}
