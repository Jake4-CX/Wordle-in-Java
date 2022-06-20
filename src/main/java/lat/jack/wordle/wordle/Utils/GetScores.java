package lat.jack.wordle.wordle.Utils;

import lat.jack.wordle.wordle.Database.Constructs.Game;
import lat.jack.wordle.wordle.Database.Constructs.User;
import lat.jack.wordle.wordle.Objects.Score;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GetScores {

    public static HashMap<Integer, String> getAllUsers() {

        ResultSet users = User.getUsers();

        HashMap<Integer, String> userHashMap = new HashMap<>();
        int userCount = 0;

        try {
            while (users.next()) {
                userHashMap.put(users.getInt("userID"), users.getString("userName"));
                userCount++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userHashMap;

    }

    public static Score getUserGameStats(int userID) {

        int gamesWon = 0;
        int gamesLossed = 0;

        int maxStreak = 0; // Highest win streak the user had
        int winStreak = 0;

        int userScore = 0;
        int totalGames = 0;

        HashMap<Integer, Integer> guessDistribution = new HashMap<>();

        ResultSet games = Game.getGames(userID);


        try {

            while (games.next()) {
                if (games.getInt("gameWon") == 1) {
                    gamesWon++;
                    winStreak++;

                } else {
                    gamesLossed++;

                    winStreak = 0;
                }

                if (winStreak > maxStreak) {
                    maxStreak = winStreak;
                }

                int gamePoints = games.getInt("gamePoints");

                List<Integer> values = Arrays.asList(100, 80, 60, 40, 20);
                int i = values.indexOf(gamePoints); // Turn a games points to position
                if (i != -1) guessDistribution.put(i, guessDistribution.getOrDefault(i, 0) + 1);


                userScore = userScore + gamePoints;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        totalGames = gamesWon + gamesLossed;

        return new Score(userID, "", 0, userScore, totalGames, gamesWon, gamesLossed, winStreak, maxStreak, guessDistribution);
    }
}
