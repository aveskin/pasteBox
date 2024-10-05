package org.example.testtask1.exception;

public class ListNotFoundException extends RuntimeException {
    public ListNotFoundException() {
        super("Список паст пустой");
    }
}
