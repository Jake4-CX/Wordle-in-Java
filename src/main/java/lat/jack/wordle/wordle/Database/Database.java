package lat.jack.wordle.wordle.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class Database {

    private static Connection connection;
    private Timer keepAlive;

    public Database() {

        keepAlive = new Timer();

        keepAlive.schedule(new TimerTask() { // Timer - to keep alive connection whilst application is open
            @Override
            public void run() {
                checkConnection();

            }
        }, 0, 60 * 1000); // in ms


    }

    public static void getNewConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static Connection getConnection() {
        return connection;
    }

    public static void checkConnection() {

        try {
            if (connection == null || connection.isClosed()) {
                getNewConnection();
            } else {
                connection.createStatement().execute("SELECT 1");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
