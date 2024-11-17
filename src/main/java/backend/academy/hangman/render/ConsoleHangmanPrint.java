package backend.academy.hangman.render;

import backend.academy.hangman.state.HangmanGameState;
import java.io.PrintStream;
import static backend.academy.hangman.settings.MaxAttempts.MAX_ATTEMPTS_EASY;
import static backend.academy.hangman.settings.MaxAttempts.MAX_ATTEMPTS_HARD;
import static backend.academy.hangman.settings.MaxAttempts.MAX_ATTEMPTS_MEDIUM;

public class ConsoleHangmanPrint  {
    private static final String HANGMAN_HEAD = "|    O\n";
    private static final String HANGMAN_BODY = "|    |\n";
    private static final String HANGMAN_ARM_LEFT = "|   /|\n";
    private static final String HANGMAN_ARM_BOTH = "|   /|\\\n";
    private static final String HANGMAN_LEG_LEFT = "|   /\n";
    private static final String LINE = "|\n";
    private static final String LINE_UP = HANGMAN_BODY;
    private static final String LINE_UP_FULL = "|/   |\n";
    private static final String HORIZONTAL_LINE = "======";
    private static final String DOWN = LINE
        + HORIZONTAL_LINE
        + HORIZONTAL_LINE;

    private static final String[] HANGMAN_STAGES_8 = {
        LINE + LINE + LINE + LINE,
        LINE_UP + LINE + LINE + LINE,
        LINE_UP_FULL + LINE + LINE + LINE,
        LINE_UP_FULL + HANGMAN_HEAD + LINE + LINE,
        LINE_UP_FULL + HANGMAN_HEAD + HANGMAN_BODY + LINE,
        LINE_UP_FULL + HANGMAN_HEAD + HANGMAN_ARM_LEFT + LINE,
        LINE_UP_FULL + HANGMAN_HEAD + HANGMAN_ARM_BOTH + LINE,
        LINE_UP_FULL + HANGMAN_HEAD + HANGMAN_ARM_BOTH + HANGMAN_LEG_LEFT,
    };

    private static final String[] HANGMAN_STAGES_6 = {
        LINE_UP_FULL + LINE + LINE + LINE,
        LINE_UP_FULL + HANGMAN_HEAD + LINE + LINE,
        LINE_UP_FULL + HANGMAN_HEAD + HANGMAN_BODY + LINE,
        LINE_UP_FULL + HANGMAN_HEAD + HANGMAN_ARM_LEFT + LINE,
        LINE_UP_FULL + HANGMAN_HEAD + HANGMAN_ARM_BOTH + LINE,
        LINE_UP_FULL + HANGMAN_HEAD + HANGMAN_ARM_BOTH + HANGMAN_LEG_LEFT,
    };

    private static final String[] HANGMAN_STAGES_4 = {
        LINE_UP_FULL + LINE + LINE + LINE,
        LINE_UP_FULL + HANGMAN_HEAD + HANGMAN_BODY + LINE,
        LINE_UP_FULL + HANGMAN_HEAD + HANGMAN_ARM_BOTH + LINE,
        LINE_UP_FULL + HANGMAN_HEAD + HANGMAN_ARM_BOTH + HANGMAN_LEG_LEFT,
    };

    public void render(HangmanGameState gameState, PrintStream out) {
        String[] stages = chooseStages(gameState.maxAttempts());
        int currentStage = gameState.maxAttempts() - gameState.attempts();
        out.println(HORIZONTAL_LINE);
        out.print(stages[currentStage]);
        out.println(DOWN);
        out.println("Текущее слово: " + gameState.currentWord().toString());
        out.println("Осталось ошибочных попыток: " + gameState.attempts());
        out.println("Недоступные бкувы: " + gameState.getWrongGuesses());
        out.println("Доступные буквы: " + gameState.getAvailableLetters());
    }

    public void renderFullBody(PrintStream out) {
        String hangmanLegBoth = "|   / \\\n";
        String newLine = "\n";
        String hangmanFinal = HORIZONTAL_LINE
            + newLine
            + LINE_UP_FULL
            + HANGMAN_HEAD
            + HANGMAN_ARM_BOTH
            + hangmanLegBoth
            + DOWN;
        out.println(hangmanFinal);
    }

    public String[] chooseStages(int maxAttempts) {
        return switch (maxAttempts) {
            case MAX_ATTEMPTS_EASY -> HANGMAN_STAGES_8;
            case MAX_ATTEMPTS_MEDIUM -> HANGMAN_STAGES_6;
            case MAX_ATTEMPTS_HARD -> HANGMAN_STAGES_4;
            default -> throw new IllegalArgumentException("Unknown maxAttempts");
        };
    }
}
