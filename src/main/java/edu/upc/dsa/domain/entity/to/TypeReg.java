package edu.upc.dsa.domain.entity.to;

public class TypeReg {
    private String idTypeReg;
    private String descriptionReg;

    public TypeReg() {

    }

    public TypeReg(String idTypeReg, String descriptionReg) {
        this.idTypeReg = idTypeReg;
        this.descriptionReg = descriptionReg;
    }

    public String getIdTypeReg() {
        return idTypeReg;
    }

    public void setIdTypeReg(String idTypeReg) {
        this.idTypeReg = idTypeReg;
    }

    public String getDescriptionReg() {
        return descriptionReg;
    }

    public void setDescriptionReg(String descriptionReg) {
        this.descriptionReg = descriptionReg;
    }
}
