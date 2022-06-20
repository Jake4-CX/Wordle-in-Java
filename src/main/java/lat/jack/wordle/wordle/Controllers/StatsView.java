package lat.jack.wordle.wordle.Controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import lat.jack.wordle.wordle.Database.Constructs.Game;
import lat.jack.wordle.wordle.Events.Stats.onStatsButtonClick;
import lat.jack.wordle.wordle.Main;
import lat.jack.wordle.wordle.Managers.GameManager;
import lat.jack.wordle.wordle.Objects.Score;
import lat.jack.wordle.wordle.Utils.GetScores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StatsView {

    private GameManager gameManager;

    @FXML
    Text PlayedText;
    @FXML
    Text WinText;
    @FXML
    Text CurrentText;
    @FXML
    Text MaxText;
    @FXML
    StackPane PlayButton;
    @FXML
    StackPane HomeButton;
    @FXML
    BarChart<Integer, String> BarChart;


    HashMap<Integer, Integer> RowHistory = new HashMap<>();


    @FXML
    protected void initialize() {
        gameManager = Main.getGameManager();

        // Get & display player's statistics

        Score userScore = GetScores.getUserGameStats(this.gameManager.getUser().userID);

        PlayedText.setText(String.valueOf(userScore.getUserGamesPlayed()));
        WinText.setText(String.valueOf(Math.round((float) userScore.getGamesWon() / userScore.getUserGamesPlayed() * 100)));
        CurrentText.setText(String.valueOf(userScore.getWinStreak()));
        MaxText.setText(String.valueOf(userScore.getMaxStreak()));

        // Define barchart

        XYChart.Series<Integer, String> series = new XYChart.Series<Integer, String>();

        HashMap<Integer, Integer> guessDistribution = userScore.getGuessDistribution();

        series.getData().add(new XYChart.Data<>(guessDistribution.getOrDefault(4, 0) , "5"));
        series.getData().add(new XYChart.Data<>(guessDistribution.getOrDefault(3, 0) , "4"));
        series.getData().add(new XYChart.Data<>(guessDistribution.getOrDefault(2, 0) , "3"));
        series.getData().add(new XYChart.Data<>(guessDistribution.getOrDefault(1, 0) , "2"));
        series.getData().add(new XYChart.Data<>(guessDistribution.getOrDefault(0, 0) , "1"));

        BarChart.getData().addAll(series);

        BarChart.setLegendVisible(false);

        // Map buttons to click event

        PlayButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new onStatsButtonClick(this, "PLAY"));
        HomeButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new onStatsButtonClick(this, "HOME"));


    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
