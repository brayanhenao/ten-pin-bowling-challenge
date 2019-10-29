package com.challenge.exceptions;

/**
 * @author Brayan Andrés Henao
 */
public class InvalidFileException extends Exception {

    /**
     * Constructor of the InvalidFileException class.
     *
     * @param message - The message of the exception.
     */
    public InvalidFileException(String message) {
        super(message);
    }
}
