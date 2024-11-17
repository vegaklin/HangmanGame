package backend.academy.hangman.game;

import backend.academy.hangman.exception.InvalidCategoryException;
import backend.academy.hangman.exception.InvalidCharacterException;
import backend.academy.hangman.exception.InvalidLengthException;
import backend.academy.hangman.render.ConsoleHangmanPrint;
import backend.academy.hangman.state.GuessResult;
import backend.academy.hangman.state.HangmanGameState;
import backend.academy.hangman.validators.HangmanCheckValid;
import backend.academy.hangman.word.Category;
import backend.academy.hangman.word.Difficulty;
import backend.academy.hangman.word.WordChoosingFromDictionary;
import backend.academy.hangman.word.WordHint;
import java.io.PrintStream;
import java.util.Scanner;
import static backend.academy.hangman.game.HangmanGameConstants.HINT_MESSAGE;
import static backend.academy.hangman.game.HangmanGameConstants.LINE_LONG;

public class Game {
    private final WordChoosingFromDictionary wordMap;
    private final ConsoleHangmanPrint hangmanPrint;
    private final HangmanOptionChoosing hangmanCategoryDifficulty;

    private WordHint currentWordHint;

    public Game(WordChoosingFromDictionary wordMap,
                ConsoleHangmanPrint hangmanPrint,
                HangmanOptionChoosing hangmanCategoryDifficulty) {
        this.wordMap = wordMap;
        this.hangmanPrint = hangmanPrint;
        this.hangmanCategoryDifficulty = hangmanCategoryDifficulty;
    }

    public void start(Scanner scanner, PrintStream out) throws IllegalArgumentException {
        try {
            HangmanUserInterface.printWelcomeMessage(scanner, out);
            Difficulty difficulty = hangmanCategoryDifficulty.chooseDifficulty(scanner, out);
            Category category = hangmanCategoryDifficulty.chooseCategory(scanner, out);
            currentWordHint = wordMap.getWord(difficulty, category);
            HangmanGameState gameState = new HangmanGameState(currentWordHint.word(), difficulty);
            out.println(LINE_LONG);
            out.println("Выбранная сложность: " + difficulty);
            out.println("Выбранная категория: " + category);
            out.println("Максимальное количество ошибок: " + gameState.maxAttempts());
            playGameLoop(gameState, scanner, out);
            printGameResult(gameState, currentWordHint.word(), out);
        } catch (InvalidCategoryException | IllegalArgumentException e) {
            out.println(e.getMessage());
        }
    }

    private void playGameLoop(HangmanGameState gameState,
                            Scanner scanner,
                            PrintStream out) throws IllegalArgumentException {
        while (!gameState.isGameOver()) {
            out.println(LINE_LONG);
            hangmanPrint.render(gameState, out);
            out.println("[ " + HINT_MESSAGE + " ]");
            char letter = inputLetter(scanner, out);
            out.println(LINE_LONG);
            if (letter == 'h') {
                out.println("Подсказка: " + currentWordHint.hint());
            } else {
                processGuess(gameState, letter, out);
            }
        }
    }

    private void processGuess(HangmanGameState gameState,
                            char letter,
                            PrintStream out) throws IllegalArgumentException {
        GuessResult result = gameState.guess(letter);
        switch (result) {
            case OK -> out.println("Вы угадали букву!");
            case ERROR -> out.println("Буквы " + letter + " нет в слове!");
            case ALREADY_GUESSED -> out.println("Вы уже называли эту букву.");
            default -> throw new IllegalArgumentException("Некорректный результат!");
        }
    }

    public char inputLetter(Scanner scanner, PrintStream out) {
        while (true) {
            out.print("Введите одну букву: ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("h")) {
                return 'h';
            }
            try {
                HangmanCheckValid.isValidateInput(input);
                return input.charAt(0);
            } catch (InvalidLengthException | InvalidCharacterException e) {
                out.println(e.getMessage());
            }
        }
    }

    private void printGameResult(HangmanGameState gameState, String word, PrintStream out) {
        out.println(LINE_LONG);
        if (gameState.isWordGuessed()) {
            out.println("Поздравляю! Вы отгадали слово: " + word);
        } else {
            hangmanPrint.renderFullBody(out);
            out.println("Игра закончена, вы проиграли! Слово: " + word);
        }
    }
}

