package lat.jack.wordle.wordle.Events.Login;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lat.jack.wordle.wordle.Controllers.LoginView;
import lat.jack.wordle.wordle.Database.Constructs.User;
import lat.jack.wordle.wordle.Main;
import lat.jack.wordle.wordle.Objects.Button;
import lat.jack.wordle.wordle.State.LoginState;
import lat.jack.wordle.wordle.Utils.Util;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Objects;

public class onLoginButtonClick implements EventHandler<MouseEvent> {

    private LoginView loginView;
    private int buttonID;

    public onLoginButtonClick(LoginView loginView, int buttonID) {
        this.loginView = loginView;
        this.buttonID = buttonID;

    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        Button button = loginView.getButton(buttonID);

        switch (button.buttonValue.toUpperCase()) {
            case "LOGIN":
                String userName = loginView.getUserName();
                String userPass = loginView.getUserPass();

                if (userName.length() > 0 && userPass.length() > 0) {
                    Object result = User.checkUser(userName, userPass);

                    if (result != null) {

                        lat.jack.wordle.wordle.Objects.User user = new lat.jack.wordle.wordle.Objects.User();
                        user.userID = (int) result;
                        user.userName = userName;

                        loginView.getGameManager().setUser(user);

                        System.out.println("You have been logged in");
                        loginView.getGameManager().setLoginState(LoginState.SIGNED_IN);


                    } else {
                        System.out.println("Incorrect user/pass");
                        loginView.toaster("Incorrect user/pass");
                    }
                } else {
                    System.out.println("No user/pass provided");
                    loginView.toaster("Missing fields");
                }
                break;

            case "BACK":
                Util.switchScene("HomeView.fxml", (Node) mouseEvent.getSource());

                break;

        }

    }
}
