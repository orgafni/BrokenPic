package com.brokenpicinc.brokenpic.model;

/**
 * Created by orgaf_000 on 2/8/2017.
 */

public class GuessGame {
    Player drawerPlayer;
    String imageToGuessPath;

    public GuessGame(Player drawerPlayer, String imageToGuessPath) {
        this.drawerPlayer = drawerPlayer;
        this.imageToGuessPath = imageToGuessPath;
    }

    public String getDrawerName() {
        return this.drawerPlayer.getName();
    }

    public String getDrawerProfilePhotoPath() {
        return this.drawerPlayer.getImage();
    }

    public String getImageToGuessPath() {
        return imageToGuessPath;
    }

    public void setImageToGuessPath(String imageToGuessPath) {
        this.imageToGuessPath = imageToGuessPath;
    }
}
