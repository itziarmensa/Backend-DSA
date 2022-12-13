package edu.upc.dsa.domain.entity;

import edu.upc.dsa.domain.entity.vo.Dice;


public class Characters {

    private String idCharacter;
    private String nameCharacter;
    private String descriptionCharacter;
    private Dice myDice;
    private String idDice;
    private double coinsCharacter;

    public Characters() {
    }

    public Characters(String idCharacter, String nameCharacter, String descriptionCharacter, String idDice, double coinsCharacter) {
        this.idCharacter = idCharacter;
        this.nameCharacter = nameCharacter;
        this.descriptionCharacter = descriptionCharacter;
        this.idDice = idDice;
        this.myDice = null;
        this.coinsCharacter = coinsCharacter;
    }

    public String getIdCharacter() {
        return idCharacter;
    }

    public void setIdCharacter(String idCharacter) {
        this.idCharacter = idCharacter;
    }

    public String getNameCharacter() {
        return nameCharacter;
    }

    public void setNameCharacter(String nameCharacter) {
        this.nameCharacter = nameCharacter;
    }

    public String getDescriptionCharacter() {
        return descriptionCharacter;
    }

    public void setDescriptionCharacter(String descriptionCharacter) {
        this.descriptionCharacter = descriptionCharacter;
    }

    public Dice getMyDice() {
        return myDice;
    }

    public void setMyDice(Dice myDice) {
        this.myDice = myDice;
    }

    public double getCoinsCharacter() {
        return coinsCharacter;
    }

    public void setCoinsCharacter(double coinsCharacter) {
        this.coinsCharacter = coinsCharacter;
    }

    public String getIdDice() {
        return idDice;
    }

    public void setIdDice(String idDice) {
        this.idDice = idDice;
    }
}
