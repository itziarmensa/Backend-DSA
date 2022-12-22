package edu.upc.dsa.domain.entity.vo;

public class UserMyObjects {
    String userId;
    String objectId;

    public UserMyObjects() {}
    public UserMyObjects(String userId,String objectId){
        this.userId =userId;
        this.objectId =objectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}