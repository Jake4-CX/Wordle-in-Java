package lat.jack.wordle.wordle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lat.jack.wordle.wordle.Controllers.HomeView;
import lat.jack.wordle.wordle.Controllers.LoginView;
import lat.jack.wordle.wordle.Managers.GameManager;

import java.io.IOException;

public class Main extends Application {

    private Stage stage;
    private Scene scene;
    private HomeView homeView;
    private LoginView loginView;

    static GameManager gameManager;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        handleStop(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomeView.fxml"));


        // Create GameManager

        gameManager = new GameManager(this);

        // Create Scene

        this.scene = new Scene(fxmlLoader.load(), 520, 820); // 744
        this.homeView = fxmlLoader.getController();
        this.homeView.setScene(this.scene);

        gameManager.setHomeView(this.homeView);

        this.stage = stage;

        stage.setResizable(false);
        stage.setTitle("Wordle");
        stage.setScene(scene);

        stage.show();
    }

    public void handleStop(Stage stage) { // Make sure all tasks running on other threads also stop (SQL)
        Platform.setImplicitExit(true);
        stage.setOnCloseRequest((ae) -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public Stage getStage() {
        return this.stage;
    }

    public Scene getScene() {
        return this.scene;
    }

    public HomeView getHomeView() {
        return homeView;
    }

    public static GameManager getGameManager() {
        return gameManager;
    }
}