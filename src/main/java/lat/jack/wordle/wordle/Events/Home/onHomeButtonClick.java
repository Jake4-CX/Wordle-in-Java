package lat.jack.wordle.wordle.Events.Home;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lat.jack.wordle.wordle.Controllers.HomeView;
import lat.jack.wordle.wordle.Main;
import lat.jack.wordle.wordle.Objects.Button;
import lat.jack.wordle.wordle.State.GameState;
import lat.jack.wordle.wordle.State.LoginState;
import lat.jack.wordle.wordle.Utils.Util;

import java.io.IOException;
import java.util.Objects;

public class onHomeButtonClick implements EventHandler<MouseEvent> {

    private HomeView homeView;
    private int buttonID;

    public onHomeButtonClick(HomeView homeView, int buttonID) {
        this.homeView = homeView;
        this.buttonID = buttonID;

    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        Button button = homeView.getButton(buttonID);

        switch (button.buttonValue.toUpperCase()) {

            case "LOGIN":
                // System.out.println("Load Login Scene");

                Util.switchScene("LoginView.fxml", (Node) mouseEvent.getSource());

                break;
            case "REGISTER":
                // System.out.println("Load Register Scene");

                Util.switchScene("RegisterView.fxml", (Node) mouseEvent.getSource());

                break;
            case "PLAY":
                // System.out.println("Load Game Scene");

                Util.switchScene("GameView.fxml", (Node) mouseEvent.getSource());
                homeView.getGameManager().setGameState(GameState.STARTING);

                break;
            case "LEADERBOARD":
                // System.out.println("Load Leaderboard Scene");

                Util.switchScene("LeaderBoardView.fxml", (Node) mouseEvent.getSource());
                break;
        }

    }
}
