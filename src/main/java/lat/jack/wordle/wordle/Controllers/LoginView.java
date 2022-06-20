package lat.jack.wordle.wordle.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import lat.jack.wordle.wordle.Events.Login.onLoginButtonClick;
import lat.jack.wordle.wordle.Main;
import lat.jack.wordle.wordle.Managers.GameManager;
import lat.jack.wordle.wordle.Objects.Button;

import java.util.Timer;
import java.util.TimerTask;

public class LoginView {

    @FXML
    TextField InputUserName;
    @FXML
    PasswordField InputUserPass;
    @FXML
    StackPane ButtonLogin;
    @FXML
    StackPane ButtonBack;
    @FXML
    StackPane ToasterPane;

    private Button[] buttons = new Button[2];

    private GameManager gameManager;

    @FXML
    protected void initialize() {
        gameManager = Main.getGameManager();
        setupButtons();

        getButton(0).buttonPane.addEventFilter(MouseEvent.MOUSE_PRESSED, new onLoginButtonClick(this, 0));
        getButton(1).buttonPane.addEventFilter(MouseEvent.MOUSE_PRESSED, new onLoginButtonClick(this, 1));

    }

    protected void setupButtons() {

        Button submit = new Button();
        submit.buttonPane = ButtonLogin;
        submit.buttonView = (ImageView) ButtonLogin.getChildren().get(0);
        submit.buttonText = (Text) ButtonLogin.getChildren().get(1);
        submit.buttonValue = "Login";
        submit.buttonColour = "Green";
        submit.buttonSize = "Large";

        buttons[0] = submit;

        Button back = new Button();
        back.buttonPane = ButtonBack;
        back.buttonView = (ImageView) ButtonBack.getChildren().get(0);
        back.buttonText = (Text) ButtonBack.getChildren().get(1);
        back.buttonValue = "Back";
        back.buttonColour = "Green";
        back.buttonSize = "Large";

        buttons[1] = back;
    }

    public void toaster(String message) {
        ImageView imageView = (ImageView) ToasterPane.getChildren().get(0);
        Text text = (Text) ToasterPane.getChildren().get(1);

        if (message.length() > 0) {
            imageView.setImage(new Image(String.valueOf(Main.class.getResource("Assets/Toasts/Black.png"))));
        } else {
            imageView.setImage(null);
        }

        text.setText(message);

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        imageView.setImage(null);
                        text.setText("");
                    }
                },
                1250
        );
    }

    public String getUserName() {
        return InputUserName.getText();
    }

    public String getUserPass() {
        return InputUserPass.getText();
    }

    public Button getButton(Integer buttonID) {
        return buttons[buttonID];
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
