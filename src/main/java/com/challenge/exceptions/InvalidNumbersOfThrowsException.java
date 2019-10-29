package com.challenge.exceptions;

/**
 * @author Brayan Andrés Henao
 */
public class InvalidNumbersOfThrowsException extends RuntimeException {

    /**
     * Constructor of the InvalidNumbersOfThrowsException class.
     *
     * @param message - The message of the exception.
     */
    public InvalidNumbersOfThrowsException(String message) {
        super(message);
    }
}
