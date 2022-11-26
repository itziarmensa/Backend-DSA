package edu.upc.dsa.domain.entity.to;

import edu.upc.dsa.domain.entity.vo.TypeObject;

public class ObjectReg {

    private String idObjectReg;
    private String nameReg;
    private String descriptionObjectReg;
    private double coinsReg;
    private String idTypeObjectReg;

    public ObjectReg() {

    }

    public ObjectReg(String idObjectReg, String nameReg, String descriptionObjectReg, String idTypeObjectReg,double coinsReg) {
        this.idObjectReg = idObjectReg;
        this.nameReg = nameReg;
        this.descriptionObjectReg = descriptionObjectReg;
        this.coinsReg = coinsReg;
        this.idTypeObjectReg = idTypeObjectReg;
    }

    public String getIdObjectReg() {
        return idObjectReg;
    }

    public void setIdObjectReg(String idObjectReg) {
        this.idObjectReg = idObjectReg;
    }

    public String getNameReg() {
        return nameReg;
    }

    public void setNameReg(String nameReg) {
        this.nameReg = nameReg;
    }

    public String getDescriptionObjectReg() {
        return descriptionObjectReg;
    }

    public void setDescriptionObjectReg(String descriptionObjectReg) {
        this.descriptionObjectReg = descriptionObjectReg;
    }

    public double getCoinsReg() {
        return coinsReg;
    }

    public void setCoinsReg(double coinsReg) {
        this.coinsReg = coinsReg;
    }

    public String getIdTypeObjectReg() {
        return idTypeObjectReg;
    }

    public void setIdTypeObjectReg(String idTypeObjectReg) {
        this.idTypeObjectReg = idTypeObjectReg;
    }
}


