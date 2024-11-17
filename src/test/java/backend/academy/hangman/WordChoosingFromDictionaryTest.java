package backend.academy.hangman;

import backend.academy.hangman.exception.InvalidCategoryException;
import backend.academy.hangman.word.WordChoosingFromDictionary;
import backend.academy.hangman.word.WordHint;
import backend.academy.hangman.word.Category;
import backend.academy.hangman.word.Difficulty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordChoosingFromDictionaryTest {
    private WordChoosingFromDictionary wordChoosingFromDictionary;
    private WordHint wordHint;

    @Test
    @DisplayName("Получение слова из легкой категории 'ANIMALS'")
    public void testGetWordFromEasyCategory() throws InvalidCategoryException {
        wordChoosingFromDictionary = new WordChoosingFromDictionary("words.txt", System.out);
        wordHint = wordChoosingFromDictionary.getWord(Difficulty.EASY, Category.ANIMALS);
        assertNotNull(wordHint);
        assertTrue(wordHint.word().matches("собака|кошка|лошадь|корова|медведь"));
    }

    @Test
    @DisplayName("Получение слова из средней категории 'CITIES'")
    public void testGetWordFromMediumCategory() throws InvalidCategoryException {
        wordChoosingFromDictionary = new WordChoosingFromDictionary("words.txt", System.out);
        wordHint = wordChoosingFromDictionary.getWord(Difficulty.MEDIUM, Category.CITIES);
        assertNotNull(wordHint);
        assertTrue(wordHint.word().matches("сидней|токио|каир|мумбаи|мадрид"));
    }

    @Test
    @DisplayName("Получение слова из сложной категории 'FOOD'")
    public void testGetWordFromHardCategory() throws InvalidCategoryException {
        wordChoosingFromDictionary = new WordChoosingFromDictionary("words.txt", System.out);
        wordHint = wordChoosingFromDictionary.getWord(Difficulty.HARD, Category.FOOD);
        assertNotNull(wordHint);
        assertTrue(wordHint.word().matches("гамбургер|авокадо|суши|круассан|рагу"));
    }

    @Test
    @DisplayName("Выброс исключения, если нет слов в категории")
    public void testInvalidCategoryException() {
        wordChoosingFromDictionary = new WordChoosingFromDictionary("incorrectWords(1).txt", System.out);

        assertThrows(InvalidCategoryException.class, () ->
            wordHint = wordChoosingFromDictionary.getWord(Difficulty.HARD, Category.SPORT)
        );
    }
}
