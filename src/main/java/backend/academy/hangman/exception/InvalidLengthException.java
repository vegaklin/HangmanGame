package backend.academy.hangman.exception;

public class InvalidLengthException extends Exception {
    public InvalidLengthException() {
        super("Некорректный ввод! Пожалуйста, введите только одну букву.");
    }
}
