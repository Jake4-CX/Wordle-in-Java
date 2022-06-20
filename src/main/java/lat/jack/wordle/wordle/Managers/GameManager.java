package lat.jack.wordle.wordle.Managers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import lat.jack.wordle.wordle.Controllers.GameView;
import lat.jack.wordle.wordle.Controllers.HomeView;
import lat.jack.wordle.wordle.Database.Constructs.Game;
import lat.jack.wordle.wordle.Database.Database;
import lat.jack.wordle.wordle.Main;
import lat.jack.wordle.wordle.Objects.Tile;
import lat.jack.wordle.wordle.State.GameState;
import lat.jack.wordle.wordle.State.LoginState;
import lat.jack.wordle.wordle.Utils.Util;
import lat.jack.wordle.wordle.Word;
import lat.jack.wordle.wordle.Database.Constructs.User;

public class GameManager {

    private lat.jack.wordle.wordle.Objects.User user;

    private GameState gameState = GameState.PRE_START;
    private LoginState loginState = LoginState.GUEST_USER;

    private HomeView homeView;
    private GameView gameView;
    private Main main;
    private Word word;
    private Database database;

    public GameManager(Main main) {
        this.main = main;
        this.word = new Word();
        this.database = new Database();

        new User(); // Create User Table if not exists
        new Game(); // Create Game Table if not exists

    }

    public void setGameState(GameState gameState) {

        GameState previousGameState = this.gameState;

        if (previousGameState == gameState) {
            System.out.println("It's already this gamestate");
            return;
        }

        this.gameState = gameState;

        switch (gameState) {

            case STARTING:

                cleanup();
                word.createWord();
                setGameState(GameState.ACTIVE);

                break;

            case ACTIVE:

                System.out.println("Word: " + word.getWord());
                break;

            case WON:

                System.out.println("Congratulations, You have guessed the right word!");

                int column = gameView.getCurrentColumn();

                switch (column) {

                    case 0:
                        gameView.toaster("Genius");
                        break;
                    case 1:
                        gameView.toaster("Magnificent");
                        break;
                    case 2:
                        gameView.toaster("Impressive");
                        break;
                    case 3:
                        gameView.toaster("Splendid");
                        break;
                    case 4:
                        gameView.toaster("Great");
                        break;
                    default: // 6th (or higher, somehow)
                        gameView.toaster("Phew");
                        break;
                }

                recordScore(user.userID, 100 - column * 20);
                setGameState(GameState.END);

                break;

            case LOSS:
                System.out.println("Bad Luck, The word was " + this.getWord().getWord() + ".");
                gameView.toaster(getWord().getWord().toUpperCase());
                recordScore(user.userID, 0);
                setGameState(GameState.END);

                break;
            case END:

                Stage stage = this.main.getStage();

                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(1250),
                        ae -> {
                            setGameState(GameState.PRE_START);
                            Util.switchScene("StatsView.fxml", stage);
                        }));

                timeline.setCycleCount(1);
                timeline.play();

                break;
        }
    }

    public void setLoginState(LoginState loginState) {

        LoginState previousLoginState = this.loginState;

        if (previousLoginState == loginState) {
            System.out.println("It's already this loginstate");
            return;
        }

        this.loginState = loginState;

        switch (loginState) {
            case SIGNED_IN:

                Util.switchScene("HomeView.fxml", this.main.getStage());
                break;
        }
    }

    public void cleanup() {

        for (Tile tile : this.gameView.getTiles()) {
            int tileID = (tile.tileColumn * 5) + tile.tileRow;

            this.gameView.clearTile(tileID);
        }

        int keyID = 0;
        while (keyID < (this.gameView.getKeys().length - 2)) {
            this.gameView.clearKey(keyID);
            keyID++;
        }
    }

    public void recordScore(int userID, int gamePoints) {
        Game.addGame(userID, gamePoints > 0, gamePoints);
    }

    public void setHomeView(HomeView homeView) {
        this.homeView = homeView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public GameState getGameState() {
        return gameState;
    }

    public LoginState getLoginState() {
        return loginState;
    }

    public Word getWord() {
        return word;
    }

    public lat.jack.wordle.wordle.Objects.User getUser() {
        return user;
    }

    public void setUser(lat.jack.wordle.wordle.Objects.User user) {
        this.user = user;
    }
}
