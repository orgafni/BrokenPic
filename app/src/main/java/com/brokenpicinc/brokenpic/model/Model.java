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

    public interface GetImageListener{
        void onSuccess(Bitmap image);
        void onFail();
    }

    private Model(){
        modelFirebase = new ModelFirebase();

//        for (int i =0;i<15;i++){
//            Player pl = new Player("myEmail" + i + "@gmail.com", "myNick " + i,"pass" + (i+1) * 3, "/images/myProfile" + i);
//            addPlayer(pl);
//        }

//        for (int i =0;i<12;i++){
//            GuessGame game = new GuessGame(players.get(i), "/images/drawPhoto" + i);
//            addGameToGuess(game);
//        }
//
//        for (int i =0;i<12;i++){
//            DrawGame game = new DrawGame(players.get(i), "myWord " + i);
//            addGameToDraw(game);
//        }
    }

    public static Model getInstance(){
        return instance;
    }

    public void getAllPlayers(GetAllPlayersListener listener){
        modelFirebase.getAllPlayersAsync(listener);
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
