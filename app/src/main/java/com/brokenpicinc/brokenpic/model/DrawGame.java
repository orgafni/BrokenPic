package com.brokenpicinc.brokenpic.model;

import android.graphics.Bitmap;

/**
 * Created by orgaf_000 on 2/10/2017.
 */

public class DrawGame {
    String wordToDraw;
    String playerName;
    Bitmap playerProfile;

    public DrawGame() {
    }

    public String getWordToDraw() {
        return wordToDraw;
    }

    public void setWordToDraw(String wordToDraw) {
        this.wordToDraw = wordToDraw;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Bitmap getPlayerProfile() {
        return playerProfile;
    }

    public void setPlayerProfile(Bitmap playerProfile) {
        this.playerProfile = playerProfile;
    }
}
