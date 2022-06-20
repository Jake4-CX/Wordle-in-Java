package lat.jack.wordle.wordle.Events.Game;

import lat.jack.wordle.wordle.Controllers.GameView;
import lat.jack.wordle.wordle.Objects.Tile;

import java.util.Objects;

public class onCharKeyPressed {

    public static void CharKeyPressed(GameView gameViewer, String keyPressed) {

        // Letter has been Pressed

        int tileID = nextEmptyTileID(gameViewer);

        if (tileID == -1) {
            return;
        }

        Tile tile = gameViewer.getTile(tileID);

        if (tile.tileRow == 0 && (tileID - 1 >= 0)) {
            Tile previousTile = gameViewer.getTile(tileID - 1);

            if (previousTile.tileType.equals("Blank")) {
                return;
            }
        }

        gameViewer.updateTileLetter(tileID, keyPressed);

        gameViewer.setCurrentRow(tile.tileRow);
        gameViewer.setCurrentColumn(tile.tileColumn);

    }

    protected static int nextEmptyTileID(GameView gameViewer) {
        int column = 0;

        boolean foundTile = false;

        while (column <= 4) {

            int row = 0;

            while (row <= 4) {
                Tile tile = gameViewer.getTile((column * 5) + row);
                if (!Objects.equals(tile.tileText.getText(), "")) {
                    row++;
                } else {
                    foundTile = true;
                    break;
                }
            }

            if (foundTile) {
                return (column * 5) + row;
            }

            column++;
        }

        return -1;
    }
}
