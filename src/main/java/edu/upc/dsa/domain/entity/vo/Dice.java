package edu.upc.dsa.domain.entity.vo;

public class Dice {

    private String idD;
    private String descriptionD;

    public Dice() {
    }

    public Dice(String idD, String descriptionD) {
        this.idD = idD;
        this.descriptionD = descriptionD;
    }

    public String getIdD() {
        return idD;
    }

    public void setIdD(String idD) {
        this.idD = idD;
    }

    public String getDescriptionD() {
        return descriptionD;
    }

    public void setDescriptionD(String descriptionD) {
        this.descriptionD = descriptionD;
    }
}
