package backend.academy.hangman.validators;

import backend.academy.hangman.exception.InvalidCharacterException;
import backend.academy.hangman.exception.InvalidLengthException;
import backend.academy.hangman.word.Category;
import backend.academy.hangman.word.Difficulty;
import java.util.Arrays;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HangmanCheckValid {
    public static boolean isValidDifficulty(String input) {
        return Arrays.stream(Difficulty.values())
            .anyMatch(difficulty -> difficulty.name().equalsIgnoreCase(input));
    }

    public static  boolean isValidCategory(String input) {
        return Arrays.stream(Category.values())
            .anyMatch(category -> category.name().equalsIgnoreCase(input));
    }

    public static  void isValidateInput(String input) throws InvalidLengthException, InvalidCharacterException {
        if (input.length() != 1) {
            throw new InvalidLengthException();
        }
        if (!input.matches("[а-яА-ЯёЁ]")) {
            throw new InvalidCharacterException();
        }
    }
}
