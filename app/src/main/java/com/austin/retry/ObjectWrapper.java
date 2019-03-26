package com.austin.retry;

public class ObjectWrapper {

    //concatonate a string of settings to get a CSV like "20,15,100" or something
    private String settings;
    //they pick an image from the database and then make that image's id this image id
    private int imageId;
    //when they hit set name or something or whatever it'll name it
    private String objectName;
    //when you insert the object take the id somehow i don't know right now
    private int objectId;

    public int getImage_Id() {
        return imageId;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getSettings() {
        return settings;
    }

    public void setImage_Id(int imageId) {
        this.imageId = imageId;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }
}
