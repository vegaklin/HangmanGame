package backend.academy.hangman.game;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HangmanGameConstants {
    private static final String YAML_FILE_PATH = "application.yaml";

    private final ConfigParser configParser = new ConfigParser(YAML_FILE_PATH);

    public static final String LINE_LONG = configParser.get("LINE_LONG", String.class);
    public static final String HINT_MESSAGE = configParser.get("HINT_MESSAGE", String.class);
    public static final int MAX_ATTEMPTS_DIFFICULTY_CATEGORY = configParser.get("MAX_ATTEMPTS_DIFFICULTY_CATEGORY",
                                                                                Integer.class);
    public static final String HIDDEN_LETTER = configParser.get("HIDDEN_LETTER", String.class);
}
