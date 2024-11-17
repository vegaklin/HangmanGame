package backend.academy.hangman;

import backend.academy.hangman.exception.InvalidCategoryException;
import backend.academy.hangman.game.HangmanOptionChoosing;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import backend.academy.hangman.game.Game;
import backend.academy.hangman.word.WordChoosingFromDictionary;
import backend.academy.hangman.word.WordHint;
import backend.academy.hangman.word.Category;
import backend.academy.hangman.word.Difficulty;
import backend.academy.hangman.render.ConsoleHangmanPrint;

@ExtendWith(MockitoExtension.class)
class GameTest {

    @InjectMocks
    private Game game;

    @Mock
    private WordChoosingFromDictionary wordMap;

    @Mock
    private ConsoleHangmanPrint hangmanPrint;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream out = new PrintStream(outputStream);

    private Scanner scanner;
    private HangmanOptionChoosing hangmanCategoryDifficulty;

    @Test
    @DisplayName("Корректность старта игры с правильными входными данными")
    void testStartGameWithValidInputs() throws InvalidCategoryException {
        scanner = new Scanner("\nEASY\nANIMALS\nс\nо\nб\nа\nк\nа\n");
        hangmanCategoryDifficulty = new HangmanOptionChoosing();

        game = new Game(wordMap, hangmanPrint, hangmanCategoryDifficulty);
        WordHint wordHint = new WordHint("собака", "Домашнее животное, верный друг человека");
        when(wordMap.getWord(Difficulty.EASY, Category.ANIMALS)).thenReturn(wordHint);
        game.start(scanner, out);

        String output = outputStream.toString();
        assertTrue(output.contains("Выбранная сложность: EASY"));
        assertTrue(output.contains("Поздравляю! Вы отгадали слово: собака"));
    }

    @Test
    @DisplayName("Некорректный ввод сложности")
    void testStartGameWithInvalidDifficulty() throws InvalidCategoryException {
        scanner = new Scanner("\nINVALID\nEASY\nANIMALS\nс\nо\nб\nа\nк\nа\n");
        hangmanCategoryDifficulty = new HangmanOptionChoosing();

        game = new Game(wordMap, hangmanPrint, hangmanCategoryDifficulty);
        WordHint wordHint = new WordHint("собака", "Домашнее животное, верный друг человека");
        when(wordMap.getWord(Difficulty.EASY, Category.ANIMALS)).thenReturn(wordHint);
        game.start(scanner, out);

        String output = outputStream.toString();
        assertTrue(output.contains("Некорректная сложность!"));
    }

    @Test
    @DisplayName("При вводе неверного символа система запрашивается повторный ввод")
    void testInputLetterWithInvalidInput() {
        scanner = new Scanner("12\nf\nа\n");
        hangmanCategoryDifficulty = new HangmanOptionChoosing();

        game = new Game(wordMap, hangmanPrint, hangmanCategoryDifficulty);

        assertEquals('а', game.inputLetter(scanner, out));
        String output = outputStream.toString();
        assertTrue(output.contains("Некорректный ввод! Пожалуйста, введите только одну букву."));
        assertTrue(output.contains("Некорректный ввод! Пожалуйста, введите русскую букву."));
    }

    @Test
    @DisplayName("Нажатие на букву 'h' используется для подсказки")
    void testInputLetterWithHintUsage() {
        scanner = new Scanner("h\n");
        hangmanCategoryDifficulty = new HangmanOptionChoosing();

        game = new Game(wordMap, hangmanPrint, hangmanCategoryDifficulty);

        assertEquals('h', game.inputLetter(scanner, out));
        String output = outputStream.toString();
        assertFalse(output.contains("Некорректный ввод! Пожалуйста, введите русскую букву."));
    }

    @Test
    @DisplayName("Проигрыш после превышения попыток")
    void testGameWithExceededAttempts() throws InvalidCategoryException {
        scanner = new Scanner("\nHARD\nANIMALS\nа\nб\nв\nг\n");
        hangmanCategoryDifficulty = new HangmanOptionChoosing();

        game = new Game(wordMap, hangmanPrint, hangmanCategoryDifficulty);
        WordHint wordHint = new WordHint("тупик", "Птица со смешным названием");
        when(wordMap.getWord(Difficulty.HARD, Category.ANIMALS)).thenReturn(wordHint);
        game.start(scanner, out);

        String output = outputStream.toString();
        assertTrue(output.contains("Игра закончена, вы проиграли! Слово: тупик"));
    }

    @Test
    @DisplayName("Вывод предупреждения при некорректном вводе длиной более одного символа и не русского символа")
    void testGameIgnoresLongInput() throws InvalidCategoryException {
        scanner = new Scanner("\nHARD\nANIMALS\nааа\na\nцукцу\n132n\nт\nу\nп\nи\nк\n");
        hangmanCategoryDifficulty = new HangmanOptionChoosing();

        game = new Game(wordMap, hangmanPrint, hangmanCategoryDifficulty);
        WordHint wordHint = new WordHint("тупик", "Птица со смешным названием");
        when(wordMap.getWord(Difficulty.HARD, Category.ANIMALS)).thenReturn(wordHint);
        game.start(scanner, out);
        String output = outputStream.toString();
//        System.out.println(output);

        assertTrue(output.contains("Некорректный ввод! Пожалуйста, введите только одну букву."));
        assertTrue(output.contains("Некорректный ввод! Пожалуйста, введите русскую букву."));
    }
}
