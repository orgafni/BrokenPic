package com.brokenpicinc.brokenpic.model;

import android.graphics.Bitmap;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by orgaf_000 on 2/4/2017.
 */

public class Model {
    private final static Model instance = new Model();
    ModelFirebase modelFirebase;


    private List<Player> players = new LinkedList<Player>();
    private List<GuessGame> gamesToGuess = new LinkedList<GuessGame>();
    private List<DrawGame> gamesToDraw = new LinkedList<DrawGame>();

    public interface RegisterUserListener{
        public void onSuccess();
        public void onFail(String msg);
    }

    public interface LoginUserListener{
        public void onSuccess();
        public void onFail(String msg);
    }

    public interface GetAllPlayersListener{
        public void onResult(List<Player> players);
        public void onCancel(String msg);
    }

    public interface GetAllGamesToDrawListener{
        public void onResult(List<playerInGame> games);
        public void onCancel(String msg);
    }

    public interface GetImageListener{
        void onSuccess(Bitmap image);
        void onFail();
    }

    private Model(){
        modelFirebase = new ModelFirebase();
    }

    public static Model getInstance(){
        return instance;
    }

    public void getAllPlayers(GetAllPlayersListener listener){
        modelFirebase.getAllPlayersAsync(listener);
    }

    public List<GuessGame> getGamesToGuess(){
        return gamesToGuess; }

    public List<DrawGame> getGamesToDraw(GetAllGamesToDrawListener listener){
        modelFirebase.getAllGamesToDrawAsync(listener);
    }

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
        Player currPlayer = new Player();
        currPlayer.setUniqueID(modelFirebase.getCurrentUserID());
        playersList.add(0, currPlayer);
        Game newGame = new Game(startWord, playersList);
        String gameID = modelFirebase.addGame(newGame);

        // TODO: register this gameID to the pending games of the second player in the list
        modelFirebase.addGameToPendingListOfPlayer(gameID, playersList.get(1).getUniqueID());

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

    public Boolean registerNewUser(String nickname, String email, String pass, String confirmPass, Bitmap profilePhoto, RegisterUserListener listener)
    {
        if (nickname.isEmpty() || email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty())
        {
            listener.onFail("FWields cannot be blank");
            return false;
        }
        if (!pass.equals(confirmPass))
        {
            listener.onFail("Passwords not equal");
            return false;
        }
        modelFirebase.registerUser(nickname, email, pass, profilePhoto, listener);
        return true;
    }

    public Boolean loginNewUser(String email, String pass, LoginUserListener listener)
    {
        if (email.isEmpty() || pass.isEmpty())
        {
            listener.onFail("Fields cannot be blank");
            return false;
        }

        modelFirebase.loginUser(email, pass, listener);
        return true;
    }

    public void getPlayerProfile(String profileUrl, GetImageListener listener)
    {
        modelFirebase.getImage(profileUrl, listener);
    }

}
