package com.challenge.exceptions;

/**
 * @author Brayan Andrés Henao
 */
public class InvalidScoreException extends Exception {

    /**
     * Constructor of the InvalidScoreException class.
     *
     * @param message - The message of the exception.
     */
    public InvalidScoreException(String message) {
        super(message);
    }
}
