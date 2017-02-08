package com.brokenpicinc.brokenpic.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by orgaf_000 on 2/4/2017.
 */

public class Model {
    private final static Model instance = new Model();

    private List<Player> players = new LinkedList<Player>();
    private List<GuessGame> gamesToGuess = new LinkedList<GuessGame>();

    private Model(){
        for (int i =0;i<15;i++){
            Player pl = new Player("myEmail" + i + "@gmail.com", "myNick " + i,"pass" + (i+1) * 3, "/images/myProfile" + i);
            addPlayer(pl);
        }

        for (int i =0;i<12;i++){
            GuessGame game = new GuessGame("drawer" + i, "/image/drawerProfile " + i, "/images/drawPhoto" + i);
            adGameToGuess(game);
        }
    }

    public static Model getInstance(){
        return instance;
    }

    public List<Player> getAllPlayers(){
        return players;
    }

    public List<GuessGame> getGamesToGuess(){
        return gamesToGuess;
    }

    public void addPlayer(Player item){
        players.add(item);
    }
    public void adGameToGuess(GuessGame item){
        gamesToGuess.add(item);
    }

    public void createNewGame(String startWord, List<Player> playersList)
    {
        // TODO: add the game to the remote DB, include the start word and the players list. and me, the creator of the gmae!
        // TODO: register this gameID to the pending games of the first player in the list
    }

}
