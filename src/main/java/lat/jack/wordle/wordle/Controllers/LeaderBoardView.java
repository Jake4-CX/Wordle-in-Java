package lat.jack.wordle.wordle.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import lat.jack.wordle.wordle.Events.Leaderboard.onLeaderboardButtonClick;
import lat.jack.wordle.wordle.Main;
import lat.jack.wordle.wordle.Managers.GameManager;
import lat.jack.wordle.wordle.Objects.Score;
import lat.jack.wordle.wordle.Utils.GetScores;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LeaderBoardView {

    private GameManager gameManager;

    // Global stats
    @FXML
    TableView<Score> LeaderboardTable;
    @FXML
    TableColumn<Score, Integer> RankColumn;
    @FXML
    TableColumn<Score, String> UsernameColumn;
    @FXML
    TableColumn<Score, Integer> ScoreColumn;
    @FXML
    TableColumn<Score, Integer> GamesPlayedColumn;

    // Button
    @FXML
    StackPane HomeButton;

    // Player Stats
    @FXML
    Text PlayedText;
    @FXML
    Text WinText;
    @FXML
    Text CurrentText;
    @FXML
    Text MaxText;

    @FXML
    protected void initialize() {
        this.gameManager = Main.getGameManager();

        // Get & display player's personal statistics

        Score playerScore = GetScores.getUserGameStats(this.gameManager.getUser().userID);

        PlayedText.setText(String.valueOf(playerScore.getUserGamesPlayed()));
        WinText.setText(String.valueOf(Math.round((float) playerScore.getGamesWon() / playerScore.getUserGamesPlayed() * 100)));
        CurrentText.setText(String.valueOf(playerScore.getWinStreak()));
        MaxText.setText(String.valueOf(playerScore.getMaxStreak()));

        // Define relative table values

        RankColumn.setCellValueFactory(new PropertyValueFactory<Score, Integer>("userRankingID"));
        UsernameColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("userName"));
        ScoreColumn.setCellValueFactory(new PropertyValueFactory<Score, Integer>("userScore"));
        GamesPlayedColumn.setCellValueFactory(new PropertyValueFactory<Score, Integer>("userGamesPlayed"));

        HashMap<Integer, String> users =  GetScores.getAllUsers(); // Integer = userID, not autoIncrease

        List<Score> userScores = new ArrayList<>();

        // get ALL the users Scores

        users.forEach((userID, userName) -> {
            Score score = GetScores.getUserGameStats(userID);

            score.userName = userName;

            userScores.add(score);
        });

        // Sort / order the scores (DESC by highest score)

        userScores.sort(Comparator.comparing(Score::getUserScore).reversed());

        // Add the rank integer to the user's score

        AtomicInteger i = new AtomicInteger();

        userScores.forEach(userScore -> {
            i.getAndIncrement();
            userScore.setUserRankingID(i.get());
        });


        // Add to table

        ObservableList<Score> list = FXCollections.observableArrayList();

        list.addAll(userScores);

        LeaderboardTable.setItems(list);

        HomeButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new onLeaderboardButtonClick("HOME"));


    }
}
