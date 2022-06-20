package lat.jack.wordle.wordle.Events.Game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import lat.jack.wordle.wordle.Controllers.GameView;
import lat.jack.wordle.wordle.Objects.Key;
import lat.jack.wordle.wordle.State.GameState;

import java.util.Objects;

public class onGameButtonClick implements EventHandler<MouseEvent> {

    private GameView gameView;
    private Key key;

    public onGameButtonClick(GameView gameView, Key key) {
        this.gameView = gameView;
        this.key = key;
    }

    @Override
    public void handle(MouseEvent event) {

        if (gameView.getGameManager().getGameState() == GameState.ACTIVE) {
            // System.out.println(key.keyValue + " has been pressed"); // Debug

            if (Objects.equals(key.keyValue, "DEL")) {
                onDeleteKeyPressed.deleteKeyPressed(gameView);

            } else if (Objects.equals(key.keyValue, "ENTER")) {
                onEnterKeyPressed.enterKeyPressed(gameView);

            } else if ("abcdefghijklmnopqrstuvwxyz".toUpperCase().contains(key.keyValue)) {
                onCharKeyPressed.CharKeyPressed(gameView, key.keyValue);
            }
        }

    }
}
