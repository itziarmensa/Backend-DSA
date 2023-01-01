package edu.upc.dsa.domain.entity.vo;

public class UserCharacters {
    String userId;
    String characterId;

    public UserCharacters() {}
    public UserCharacters(String userId,String characterId){
        this.userId =userId;
        this.characterId =characterId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCharacterId() {
        return characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }
}