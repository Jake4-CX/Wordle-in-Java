package lat.jack.wordle.wordle;

import lat.jack.wordle.wordle.Utils.Util;

import java.util.List;
import java.util.Random;

public class Word {
    String word;

    List<String> wordChoices;
    List<String> wordsPossible;

    public Word() {

        wordChoices = Util.readFileAsList("Words/choices.txt");
        wordsPossible = Util.readFileAsList("Words/possible.txt");

        wordsPossible.addAll(wordChoices);

    }

    public void createWord() { // get and set a random word from the list
        int randomID = new Random().nextInt(wordChoices.size());
        word = wordChoices.get(randomID);

    }

    public String getWord() { // GETTER
        return word;
    }

    public boolean wordExists(String potentialWord) { // is valid word?
        return wordsPossible.contains(potentialWord.toLowerCase());
    }
}
