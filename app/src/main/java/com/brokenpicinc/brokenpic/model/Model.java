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
    final public static String DrawType = "Draw";
    final public static String GuessType = "Guess";

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

    public interface GetPlayerListener{
        public void onResult(Player player);
        public void onCancel(String msg);
    }

    public interface GetAllGamesToDrawListener{
        public void onResult(List<String> games);
        public void onCancel(String msg);
    }

    public interface GetImageListener{
        void onSuccess(Bitmap image);
        void onFail();
    }

    public interface GetNumericResultListener {
        void onSuccess(int res);
        void onFail();
    }


    public interface GetDrawGameDetailsListener{
        void onResult(Bitmap playerProfilePhoto, String playerName, String wordToDraw, String gameID, int currTurnIndex);
        void onFail(String msg);
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

    public void getGamesToDraw(GetAllGamesToDrawListener listener){
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
        modelFirebase.addGameToPendingListOfPlayer(gameID, playersList.get(1).getUniqueID(), DrawType);

    }

    public void advanceGame(GuessGame game, String guessWord)
    {
        // TODO: find the game received in the DB, set the received guessWord as the current player guess.
        // TODO: remove the game from this player pending games.
        // TODO: if there is another player in this game, register the gameID to the pending game of the next player.
        // TODO: if this is the last turn of the game, register this game in the finishedGames of all the players that took part in this game.
    }

    public void advanceGame(final DrawGame game, Bitmap draw)
    {
        // TODO: find the game received in the DB, set the received draw as the current player draw.
        modelFirebase.SetDrawToGame(game.getGameID(), game.getCurrTurnIndex(), draw);

        modelFirebase.advancedGameTurnIndex(game.getGameID(), game.getCurrTurnIndex() + 1);

        // TODO: remove the game from this player pending games.
        modelFirebase.removePendingGame(game.getGameID());

        // TODO: register the gameID to the pending game of the next player.

        modelFirebase.getPlayerInGameByIndex(game.getGameID(), game.getCurrTurnIndex() + 1, new ModelFirebase.GetMyTurnListener() {
            @Override
            public void onSuccess(playerInGame res) {
                String nextPlayerId = res.getPlayerID();
                modelFirebase.addGameToPendingListOfPlayer(game.getGameID(), nextPlayerId, GuessType);
            }

            @Override
            public void onFail() {

            }
        });

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

    public void getDrawGameDetails(final String gameId, final Model.GetDrawGameDetailsListener listener) {
        modelFirebase.getGameTurnIndex(gameId, new GetNumericResultListener(){
                    @Override
                    public void onSuccess(final int nextTurnIndex) {
                        modelFirebase.getPlayerInGameByIndex(gameId, nextTurnIndex - 1, new ModelFirebase.GetMyTurnListener()
                        {
                            @Override
                            public void onSuccess(final playerInGame plInGame) {
                                modelFirebase.getPlayerDetailsByPlayerID(plInGame.getPlayerID(), new GetPlayerListener(){
                                    @Override
                                    public void onResult(final Player player) {
                                        modelFirebase.getImage(player.getImage(), new GetImageListener() {
                                            @Override
                                            public void onSuccess(Bitmap image) {
                                                listener.onResult(image, player.getName(), plInGame.getWord(), gameId, nextTurnIndex);
                                            }

                                            @Override
                                            public void onFail() {
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancel(String msg) {
                                    }
                                });
                            }
                            @Override
                            public void onFail() {
                            }
                        });
                    }
                    @Override
                    public void onFail() {
                    }
                }
        );
    }
}
