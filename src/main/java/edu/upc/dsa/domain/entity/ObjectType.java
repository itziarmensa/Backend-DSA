package edu.upc.dsa.domain.entity;

public class ObjectType {

    private String idType;
    private String description;

    public ObjectType() {
    }

    public ObjectType(String idType, String description) {
        this.idType = idType;
        this.description = description;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
