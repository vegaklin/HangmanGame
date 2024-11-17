package backend.academy.hangman;

import backend.academy.hangman.state.GuessResult;
import backend.academy.hangman.state.HangmanGameState;
import backend.academy.hangman.word.Difficulty;
import org.junit.jupiter.api.Test;
import static backend.academy.hangman.settings.MaxAttempts.MAX_ATTEMPTS_EASY;
import static org.junit.jupiter.api.Assertions.*;

class HangmanGameStateTest {
    private HangmanGameState gameState;

    @Test
    void testInitialization() {
        gameState = new HangmanGameState("собака", Difficulty.EASY);

        assertEquals("собака", gameState.wordForGuess());
        assertEquals("______", gameState.currentWord().toString());
        assertEquals(MAX_ATTEMPTS_EASY, gameState.maxAttempts());
        assertEquals(MAX_ATTEMPTS_EASY, gameState.attempts());
        assertTrue(gameState.getAvailableLetters().contains("а"));
        assertTrue(gameState.getAvailableLetters().contains("я"));
        assertTrue(gameState.getWrongGuesses().isEmpty());
    }

    @Test
    void testGuessCorrectLetter() {
        gameState = new HangmanGameState("собака", Difficulty.EASY);

        GuessResult result = gameState.guess('с');

        assertEquals(GuessResult.OK, result);
        assertEquals("с_____", gameState.currentWord().toString());
        assertEquals(MAX_ATTEMPTS_EASY, gameState.attempts());
        assertFalse(gameState.getAvailableLetters().contains("с"));
        assertFalse(gameState.getWrongGuesses().isEmpty());
    }

    @Test
    void testGuessCorrectLetterNotLowerCaseLetter() {
        gameState = new HangmanGameState("собака", Difficulty.EASY);

        GuessResult result = gameState.guess('С');

        assertEquals(GuessResult.OK, result);
        assertEquals("с_____", gameState.currentWord().toString());
        assertEquals(MAX_ATTEMPTS_EASY, gameState.attempts());
        assertFalse(gameState.getAvailableLetters().contains("с"));
    }

    @Test
    void testGuessCorrectLetterNotLowerCaseString() {
        gameState = new HangmanGameState("СОБАКА", Difficulty.EASY);

        GuessResult result = gameState.guess('с');

        assertEquals(GuessResult.OK, result);
        assertEquals("с_____", gameState.currentWord().toString());
        assertEquals(MAX_ATTEMPTS_EASY, gameState.attempts());
        assertFalse(gameState.getAvailableLetters().contains("с"));
        assertFalse(gameState.getWrongGuesses().isEmpty());
    }

    @Test
    void testGuessIncorrectLetter() {
        gameState = new HangmanGameState("собака", Difficulty.EASY);

        GuessResult result = gameState.guess('в');

        assertEquals(GuessResult.ERROR, result);
        assertEquals("______", gameState.currentWord().toString());
        assertEquals(MAX_ATTEMPTS_EASY - 1, gameState.attempts());
        assertFalse(gameState.getAvailableLetters().contains("в"));
        assertTrue(gameState.getWrongGuesses().contains("в"));
    }

    @Test
    void testGuessAlreadyGuessedLetter() {
        gameState = new HangmanGameState("собака", Difficulty.EASY);

        gameState.guess('с');

        GuessResult result = gameState.guess('с');
        assertEquals(GuessResult.ALREADY_GUESSED, result);
        assertEquals("с_____", gameState.currentWord().toString());
        assertEquals(MAX_ATTEMPTS_EASY, gameState.attempts());
    }

    @Test
    void testIsWordGuessed() {
        gameState = new HangmanGameState("собака", Difficulty.MEDIUM);

        gameState.guess('с');
        gameState.guess('о');
        gameState.guess('б');
        gameState.guess('а');
        gameState.guess('к');
        gameState.guess('а');

        assertTrue(gameState.isWordGuessed());
    }

    @Test
    void testIsGameOverWithGuessedWord() {
        gameState = new HangmanGameState("собака", Difficulty.EASY);

        gameState.guess('с');
        gameState.guess('о');
        gameState.guess('б');
        gameState.guess('а');
        gameState.guess('к');
        gameState.guess('а');

        assertTrue(gameState.isGameOver());
        assertTrue(gameState.isWordGuessed());
    }

    @Test
    void testIsGameOverWithNoAttemptsLeft() {
        gameState = new HangmanGameState("собака", Difficulty.HARD);

        gameState.guess('д');
        gameState.guess('г');
        gameState.guess('р');
        gameState.guess('е');

        assertFalse(gameState.isWordGuessed());
        assertTrue(gameState.isGameOver());
    }

    @Test
    void testGetAvailableLetters() {
        gameState = new HangmanGameState("собака", Difficulty.EASY);

        assertTrue(gameState.getAvailableLetters().contains("а"));
        assertTrue(gameState.getAvailableLetters().contains("я"));
    }

    @Test
    void testGetWrongGuesses() {
        gameState = new HangmanGameState("собака", Difficulty.EASY);

        gameState.guess('в');

        assertEquals("в", gameState.getWrongGuesses());
    }

    @Test
    void testConstructorThrowsExceptionForNullWord() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new HangmanGameState(null, Difficulty.EASY);
        });

        assertEquals("Слово не может быть пустым!", exception.getMessage());
    }

    @Test
    void testConstructorThrowsExceptionForEmptyWord() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new HangmanGameState("", Difficulty.EASY);
        });

        assertEquals("Слово не может быть пустым!", exception.getMessage());
    }

    @Test
    void testConstructorThrowsExceptionForInvalidDifficulty() {
        assertThrows(NullPointerException.class, () -> {
            new HangmanGameState("собака", null);
        });
    }
}
