package com.brokenpicinc.brokenpic.model;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by orgaf on 01/03/2017.
 */

public class FinishedGame {
    int index;
    String finishTime;
    boolean isVictory;

    HashMap<String, PlayerInFinishedGame> players;

    public FinishedGame() {
        players = new HashMap<>();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }


    public boolean isVictory() {
        return isVictory;
    }

    public void setVictory(boolean victory) {
        isVictory = victory;
    }

    public void allocatePlayer(String playerID)
    {
        this.players.put(playerID, new PlayerInFinishedGame());
    }
    public void addPlayer( String playerID, String name, Bitmap playerProfile, playerInGame plInGame)
    {
        this.players.get(playerID).setName(name);
        this.players.get(playerID).setPlayerProfile(playerProfile);
        this.players.get(playerID).setPlInGame(plInGame);
    }
}
