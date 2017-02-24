package com.brokenpicinc.brokenpic.model;

import android.graphics.Bitmap;

/**
 * Created by orgaf_000 on 2/8/2017.
 */

public class GuessGame {
    String playerName;
    Bitmap playerProfilePhoto;
    Bitmap pictureToGuess;
    String gameID;
    int currTurnIndex;

    public GuessGame() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Bitmap getPlayerProfilePhoto() {
        return playerProfilePhoto;
    }

    public void setPlayerProfilePhoto(Bitmap playerProfilePhoto) {
        this.playerProfilePhoto = playerProfilePhoto;
    }

    public Bitmap getPictureToGuess() {
        return pictureToGuess;
    }

    public void setPictureToGuess(Bitmap pictureToGuess) {
        this.pictureToGuess = pictureToGuess;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public int getCurrTurnIndex() {
        return currTurnIndex;
    }

    public void setCurrTurnIndex(int currTurnIndex) {
        this.currTurnIndex = currTurnIndex;
    }
}
