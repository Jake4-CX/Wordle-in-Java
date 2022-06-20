package lat.jack.wordle.wordle.Events.Stats;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import lat.jack.wordle.wordle.Controllers.StatsView;
import lat.jack.wordle.wordle.State.GameState;
import lat.jack.wordle.wordle.Utils.Util;

public class onStatsButtonClick implements EventHandler<MouseEvent> {

    StatsView statsView;
    String buttonAction;


    public onStatsButtonClick(StatsView statsView, String buttonAction) {
        this.statsView = statsView;
        this.buttonAction = buttonAction;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        switch (buttonAction.toUpperCase()) {
            case "HOME":
                // System.out.println("Load Home Scene");

                Util.switchScene("HomeView.fxml", (Node) mouseEvent.getSource());

                break;
            case "PLAY":
                // System.out.println("Load Game Scene");
                Util.switchScene("GameView.fxml", (Node) mouseEvent.getSource());
                statsView.getGameManager().setGameState(GameState.STARTING);
                break;
        }
    }
}
