package backend.academy.hangman;

import backend.academy.hangman.render.ConsoleHangmanPrint;
import backend.academy.hangman.state.HangmanGameState;
import backend.academy.hangman.word.Difficulty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static backend.academy.hangman.settings.MaxAttempts.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConsoleHangmanPrintTest {
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream printStream = new PrintStream(outputStream);

    private final ConsoleHangmanPrint consoleHangmanPrint = new ConsoleHangmanPrint();

    @Test
    @DisplayName("Вывод функции chooseStages для EASY")
    void testChooseStagesEasyDifficulty() {
        String[] expectedStages = {
            "|\n|\n|\n|\n",
            "|    |\n|\n|\n|\n",
            "|/   |\n|\n|\n|\n",
            "|/   |\n|    O\n|\n|\n",
            "|/   |\n|    O\n|    |\n|\n",
            "|/   |\n|    O\n|   /|\n|\n",
            "|/   |\n|    O\n|   /|\\\n|\n",
            "|/   |\n|    O\n|   /|\\\n|   /\n",
        };

        String[] resultStages = consoleHangmanPrint.chooseStages(MAX_ATTEMPTS_EASY);

        assertArrayEquals(expectedStages, resultStages);
    }

    @Test
    @DisplayName("Вывод функции chooseStages для MEDIUM")
    void testChooseStagesMediumDifficulty() {
        String[] expectedStages = {
            "|/   |\n|\n|\n|\n",
            "|/   |\n|    O\n|\n|\n",
            "|/   |\n|    O\n|    |\n|\n",
            "|/   |\n|    O\n|   /|\n|\n",
            "|/   |\n|    O\n|   /|\\\n|\n",
            "|/   |\n|    O\n|   /|\\\n|   /\n",
        };

        String[] resultStages = consoleHangmanPrint.chooseStages(MAX_ATTEMPTS_MEDIUM);

        assertArrayEquals(expectedStages, resultStages);
    }

    @Test
    @DisplayName("Вывод функции chooseStages для HARD")
    void testChooseStagesHardDifficulty() {
        String[] expectedStages = {
            "|/   |\n|\n|\n|\n",
            "|/   |\n|    O\n|    |\n|\n",
            "|/   |\n|    O\n|   /|\\\n|\n",
            "|/   |\n|    O\n|   /|\\\n|   /\n",
        };

        String[] resultStages = consoleHangmanPrint.chooseStages(MAX_ATTEMPTS_HARD);

        assertArrayEquals(expectedStages, resultStages);
    }

    @Test
    @DisplayName("Недопустимое количество попыток")
    void testChooseStagesInvalidDifficulty() {
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> consoleHangmanPrint.chooseStages(10),
            "Expected chooseStages() to throw, but it didn't"
        );

        assertEquals("Unknown maxAttempts", thrown.getMessage());
    }

    @Test
    @DisplayName("Вывод функции renderFullBody")
    void testRenderFullBody() {
        consoleHangmanPrint.renderFullBody(printStream);
        String expectedOutput = """
            ======
            |/   |
            |    O
            |   /|\\
            |   / \\
            |
            ======\
            ======
            """;

        String actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    @DisplayName("Корректность отображения состояния игры после каждого ввода пользователя для EASY")
    void testRenderGameStateAllWordEasy() {
        HangmanGameState gameState = new HangmanGameState("рим", Difficulty.EASY);
        String expectedOutput = """
            ======
            |    |
            |
            |
            |
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 7
            Недоступные бкувы: б
            Доступные буквы: а, в, г, д, е, ж, з, и, й, к, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('б');
        consoleHangmanPrint.render(gameState, printStream);
        String actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);


        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |
            |
            |
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 6
            Недоступные бкувы: б, к
            Доступные буквы: а, в, г, д, е, ж, з, и, й, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('к');
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);


        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |    O
            |
            |
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 5
            Недоступные бкувы: б, к, а
            Доступные буквы: в, г, д, е, ж, з, и, й, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('а');
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);


        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |    O
            |    |
            |
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 4
            Недоступные бкувы: б, к, а, в
            Доступные буквы: г, д, е, ж, з, и, й, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('в');
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);


        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |    O
            |   /|
            |
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 3
            Недоступные бкувы: б, к, а, в, г
            Доступные буквы: д, е, ж, з, и, й, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('г');
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);


        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |    O
            |   /|\\
            |
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 2
            Недоступные бкувы: б, к, а, в, г, д
            Доступные буквы: е, ж, з, и, й, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('д');
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);


        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |    O
            |   /|\\
            |   /
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 1
            Недоступные бкувы: б, к, а, в, г, д, я
            Доступные буквы: е, ж, з, и, й, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю
            """;

        gameState.guess('я');
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);


        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |    O
            |   /|\\
            |   /
            |
            ============
            Текущее слово: р__
            Осталось ошибочных попыток: 1
            Недоступные бкувы: б, к, а, в, г, д, я, р
            Доступные буквы: е, ж, з, и, й, л, м, н, о, п, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю
            """;

        gameState.guess('р'); // Верная буква
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    @DisplayName("Корректность отображения состояния игры после каждого ввода пользователя для MEDIUM")
    void testRenderGameStateAllWordMedium() {
        HangmanGameState gameState = new HangmanGameState("рим", Difficulty.MEDIUM);
        String expectedOutput = """
            ======
            |/   |
            |    O
            |
            |
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 5
            Недоступные бкувы: б
            Доступные буквы: а, в, г, д, е, ж, з, и, й, к, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('б');
        consoleHangmanPrint.render(gameState, printStream);
        String actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);


        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |    O
            |    |
            |
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 4
            Недоступные бкувы: б, к
            Доступные буквы: а, в, г, д, е, ж, з, и, й, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('к');
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);


        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |    O
            |   /|
            |
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 3
            Недоступные бкувы: б, к, а
            Доступные буквы: в, г, д, е, ж, з, и, й, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('а');
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);


        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |    O
            |   /|\\
            |
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 2
            Недоступные бкувы: б, к, а, в
            Доступные буквы: г, д, е, ж, з, и, й, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('в');
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);


        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |    O
            |   /|\\
            |   /
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 1
            Недоступные бкувы: б, к, а, в, г
            Доступные буквы: д, е, ж, з, и, й, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('г');
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);


        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |    O
            |   /|\\
            |   /
            |
            ============
            Текущее слово: р__
            Осталось ошибочных попыток: 1
            Недоступные бкувы: б, к, а, в, г, р
            Доступные буквы: д, е, ж, з, и, й, л, м, н, о, п, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('р');
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    @DisplayName("Корректность отображения состояния игры после каждого ввода пользователя для HARD")
    void testRenderGameStateAllWordHard() {
        HangmanGameState gameState = new HangmanGameState("рим", Difficulty.HARD);
        String expectedOutput = """
            ======
            |/   |
            |    O
            |    |
            |
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 3
            Недоступные бкувы: б
            Доступные буквы: а, в, г, д, е, ж, з, и, й, к, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('б');
        consoleHangmanPrint.render(gameState, printStream);
        String actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);


        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |    O
            |   /|\\
            |
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 2
            Недоступные бкувы: б, а
            Доступные буквы: в, г, д, е, ж, з, и, й, к, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('а');
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");

        actualOutput = actualOutput.replace("\r\n", "\n");
        assertEquals(expectedOutput, actualOutput);


        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |    O
            |   /|\\
            |   /
            |
            ============
            Текущее слово: ___
            Осталось ошибочных попыток: 1
            Недоступные бкувы: б, а, в
            Доступные буквы: г, д, е, ж, з, и, й, к, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('в');
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);

        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        expectedOutput = """
            ======
            |/   |
            |    O
            |   /|\\
            |   /
            |
            ============
            Текущее слово: р__
            Осталось ошибочных попыток: 1
            Недоступные бкувы: б, а, в, р
            Доступные буквы: г, д, е, ж, з, и, й, к, л, м, н, о, п, с, т, у, ф, х, ц, ч, ш, щ, ъ, ы, ь, э, ю, я
            """;

        gameState.guess('р');
        consoleHangmanPrint.render(gameState, printStream);
        actualOutput = outputStream.toString();
        expectedOutput = expectedOutput.replace("\r\n", "\n");
        actualOutput = actualOutput.replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);
    }
}



