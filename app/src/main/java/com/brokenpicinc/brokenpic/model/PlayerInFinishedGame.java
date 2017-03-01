package com.brokenpicinc.brokenpic.model;

import android.graphics.Bitmap;

/**
 * Created by orgaf on 01/03/2017.
 */

public class PlayerInFinishedGame {
    String name;
    Bitmap playerProfile;

    playerInGame plInGame;

    public PlayerInFinishedGame() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getPlayerProfile() {
        return playerProfile;
    }

    public void setPlayerProfile(Bitmap playerProfile) {
        this.playerProfile = playerProfile;
    }

    public playerInGame getPlInGame() {
        return plInGame;
    }

    public void setPlInGame(playerInGame plInGame) {
        this.plInGame = plInGame;
    }
}
