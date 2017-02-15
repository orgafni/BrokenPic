package com.brokenpicinc.brokenpic.model;

/**
 * Created by orgaf_000 on 2/10/2017.
 */

public class DrawGame {
    Player player;
    String wordToDraw;

    public DrawGame(Player player, String wordToDraw) {
        this.player = player;
        this.wordToDraw = wordToDraw;
    }

    public String getPlayerName() {
        return this.player.getName();
    }

    public String getPlayerProfilePhotoPath() {
        return this.player.getImage();
    }

    public String getWordToDraw() {
        return wordToDraw;
    }

    public void setWordToDraw(String wordToDraw) {
        this.wordToDraw = wordToDraw;
    }
}
