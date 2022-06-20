package lat.jack.wordle.wordle.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lat.jack.wordle.wordle.Events.Home.onHomeButtonClick;
import lat.jack.wordle.wordle.Main;
import lat.jack.wordle.wordle.Managers.GameManager;
import lat.jack.wordle.wordle.Objects.Button;
import lat.jack.wordle.wordle.State.LoginState;

public class HomeView {

    private Scene scene;
    private Stage stage;
    private Button[] buttons = new Button[2];

    private GameManager gameManager;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox HomeButtons;


    @FXML
    public void setScene(Scene scene) {
        this.scene = scene;
        this.stage = (Stage) scene.getWindow();
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public Stage getStage() {
        return stage;
    }

    @FXML
    protected void initialize() {
        gameManager = Main.getGameManager();

        // Setup Button Mappings

        setupButtons();

        // Display correct buttons depending on the LoginState

        if (gameManager.getLoginState() == LoginState.GUEST_USER) {
            updateButtonText(0, "Login");
            updateButtonText(1, "Register");

        } else {
            updateButtonText(0, "Play");
            updateButtonText(1, "Leaderboard");
        }

        getButton(0).buttonPane.addEventFilter(MouseEvent.MOUSE_PRESSED, new onHomeButtonClick(this, 0));
        getButton(1).buttonPane.addEventFilter(MouseEvent.MOUSE_PRESSED, new onHomeButtonClick(this, 1));

    }

    protected void setupButtons() {

        int i = 0;

        for (Node child : HomeButtons.getChildren()) {

            StackPane stackPane = (StackPane) child;

            Button button = new Button();

            button.buttonView = (ImageView) stackPane.getChildren().get(0);
            button.buttonText = (Text) stackPane.getChildren().get(1);

            button.buttonSize = "Large";
            button.buttonColour = "Green";

            button.buttonPane = stackPane;

            buttons[i] = button;

            i++;

        }

    }

    public Button getButton(Integer buttonID) {
        return buttons[buttonID];
    }

    public void updateButtonText(Integer buttonID, String buttonText) {
        Button button = getButton(buttonID);

        if (button == null) {
            System.out.println("Failed to get button");
            return;
        }

        button.buttonText.setText(buttonText);
        button.buttonValue = buttonText;
    }
}