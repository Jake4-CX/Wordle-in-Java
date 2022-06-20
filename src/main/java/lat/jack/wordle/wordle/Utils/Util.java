package lat.jack.wordle.wordle.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lat.jack.wordle.wordle.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Util {

    public static List<String> readFileAsList(String fileURL) {

        List<String> list = new ArrayList<>();

        try {
            InputStream inputStream = (Main.class.getResourceAsStream(fileURL));
            Scanner scanner = new Scanner(inputStream);

            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }

        } catch (Exception e) {
            System.out.println("An error occurred whilst reading '" + fileURL + "'");
            e.printStackTrace();
        }

        return list;
    }

    public static void switchScene(String fxmlPath, Node node) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxmlPath)));
            Stage stage = (Stage) node.getScene().getWindow(); // Get stage from node
            stage.setScene(new Scene(root, 520, 820));

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    public static void switchScene(String fxmlPath, Stage stage) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxmlPath)));
            stage.setScene(new Scene(root, 520, 820));

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
}
