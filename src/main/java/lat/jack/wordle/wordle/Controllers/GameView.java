package lat.jack.wordle.wordle.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lat.jack.wordle.wordle.Events.Game.onGameButtonClick;
import lat.jack.wordle.wordle.Events.Game.onGameKeyPressed;
import lat.jack.wordle.wordle.Main;
import lat.jack.wordle.wordle.Managers.GameManager;
import lat.jack.wordle.wordle.Objects.Key;
import lat.jack.wordle.wordle.Objects.Tile;

import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class GameView {

    private GameManager gameManager;

    private Tile[] tiles = new Tile[25];
    private Key[] keys = new Key[28];

    private int currentColumn;
    private int currentRow;

    @FXML
    private VBox VBoxGameBoard;
    @FXML
    private AnchorPane keyBoard;
    @FXML
    private StackPane toasterPane;

    private HashMap<String, String> tilePaths = new HashMap<>() {{
        put("Absent", "Assets/Tiles/Absent.png");
        put("Blank", "Assets/Tiles/Blank.png");
        put("Correct", "Assets/Tiles/Correct.png");
        put("Present", "Assets/Tiles/Present.png");
    }};

    private HashMap<String, String> keyPaths = new HashMap<>() {{
        put("Absent", "Assets/Keyboard/Absent.png");
        put("Blank", "Assets/Keyboard/Blank.png");
        put("Correct", "Assets/Keyboard/Correct.png");
        put("Present", "Assets/Keyboard/Present.png");
    }};

    @FXML
    protected void initialize() {
        gameManager = Main.getGameManager();
        gameManager.setGameView(this);

        // Setup Grid

        setupGameBoard();

        // Setup Keyboard Mappings

        setupKeyBoard();

        GameView gameView = this;

        // Enable onKeyPressed Event (Scene cannot be accessed during initialization)

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                VBoxGameBoard.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new onGameKeyPressed(gameView));
            }
        }, 250);

    }

    // Setup Game Grid

    private void setupGameBoard() {

        int tileCount = 0;
        currentColumn = 0;  // |
        currentRow = 0;     // -

        for (Node child : VBoxGameBoard.getChildren()) { // Loop VBox - Column

            for (Node secondChild : ((HBox) child).getChildren()) { // Loop HBox - Row

                StackPane stackPane = (StackPane) secondChild;

                Tile tile = new Tile();

                tile.tileView = (ImageView) stackPane.getChildren().get(0);
                tile.tileText = (Text) stackPane.getChildren().get(1);
                tile.tileType = "Blank";
                tile.tileColumn = (int) Math.floor(tileCount / 5d);
                tile.tileRow = (tileCount % 5);

                tiles[tileCount] = tile;

                tileCount++;

                tile.tileView.setImage(new Image(String.valueOf(Main.class.getResource(tilePaths.get(tile.tileType)))));
            }
        }

    }

    // Setup Keyboard

    private void setupKeyBoard() {

        char[] allowedLetters = "qwertyuiopasdfghjklzxcvbnm".toUpperCase().toCharArray();
        int keyCount = 0;

        for (Node child : keyBoard.getChildren()) {

            StackPane stackPane = (StackPane) child;

            ImageView imageView = (ImageView) stackPane.getChildren().get(0);
            Text text = (Text) stackPane.getChildren().get(1);

            Key key = new Key();

            key.keyView = imageView;
            key.keyText = text;
            key.keyType = "Blank";

            text.setFont(Font.font(Font.getDefault().getName(), FontWeight.SEMI_BOLD, 16));

            if (keyCount < 26) { // a-z
                text.setText(String.valueOf(allowedLetters[keyCount]));
                key.keyValue = String.valueOf(allowedLetters[keyCount]);

            } else if (keyCount == 26) {
                text.setText("DEL");
                key.keyValue = "DEL";

            } else if (keyCount == 27) {
                text.setText("ENTER");
                key.keyValue = "ENTER";

            }

            keys[keyCount] = key;

            stackPane.addEventFilter(MouseEvent.MOUSE_PRESSED, new onGameButtonClick(this, key));

            keyCount++;
        }
    }

    public void toaster(String message) { // Toaster (Alerts dialog - Congrats, Word not Found etc)
        ImageView imageView = (ImageView) toasterPane.getChildren().get(0);
        Text text = (Text) toasterPane.getChildren().get(1);

        if (message.length() > 0) {
            imageView.setImage(new Image(String.valueOf(Main.class.getResource("Assets/Toasts/Black.png"))));
        } else {
            imageView.setImage(null);
        }

        text.setText(message);

        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        imageView.setImage(null);
                        text.setText("");
                    }
                },
                1250
        );
    }

    public void updateTileType(Integer tileID, String tileType) {
        Tile tile = getTile(tileID);

        if (tile == null) {
            System.out.println("Failed to get tile");
            return;
        }

        String tilePath;
        if ((tilePath = tilePaths.get(tileType)) == null) {
            System.out.println("TilePath does not exist");
            return;
        }

        tile.tileType = tileType;
        tile.tileView.setImage(new Image(String.valueOf(Main.class.getResource(tilePath))));

        if (!tileType.equals("Blank")) {
            tile.tileText.setFill(Color.WHITE);
        } else {
            tile.tileText.setFill(Color.BLACK);
        }
    }

    public void updateTileLetter(Integer tileID, String letter) {

        Tile tile = getTile(tileID);

        if (tile == null) {
            System.out.println("Failed to get tile");
            return;
        }

        tile.tileText.setText(letter);
    }

    public void updateKeyType(Integer keyID, String keyType) {
        Key key = getKey(keyID);

        if (key == null) {
            System.out.println("Failed to get Key.");
        }

        key.keyType = keyType;

        String keyPath;
        if ((keyPath = keyPaths.get(keyType)) == null) {
            System.out.println("Falied to update Key - key type: '" + keyType + "' does not exist");
            return;
        }

        key.keyView.setImage(new Image(String.valueOf(Main.class.getResource(keyPath))));

        if (!keyType.equals("Blank")) {
            key.keyText.setFill(Color.WHITE);
        } else {
            key.keyText.setFill(Color.BLACK);
        }

    }

    public void clearTile(Integer tileID) {
        updateTileType(tileID, "Blank");
        updateTileLetter(tileID, "");
    }

    public void clearKey(Integer keyID) {
        updateKeyType(keyID, "Blank");
    }

    // Getters and Setters

    public Tile getTile(int tileID) {
        return this.tiles[tileID];
    }

    public Tile[] getTiles() {
        return this.tiles;
    }

    public Key getKey(int keyID) {
        return this.keys[keyID];
    }

    public Key[] getKeys() {
        return this.keys;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getColumnWord(Integer column) {

        int tilePos = (column * 5);
        int tilePosUpper = tilePos + 5;

        String word = "";

        while (tilePos < tilePosUpper) {
            word = word.concat(tiles[tilePos].tileText.getText());
            tilePos++;
        }

        return word;

    }

    public boolean hasColumnGuessed(Integer column) {

        int tilePos = (column * 5);
        // If one tile in a column is blank, then all must also be blank

        return !Objects.equals(tiles[tilePos].tileType, "Blank");

    }

    public void setColumnTilesType(Integer column, String tileType) {
        int tilePos = (column * 5);
        int tilePosUpper = tilePos + 5;

        while (tilePos < tilePosUpper) {
            updateTileType(tilePos, tileType);
            tilePos++;
        }

    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
