package edu.upc.dsa.domain.entity.vo;

public class TypeObject {

    private String idType;
    private String description;

    public TypeObject() {
    }

    public TypeObject(String idType, String description) {
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
