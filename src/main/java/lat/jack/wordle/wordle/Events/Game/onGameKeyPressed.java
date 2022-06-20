package lat.jack.wordle.wordle.Events.Game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lat.jack.wordle.wordle.Controllers.GameView;
import lat.jack.wordle.wordle.State.GameState;

import java.util.Arrays;

public class onGameKeyPressed implements EventHandler<KeyEvent> {

    GameView gameView;
    public onGameKeyPressed(GameView gameView) {
        this.gameView = gameView;
    }


    @Override
    public void handle(KeyEvent event) {


        if (gameView.getGameManager().getGameState() == GameState.ACTIVE) {
            KeyCode keyCode = event.getCode();
            event.consume();

            if (keyCode.toString().equals("ENTER")) {
                //  onEnterKeyPressed

                onEnterKeyPressed.enterKeyPressed(gameView);

            } else if (Arrays.asList(KeyCode.BACK_SPACE, KeyCode.DELETE).contains(keyCode)) {
                //  onDeleteKeyPressed

                onDeleteKeyPressed.deleteKeyPressed(gameView);

            } else {
                char[] allowedLetters = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

                if (new String(allowedLetters).contains(keyCode.toString())) {
                    //  onCharKeyPressed

                    onCharKeyPressed.CharKeyPressed(gameView, keyCode.toString());

                }

            }
        }

    }
}
