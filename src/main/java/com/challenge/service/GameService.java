package com.challenge.service;

import com.challenge.exceptions.InvalidFileException;
import com.challenge.exceptions.InvalidScoreException;
import com.challenge.model.Player;

import java.util.Map;

/**
 * @author Brayan Andrés Henao
 */
public interface GameService {

    /**
     * Method that generates the scores for a game.
     *
     * @return Map<Player, Integer> - Key = The Player Value = Player final score
     */
    public Map<Player, Integer> generateScoresForGame() throws InvalidScoreException, InvalidFileException;
}
