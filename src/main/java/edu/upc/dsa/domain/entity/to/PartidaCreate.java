package edu.upc.dsa.domain.entity.to;

import edu.upc.dsa.domain.entity.vo.RandomId;

public class PartidaCreate {
    String email;
    String objectId;
    String characterId;

    public PartidaCreate() {

    }

    public PartidaCreate(String email, String objectId, String characterId) {
        this.email = email;
        this.objectId = objectId;
        this.characterId = characterId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCharacterId() {
        return this.characterId;
    }

    public void setCharacterId(String characterId) {
        this.characterId = characterId;
    }
}
