package com.challenge.view;

import com.challenge.exceptions.InvalidFileException;
import com.challenge.exceptions.InvalidScoreException;
import com.challenge.model.Player;
import com.challenge.service.BoardService;
import com.challenge.service.FileService;
import com.challenge.service.GameService;
import com.challenge.service.impl.BoardServiceImpl;
import com.challenge.service.impl.FileServiceImpl;
import com.challenge.service.impl.GameServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Brayan Andrés Henao
 */
public class BowlingGame {

    // CONSTANTS

    /**
     * Logger library used to print any information to the user.
     */
    private static final Logger log = LoggerFactory.getLogger(BowlingGame.class);

    // ATTRIBUTES

    /**
     * Game service declaration.
     */
    private GameService gameService;

    /**
     * Board service declaration.
     */
    private BoardService boardService;

    /**
     * File service declaration.
     */
    private FileService fileService;


    /**
     * Constructor of the BowlingGame class.
     */
    public BowlingGame() {
        fileService = new FileServiceImpl();
        boardService = new BoardServiceImpl();
    }

    /**
     * Constructor of the BowlingGame class. USED FOR TESTING
     *
     * @param fileService - file service instance.
     */
    public BowlingGame(FileService fileService) {
        this.fileService = fileService;
        boardService = new BoardServiceImpl();
    }

    // METHODS

    /**
     * Method that starts the game reading the file and calculating the scores.
     * Also prints the final score board for all players.
     *
     * @param fileName - List of file lines.
     * @return List<Integer> - The final scores for all players.
     */
    public List<Integer> play(String fileName) throws IOException, InvalidFileException, InvalidScoreException {
        List<String> lines = fileService.processFile(fileName);
        gameService = new GameServiceImpl(lines);

        Map<Player, Integer> players = gameService.generateScoresForGame();
        log.info(boardService.generateBoard(new ArrayList<>(players.keySet())));
        return new ArrayList<>(players.values());
    }
}
