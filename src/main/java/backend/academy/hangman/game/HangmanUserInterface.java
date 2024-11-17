package backend.academy.hangman.game;

import java.io.PrintStream;
import java.util.Scanner;
import lombok.experimental.UtilityClass;
import static backend.academy.hangman.game.HangmanGameConstants.HINT_MESSAGE;
import static backend.academy.hangman.game.HangmanGameConstants.LINE_LONG;

@UtilityClass
public class HangmanUserInterface {
    public static void printWelcomeMessage(Scanner scanner, PrintStream out) throws IllegalArgumentException {
        out.println(LINE_LONG);
        out.println("         Добро пожаловать в игру Виселица!");
        out.println(LINE_LONG);
        out.println("В этой игре вам предстоит угадать слово,");
        out.println("буква за буквой, прежде чем количество попыток");
        out.println("закончится. Вы можете выбрать уровень сложности");
        out.println("и категорию слов.");
        out.println();
        out.println("Удачи и веселой игры!");
        printMenu(out);
        handleIntroductionInput(scanner, out);
    }

    private void printMenu(PrintStream out) {
        out.println(LINE_LONG);
        out.println("[1] - Прочитать правила");
        out.println("[2] - Выйти из игры");
        out.println("ENTER - Начать игру");
    }

    private void handleIntroductionInput(Scanner scanner, PrintStream out) {
        while (true) {
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> {
                    printRules(out);
                    printMenu(out);
                }
                case "2" -> {
                    out.println("Вы вышли из игры. Спасибо за игру!");
                    System.exit(0);
                }
                case "" -> {
                    return;
                }
                default -> out.println("Введите [1], [2] или нажмите ENTER для начала игры.");
            }
        }
    }

    private void printRules(PrintStream out) {
        out.println(LINE_LONG);
        out.println("Правила игры Виселица:");
        out.println();
        out.println("1. В игре вам будет предложено слово, которое нужно угадать.");
        out.println("2. Вы можете выбрать уровень сложности (EASY, MEDIUM, HARD) и заданную категорию слов.");
        out.println("3. Вы будете угадывать буквы одну за другой.");
        out.println("4. Если угаданная буква присутствует в слове, она будет открыта.");
        out.println("5. Если угаданной буквы нет в слове, количество оставшихся попыток уменьшится.");
        out.println("6. Игра заканчивается, когда вы угадаете все буквы слова или исчерпаете все попытки.");
        out.println("7. Если вы исчерпаете все попытки до того, как угадаете слово, вы проиграете.");
        out.println("8. Если угадаете слово до истечения попыток, вы выиграете.");
        out.println("9. " + HINT_MESSAGE);
        out.println();
    }
}
