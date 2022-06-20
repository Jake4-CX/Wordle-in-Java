package lat.jack.wordle.wordle.Events.Game;

import lat.jack.wordle.wordle.Controllers.GameView;
import lat.jack.wordle.wordle.Objects.Tile;
import lat.jack.wordle.wordle.State.GameState;

import java.util.Objects;

public class onEnterKeyPressed {

    public static void enterKeyPressed(GameView gameViewer) {

        // Enter Key has been Pressed

        int currentColumn = gameViewer.getCurrentColumn();
        int currentRow = gameViewer.getCurrentRow();

        if (currentRow == 4) {

            if (gameViewer.hasColumnGuessed(currentColumn)) {
                // This row has already been guessed
                return;
            } else {

                String letters = "qwertyuiopasdfghjklzxcvbnm".toUpperCase();

                String guessedWord = gameViewer.getColumnWord(currentColumn);
                String wordToGuess = gameViewer.getGameManager().getWord().getWord().toUpperCase();

                // Guessed word exists?

                if (!gameViewer.getGameManager().getWord().wordExists(guessedWord)) {
                    gameViewer.toaster("Not in word list");
                    System.out.println("The word '" + guessedWord + "' does not exist");
                    return;
                }

                int i = 0;
                while (currentColumn > i) {
                    if (guessedWord.equalsIgnoreCase(gameViewer.getColumnWord(i))) {
                        gameViewer.toaster("Word already entered");
                        return;
                    }
                    i++;
                }

                // Guessed word equals word?

                if (guessedWord.equals(wordToGuess)) {
                    gameViewer.setColumnTilesType(currentColumn, "Correct");
                    gameViewer.getGameManager().setGameState(GameState.WON);

                    return;

                } else {

                    int tilePos = currentColumn * 5;
                    int tilePosUpper = tilePos + 5;

                    String lettersLeft = guessedWord; // Remove a letter once it's been used

                    while (tilePos < tilePosUpper) {
                        Tile tile = gameViewer.getTile(tilePos);

                        String tileLetter = tile.tileText.getText();
                        int letterPos = letters.indexOf(tileLetter);

                        if (wordToGuess.contains(tileLetter)) {

                            if (wordToGuess.charAt(tilePos - tilePosUpper + 5) == tileLetter.charAt(0)) {

                                gameViewer.updateTileType(tilePos, "Correct");
                                gameViewer.updateKeyType(letterPos, "Correct");

                            } else {

                                if (lettersLeft.contains(tileLetter)) { // If the letter hasn't already been displayed as Present (or Found)
                                    gameViewer.updateTileType(tilePos, "Present");

                                    if (!(Objects.equals(gameViewer.getKey(letterPos).keyType, "Correct"))) { // Don't change Correct -> Present
                                        gameViewer.updateKeyType(letterPos, "Present");
                                    }

                                } else {

                                    // Letter is not in word

                                    gameViewer.updateTileType(tilePos, "Absent");
                                    gameViewer.updateKeyType(letterPos, "Absent");
                                }

                            }

                            lettersLeft = lettersLeft.replace(tileLetter, ""); // Remove Char from list

                        } else {

                            // Letter is not in word

                            gameViewer.updateTileType(tilePos, "Absent");
                            gameViewer.updateKeyType(letterPos, "Absent");

                        }
                        tilePos++;
                    }
                }
            }

            gameViewer.setCurrentColumn(currentColumn + 1);
            gameViewer.setCurrentRow(0);

            System.out.println("Column: " + gameViewer.getCurrentColumn());

            if (gameViewer.getCurrentColumn() == 5) {
                gameViewer.getGameManager().setGameState(GameState.LOSS);
                // gameViewer.toaster(gameViewer.getGameManager().getWord().getWord().toUpperCase());
            }
        } else {

            gameViewer.toaster("Not enough letters");
        }

    }
}
