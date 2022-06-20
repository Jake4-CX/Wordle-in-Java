package lat.jack.wordle.wordle.Events.Register;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import lat.jack.wordle.wordle.Controllers.RegisterView;
import lat.jack.wordle.wordle.Database.Constructs.User;
import lat.jack.wordle.wordle.Objects.Button;
import lat.jack.wordle.wordle.State.LoginState;
import lat.jack.wordle.wordle.Utils.Util;


public class onRegisterButtonClick implements EventHandler<MouseEvent> {

    private RegisterView registerView;
    private int buttonID;

    public onRegisterButtonClick(RegisterView registerView, int buttonID) {
        this.registerView = registerView;
        this.buttonID = buttonID;

    }


    @Override
    public void handle(MouseEvent mouseEvent) {

        Button button = registerView.getButton(buttonID);

        switch (button.buttonValue.toUpperCase()) {
            case "REGISTER":


                String userName = this.registerView.getUserName();
                String userPass = this.registerView.getUserPass();

                // Required parameters to meet

                if (userName.length() > 0 && userPass.length() > 0) {

                    if (!userName.matches("[a-zA-Z0-9]*")) {
                        this.registerView.toaster("Illegal UserName");
                        break;
                    }

                    if (userPass.matches(".*[^A-z0-9!@?#$£*]")) {
                        this.registerView.toaster("Illegal UserPass");
                        break;
                    }

                    if (User.existsUser(userName)) {

                        this.registerView.toaster("Username already taken");
                        break;
                    }

                    User.addUser(userName, userPass);

                    // Check if account has been created

                    Object result = User.checkUser(userName, userPass);

                    if (result != null) {

                        this.registerView.toaster("Account registered");

                        // Build user

                        lat.jack.wordle.wordle.Objects.User user = new lat.jack.wordle.wordle.Objects.User();
                        user.userID = (int) result;
                        user.userName = userName;

                        // Set user

                        registerView.getGameManager().setUser(user);

                        System.out.println("You have been logged in");

                        this.registerView.getGameManager().setLoginState(LoginState.SIGNED_IN);


                    } else {

                        // Failed to register

                        System.out.println("Failed to register account");
                    }


                } else {

                    this.registerView.toaster("Missing Fields");
                }


                break;
            case "BACK":
                Util.switchScene("HomeView.fxml", (Node) mouseEvent.getSource());
                break;
        }
    }
}
