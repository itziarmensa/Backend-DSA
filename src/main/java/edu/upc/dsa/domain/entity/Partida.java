package edu.upc.dsa.domain.entity;

import edu.upc.dsa.domain.entity.vo.RandomId;

public class Partida {
    String partidaId;
    String email;
    String objectId;
    String characterId;
    int mapa;
    int level;
    int points;
    Boolean finished;

    public Partida() {

    }

    public Partida(String email, String objectId, String characterId) {
        this.partidaId = RandomId.getId();
        this.email = email;
        this.objectId = objectId;
        this.characterId = characterId;
        this.mapa = 1;
        this.level = 1;
        this.points = 0;
        this.finished = false;
    }

    public String getPartidaId() {
        return this.partidaId;
    }

    public void setPartidaId(String partidaId) {
        this.partidaId = partidaId;
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

    public int getMapa() {
        return this.mapa;
    }

    public void setMapa(int mapa) {
        this.mapa = mapa;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean getFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
