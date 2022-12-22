package edu.upc.dsa.domain.entity.vo;

public class Dice {

    private String diceId;
    private String diceDescription;

    public Dice() {
    }

    public Dice(String diceId, String diceDescription) {
        this.diceId = diceId;
        this.diceDescription = diceDescription;
    }

    public String getDiceId() {
        return diceId;
    }

    public void setDiceId(String diceId) {
        this.diceId = diceId;
    }

    public String getDiceDescription() {
        return diceDescription;
    }

    public void setDiceDescription(String diceDescription) {
        this.diceDescription = diceDescription;
    }
}
