package com.challenge;

import com.challenge.exceptions.InvalidFileException;
import com.challenge.exceptions.InvalidNumbersOfThrowsException;
import com.challenge.exceptions.InvalidScoreException;
import com.challenge.view.BowlingGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Brayan Andrés Henao
 */
public class BowlingApp {

    // CONSTANTS

    /**
     * Logger library used to print any information to the user.
     */
    private static final Logger log = LoggerFactory.getLogger(BowlingApp.class);

    // METHODS

    /**
     * The main method of the application. It reads the file name from the provided arguments and starts the game.
     * Also, it catches the custom exceptions and prints messages to the user.
     */
    public static void main(String[] args) throws Exception {
        log.info("--------------------------------------------------------------------------");
        log.info("--------------------------------------------------------------------------");
        log.info("---------------------------Bowling game started---------------------------");
        log.info("--------------------------------------------------------------------------");
        String fileName;
        if (args.length == 1) {
            fileName = args[0];

        } else {
            log.info("Input file not provided, using sample test case");
            fileName = "input.txt";
        }
        BowlingGame bowlingGame = new BowlingGame();

        try {
            bowlingGame.play(fileName);
        } catch (InvalidFileException | InvalidScoreException | InvalidNumbersOfThrowsException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error("Problems reading the file");
        }


        log.info("---------------------------------------------------------------------------");
        log.info("---------------------------------------------------------------------------");
        log.info("---------------------------Bowling game finished---------------------------");
        log.info("---------------------------------------------------------------------------");
        log.info("---------------------------------------------------------------------------");
    }
}
