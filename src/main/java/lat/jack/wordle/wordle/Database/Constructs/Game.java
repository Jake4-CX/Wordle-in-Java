package lat.jack.wordle.wordle.Database.Constructs;

import lat.jack.wordle.wordle.Database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Game {

    public Game() {

        Database.checkConnection();

        // Create table if not exists

        String query = "CREATE TABLE IF NOT EXISTS Games ('gameID' INTEGER PRIMARY KEY AUTOINCREMENT, 'userID' INTEGER, 'gameWon' INTEGER, 'gamePoints' INTEGER, FOREIGN KEY(userID) REFERENCES Users(userID) ON DELETE CASCADE);";

        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addGame(int userID, boolean gameWon, int gamePoints) {

        Database.checkConnection();

        String query = "INSERT INTO Games('userID', 'gameWon', 'gamePoints') VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, gameWon ? 1 : 0);
            preparedStatement.setInt(3, gamePoints);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getGames(int userID) {

        Database.checkConnection();

        String query = "SELECT * FROM Games WHERE userID = ?";

        try {

            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, userID);

            try {
                return preparedStatement.executeQuery();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }
}
