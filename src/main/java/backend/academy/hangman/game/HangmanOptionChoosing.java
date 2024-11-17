package backend.academy.hangman.game;

import backend.academy.hangman.validators.HangmanCheckValid;
import backend.academy.hangman.word.Category;
import backend.academy.hangman.word.Difficulty;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Supplier;
import static backend.academy.hangman.game.HangmanGameConstants.MAX_ATTEMPTS_DIFFICULTY_CATEGORY;

public class HangmanOptionChoosing {
    private final Random randomCategory;
    private final Random randomDifficulty;

    public HangmanOptionChoosing() {
        randomCategory = new Random();
        randomDifficulty = new Random();
    }

    public Difficulty chooseDifficulty(Scanner scanner, PrintStream out) {
        return chooseOption(
            Difficulty.class,
            this::chooseRandomDifficulty,
            "Выбери сложность: EASY, MEDIUM, HARD (для RANDOM нажми Enter):",
            "Некорректная сложность!",
            HangmanCheckValid::isValidDifficulty,
            scanner,
            out);
    }

    public Category chooseCategory(Scanner scanner, PrintStream out) {
        return chooseOption(
            Category.class,
            this::chooseRandomCategory,
            "Выбери категорию слов: ANIMALS, CITIES, FOOD, SPORT (для RANDOM нажми Enter):",
            "Некорректная категория!",
            HangmanCheckValid::isValidCategory,
            scanner,
            out);
    }

    private Difficulty chooseRandomDifficulty() {
        Difficulty[] values = Difficulty.values();
        return values[randomDifficulty.nextInt(values.length)];
    }

    private Category chooseRandomCategory() {
        Category[] values = Category.values();
        return values[randomCategory.nextInt(values.length)];
    }

    private <T extends Enum<T>> T chooseOption(Class<T> enumClass,
        Supplier<T> randomChoice,
        String prompt,
        String invalidMessage,
        Predicate<String> validator,
        Scanner scanner,
        PrintStream out) {
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS_DIFFICULTY_CATEGORY) {
            try {
                out.println(prompt);
                String input = scanner.nextLine().toUpperCase();
                if (input.isEmpty()) {
                    return randomChoice.get();
                }
                if (validator.test(input)) {
                    return Enum.valueOf(enumClass, input);
                } else {
                    throw new IllegalArgumentException(invalidMessage);
                }
            } catch (IllegalArgumentException e) {
                out.println(e.getMessage());
                attempts++;
            }
        }
        out.println("Слишком много попыток. Выбран случайный вариант.\n");
        return randomChoice.get();
    }
}
