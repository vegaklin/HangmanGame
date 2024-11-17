package backend.academy.hangman;

import backend.academy.hangman.factory.HangmanGameFactory;
import backend.academy.hangman.game.Game;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        Game game = HangmanGameFactory.createGame();
        game.start(new Scanner(System.in), System.out);
    }
}
