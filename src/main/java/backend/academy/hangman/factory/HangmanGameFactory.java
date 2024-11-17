package backend.academy.hangman.factory;

import backend.academy.hangman.game.Game;
import backend.academy.hangman.game.HangmanOptionChoosing;
import backend.academy.hangman.render.ConsoleHangmanPrint;
import backend.academy.hangman.word.WordChoosingFromDictionary;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HangmanGameFactory {
    private static final String WORDS_FILE_PATH = "words.txt";

    public static Game createGame() {
        WordChoosingFromDictionary wordMap = new WordChoosingFromDictionary(WORDS_FILE_PATH, System.out);
        ConsoleHangmanPrint hangmanPrint = new ConsoleHangmanPrint();
        HangmanOptionChoosing hangmanCategoryDifficulty = new HangmanOptionChoosing();
        return new Game(wordMap, hangmanPrint, hangmanCategoryDifficulty);
    }
}
