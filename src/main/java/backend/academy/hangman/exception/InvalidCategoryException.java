package backend.academy.hangman.exception;

public class InvalidCategoryException extends Exception {
    public InvalidCategoryException() {
        super("Некорректная категория!");
    }
}
