package com.challenge.service.impl;

import com.challenge.exceptions.InvalidFileException;
import com.challenge.exceptions.InvalidScoreException;
import com.challenge.model.Player;
import com.challenge.model.Score;
import com.challenge.service.GameService;
import com.challenge.service.ScoreService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brayan Andrés Henao
 */
public class GameServiceImpl implements GameService {

    // ATTRIBUTES

    /**
     * Score service declaration.
     */
    private ScoreService scoreService;

    /**
     * List of file lines.
     */
    private List<String> fileLines;


    /**
     * Constructor of the GameServiceImpl class.
     *
     * @param fileLines - List of file lines.
     */
    public GameServiceImpl(List<String> fileLines) {
        this.fileLines = fileLines;
    }

    // METHODS

    /**
     * Method that generates the scores for a game.
     *
     * @return Map<Player, Integer> - Key = The Player Value = Player final score
     */
    public Map<Player, Integer> generateScoresForGame() throws InvalidScoreException, InvalidFileException {
        scoreService = new ScoreServiceImpl();
        List<Player> players = scoreService.getPlayersScoresFromLines(fileLines);

        Map<Player, Integer> playersWithScores = new HashMap<>();
        players.forEach(player -> {
            Score score = player.getPlayerScore();
            playersWithScores.put(player, score.getFrames().getLast().getFrameScore());
        });

        return playersWithScores;
    }
}
