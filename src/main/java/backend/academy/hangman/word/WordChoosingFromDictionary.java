package backend.academy.hangman.word;

import backend.academy.hangman.exception.InvalidCategoryException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class WordChoosingFromDictionary {
    private final Random random;

    private final HashMap<Category, List<WordHint>> easyWordsWithCategory;
    private final HashMap<Category, List<WordHint>> mediumWordsWithCategory;
    private final HashMap<Category, List<WordHint>> hardWordsWithCategory;

    public static final int MIN_WORD_LENGTH = 3;
    public static final int MAX_WORD_LENGTH = 12;
    public static final int MAX_PARTS_LENGTH = 4;
    public static final int DIFFICULTY_INDEX = 0;
    public static final int CATEGORY_INDEX = 1;
    public static final int WORD_INDEX = 2;
    public static final int HINT_INDEX = 3;

    public WordChoosingFromDictionary(String filePath, PrintStream out) {
        random = new Random();
        easyWordsWithCategory = new HashMap<>();
        mediumWordsWithCategory = new HashMap<>();
        hardWordsWithCategory = new HashMap<>();
        try {
            readWordsFromFile(filePath);
        } catch (IllegalArgumentException | IOException e) {
            out.println(e.getMessage());
            System.exit(0);
        }
    }

    public WordHint getWord(Difficulty difficulty, Category category) throws InvalidCategoryException {
        return switch (difficulty) {
            case EASY -> getRandomWordFromEasy(category);
            case MEDIUM -> getRandomWordFromMedium(category);
            case HARD -> getRandomWordFromHard(category);
        };
    }

    private void readWordsFromFile(String filePath) throws IOException, IllegalArgumentException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new FileNotFoundException("Файл не найден в ресурсах");
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length != MAX_PARTS_LENGTH) {
                    throw new IllegalArgumentException("Неверный формат строки: " + line);
                }
                Difficulty difficulty = Difficulty.valueOf(parts[DIFFICULTY_INDEX].toUpperCase());
                Category category = Category.valueOf(parts[CATEGORY_INDEX].toUpperCase());
                String word = parts[WORD_INDEX].trim();
                String hint = parts[HINT_INDEX].trim();
                if (word.length() < MIN_WORD_LENGTH || word.length() > MAX_WORD_LENGTH) {
                    throw new IllegalArgumentException("Слово " + word + " не подходит по длине.");
                }
                WordHint wordHint = new WordHint(word, hint);
                addWordToCategory(difficulty, category, wordHint);
            }
        }
    }

    private void addWordToCategory(Difficulty difficulty, Category category, WordHint wordHint) {
        HashMap<Category, List<WordHint>> targetMap = getMapByDifficulty(difficulty);
        List<WordHint> wordHints = targetMap.getOrDefault(category, new ArrayList<>());
        wordHints.add(wordHint);
        targetMap.put(category, wordHints);
    }

    private HashMap<Category, List<WordHint>> getMapByDifficulty(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> easyWordsWithCategory;
            case MEDIUM -> mediumWordsWithCategory;
            case HARD -> hardWordsWithCategory;
        };
    }

    private WordHint getRandomWordFromEasy(Category category) throws InvalidCategoryException {
        List<WordHint> values = easyWordsWithCategory.get(category);
        if (values == null || values.isEmpty()) {
            throw new InvalidCategoryException();
        }
        return values.get(random.nextInt(values.size()));
    }

    private WordHint getRandomWordFromMedium(Category category) throws InvalidCategoryException {
        List<WordHint> values = mediumWordsWithCategory.get(category);
        if (values == null || values.isEmpty()) {
            throw new InvalidCategoryException();
        }
        return values.get(random.nextInt(values.size()));
    }

    private WordHint getRandomWordFromHard(Category category) throws InvalidCategoryException {
        List<WordHint> values = hardWordsWithCategory.get(category);
        if (values == null || values.isEmpty()) {
            throw new InvalidCategoryException();
        }
        return values.get(random.nextInt(values.size()));
    }
}
