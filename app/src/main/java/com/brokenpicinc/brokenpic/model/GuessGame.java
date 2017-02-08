package com.brokenpicinc.brokenpic.model;

/**
 * Created by orgaf_000 on 2/8/2017.
 */

public class GuessGame {
    String drawerName;
    String drawerProfilePhotoPath;
    String imageToGuessPath;

    public GuessGame(String drawerName, String imageToGuessPath, String drawerProfilePhotoPath) {
        this.drawerName = drawerName;
        this.imageToGuessPath = imageToGuessPath;
        this.drawerProfilePhotoPath = drawerProfilePhotoPath;
    }

    public String getDrawerName() {
        return drawerName;
    }

    public void setDrawerName(String drawerName) {
        this.drawerName = drawerName;
    }

    public String getDrawerProfilePhotoPath() {
        return drawerProfilePhotoPath;
    }

    public void setDrawerProfilePhotoPath(String drawerProfilePhotoPath) {
        this.drawerProfilePhotoPath = drawerProfilePhotoPath;
    }

    public String getImageToGuessPath() {
        return imageToGuessPath;
    }

    public void setImageToGuessPath(String imageToGuessPath) {
        this.imageToGuessPath = imageToGuessPath;
    }
}
