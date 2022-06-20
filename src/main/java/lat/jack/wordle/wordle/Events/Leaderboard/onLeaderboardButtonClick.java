package lat.jack.wordle.wordle.Events.Leaderboard;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import lat.jack.wordle.wordle.Controllers.LeaderBoardView;
import lat.jack.wordle.wordle.Utils.Util;

public class onLeaderboardButtonClick implements EventHandler<MouseEvent> {

    String buttonAction;

    public onLeaderboardButtonClick(String buttonAction) {
        this.buttonAction = buttonAction;

    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        if ("HOME".equals(buttonAction.toUpperCase())) {
            // System.out.println("Load Home Scene");

            Util.switchScene("HomeView.fxml", (Node) mouseEvent.getSource());
        }
    }


}
