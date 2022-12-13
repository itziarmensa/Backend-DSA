package edu.upc.dsa.domain.entity;

public class MyObjects {

    String objectId;
    String objectName;
    String objectDescription;
    double objectCoins;
    String objectTypeId;

    public MyObjects() {
    }

    public MyObjects(String objectId, String objectName, String objectDescription, double objectCoins, String objectTypeId) {
        this.objectId = objectId;
        this.objectName = objectName;
        this.objectDescription = objectDescription;
        this.objectCoins = objectCoins;
        this.objectTypeId = objectTypeId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectDescription() {
        return objectDescription;
    }

    public void setObjectDescription(String objectDescription) {
        this.objectDescription = objectDescription;
    }

    public double getObjectCoins() {
        return objectCoins;
    }

    public void setObjectCoins(double objectCoins) {
        this.objectCoins = objectCoins;
    }

    public String getObjectTypeId() {
        return objectTypeId;
    }

    public void setObjectTypeId(String objectTypeId) {
        this.objectTypeId = objectTypeId;
    }

}
