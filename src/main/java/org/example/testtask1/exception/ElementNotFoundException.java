package org.example.testtask1.exception;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String element) {
        super( element + "не найден");
    }
}
