package lat.jack.wordle.wordle.Database.Constructs;

import lat.jack.wordle.wordle.Database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    public User() {

        // Check if Connection is running

        Database.checkConnection();


        // Create table if not exists


        String query = "CREATE TABLE IF NOT EXISTS Users ('userID' INTEGER PRIMARY KEY AUTOINCREMENT, 'userName' VARCHAR, 'userPass');";

        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void addUser(String userName, String userPassword) {

        Database.checkConnection();

        String query = "INSERT INTO Users(userName, userPass) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPassword);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Object checkUser(String userName, String userPassword) {

        Database.checkConnection();

        String query = "SELECT * FROM Users WHERE userName = ? AND userPass = ? LIMIT 1";


        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("userID");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static ResultSet getUsers() {

        Database.checkConnection();

        String query = "SELECT userID, userName from Users";

        try {

            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);

            return preparedStatement.executeQuery();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Boolean existsUser(String userName) {

        Database.checkConnection();

        String query = "SELECT * FROM Users WHERE userName = ? LIMIT 1";

        try {

            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userName);

            if (preparedStatement.executeQuery().next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
