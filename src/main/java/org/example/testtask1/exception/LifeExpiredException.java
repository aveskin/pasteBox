package org.example.testtask1.exception;

public class LifeExpiredException extends RuntimeException {
    public LifeExpiredException(String element) {
        super( element + " :время жизни объекта истекло");
    }
}
