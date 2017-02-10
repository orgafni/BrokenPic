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
    private List<DrawGame> gamesToDraw = new LinkedList<DrawGame>();

    private Model(){
        for (int i =0;i<15;i++){
            Player pl = new Player("myEmail" + i + "@gmail.com", "myNick " + i,"pass" + (i+1) * 3, "/images/myProfile" + i);
            addPlayer(pl);
        }

        for (int i =0;i<12;i++){
            GuessGame game = new GuessGame(players.get(i), "/images/drawPhoto" + i);
            addGameToGuess(game);
        }

        for (int i =0;i<12;i++){
            DrawGame game = new DrawGame(players.get(i), "myWord " + i);
            addGameToDraw(game);
        }
    }

    public static Model getInstance(){
        return instance;
    }

    public List<Player> getAllPlayers(){
        return players;
    }

    public List<GuessGame> getGamesToGuess(){ return gamesToGuess; }
    public List<DrawGame> getGamesToDraw(){ return gamesToDraw; }

    public void addPlayer(Player item){
        players.add(item);
    }
    public void addGameToGuess(GuessGame item){
        gamesToGuess.add(item);
    }
    public void addGameToDraw(DrawGame item){
        gamesToDraw.add(item);
    }

    public void createNewGame(String startWord, List<Player> playersList)
    {
        // TODO: add the game to the remote DB, include the start word and the players list. and me, the creator of the gmae!
        // TODO: register this gameID to the pending games of the first player in the list
    }

    public void advanceGame(GuessGame game, String guessWord)
    {
        // TODO: find the game received in the DB, set the received guessWord as the current player guess.
        // TODO: remove the game from this player pending games.
        // TODO: if there is another player in this game, register the gameID to the pending game of the next player.
        // TODO: if this is the last turn of the game, register this game in the finishedGames of all the players that took part in this game.
    }

    public void advanceGame(DrawGame game, String drawPath)
    {
        // TODO: find the game received in the DB, set the received draw as the current player draw.
        // TODO: remove the game from this player pending games.
        // TODO: register the gameID to the pending game of the next player.
    }

}
