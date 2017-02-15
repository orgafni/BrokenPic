package com.brokenpicinc.brokenpic.model;

/**
 * Created by orgaf_000 on 2/15/2017.
 */

public class playerInGame
{
    String playerID;

    // Each game has either word or picturePath, never both.
    String word;
    String picturePath;

    public playerInGame() {
    }

    public playerInGame(Player player) {
        playerID = player.getUniqueID();
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
