package com.brokenpicinc.brokenpic.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by orgaf_000 on 2/15/2017.
 */



public class Game {
    GameState gameState;
    int creationTime;
    int nextTurnIndex;
    List<playerInGame> playersInGame;

    public Game() {
        gameState = GameState.NOT_YET;
        nextTurnIndex = 0;
        playersInGame = new LinkedList<>();
    }

    public Game(String startWord, List<Player> playersList) {
        this();
        nextTurnIndex = 1;
        for (Player pl: playersList) {
            playersInGame.add(new playerInGame(pl));
        }
        playersInGame.get(0).setWord(startWord);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(int creationTime) {
        this.creationTime = creationTime;
    }

    public int getNextTurnIndex() {
        return nextTurnIndex;
    }

    public void setNextTurnIndex(int nextTurnIndex) {
        this.nextTurnIndex = nextTurnIndex;
    }

    public enum GameState
    {
        NOT_YET,
        VICTORY,
        FAILURE
    }
}
