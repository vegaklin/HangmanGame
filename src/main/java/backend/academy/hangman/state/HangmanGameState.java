package backend.academy.hangman.state;

import backend.academy.hangman.word.Difficulty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import static backend.academy.hangman.game.HangmanGameConstants.HIDDEN_LETTER;
import static backend.academy.hangman.settings.MaxAttempts.MAX_ATTEMPTS_EASY;
import static backend.academy.hangman.settings.MaxAttempts.MAX_ATTEMPTS_HARD;
import static backend.academy.hangman.settings.MaxAttempts.MAX_ATTEMPTS_MEDIUM;

public class HangmanGameState {
    @Getter private final String wordForGuess;
    @Getter private final StringBuilder currentWord;
    @Getter private final int maxAttempts;
    @Getter private int attempts;

    private final List<Character> wrongGuesses;
    private final List<Character> availableLetters;

    public HangmanGameState(String wordForGuess, Difficulty difficulty) throws IllegalArgumentException {
        if (wordForGuess == null || wordForGuess.isEmpty()) {
            throw new IllegalArgumentException("Слово не может быть пустым!");
        }
        this.wordForGuess = wordForGuess.toLowerCase();
        this.currentWord = new StringBuilder(HIDDEN_LETTER.repeat(wordForGuess.length()));
        this.maxAttempts = calculateMaxAttempts(difficulty);
        this.attempts = maxAttempts;
        this.wrongGuesses = new ArrayList<>();
        this.availableLetters = new ArrayList<>();
        for (char c = 'а'; c <= 'я'; c++) {
            availableLetters.add(c);
        }
    }

    public GuessResult guess(char letter) {
        char tempLetter = Character.toLowerCase(letter);
        availableLetters.remove(Character.valueOf(tempLetter));
        if (wordForGuess.indexOf(tempLetter) >= 0) {
            if (currentWord.indexOf(String.valueOf(tempLetter)) >= 0) {
                return GuessResult.ALREADY_GUESSED;
            }
            updateCurrentGuess(tempLetter);
            wrongGuesses.add(tempLetter);
            return GuessResult.OK;
        }
        if (!wrongGuesses.contains(tempLetter)) {
            attempts--;
            wrongGuesses.add(tempLetter);
            return GuessResult.ERROR;
        }
        return GuessResult.ALREADY_GUESSED;
    }

    public boolean isWordGuessed() {
        return currentWord.toString().equals(wordForGuess);
    }

    public boolean isGameOver() {
        return attempts <= 0 || isWordGuessed();
    }

    public String getAvailableLetters() {
        return availableLetters.stream()
            .map(String::valueOf)
            .reduce((a, b) -> a + ", " + b)
            .orElse("");
    }

    public String getWrongGuesses() {
        return wrongGuesses.stream()
            .map(String::valueOf)
            .reduce((a, b) -> a + ", " + b)
            .orElse("");
    }

    private int calculateMaxAttempts(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> MAX_ATTEMPTS_EASY;
            case MEDIUM -> MAX_ATTEMPTS_MEDIUM;
            case HARD -> MAX_ATTEMPTS_HARD;
        };
    }

    private void updateCurrentGuess(char letter) {
        for (int i = 0; i < wordForGuess.length(); i++) {
            if (wordForGuess.charAt(i) == letter) {
                currentWord.setCharAt(i, letter);
            }
        }
    }
}
