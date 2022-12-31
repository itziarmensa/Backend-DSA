package edu.upc.dsa.domain.entity;

public class Characters {

    private String characterId;
    private String characterName;
    private String characterDescription;
    private double characterCoins;

    public Characters() {
    }

    public Characters(String characterId, String characterName, String characterDescription, double characterCoins) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.characterDescription = characterDescription;
        this.characterCoins = characterCoins;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterDescription() {
        return characterDescription;
    }

    public void setCharacterDescription(String characterDescription) {
        this.characterDescription = characterDescription;
    }

    public double getCharacterCoins() {
        return characterCoins;
    }

    public void setCharacterCoins(double characterCoins) {
        this.characterCoins = characterCoins;
    }
}
