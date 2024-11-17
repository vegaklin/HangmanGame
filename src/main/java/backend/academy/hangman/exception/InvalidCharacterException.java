package backend.academy.hangman.exception;

public class InvalidCharacterException extends Exception {
    public InvalidCharacterException() {
        super("Некорректный ввод! Пожалуйста, введите русскую букву.");
    }
}
