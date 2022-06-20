package lat.jack.wordle.wordle.Events.Game;

import lat.jack.wordle.wordle.Controllers.GameView;
import lat.jack.wordle.wordle.Objects.Tile;

public class onDeleteKeyPressed {

    public static void deleteKeyPressed(GameView gameViewer) {

        // Backspace / Delete Key pressed

        // Allow deletion only in the current Row <->

        int currentColumn = gameViewer.getCurrentColumn();
        int currentRow = gameViewer.getCurrentRow();

        int tileID = (currentColumn * 5) + currentRow;

        Tile tile = gameViewer.getTile(tileID);

        if (!(tile.tileType.equals("Blank"))) {
            return;
        }

        if (currentRow >= 0) {
            gameViewer.clearTile(tileID);

            if (currentRow >= 1) {
                gameViewer.setCurrentRow(currentRow - 1);
            }
        }
    }
}
