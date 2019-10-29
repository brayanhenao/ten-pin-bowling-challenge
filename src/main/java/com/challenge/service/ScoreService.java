package com.challenge.service;

import com.challenge.exceptions.InvalidFileException;
import com.challenge.exceptions.InvalidScoreException;
import com.challenge.model.Player;

import java.util.List;

/**
 * @author Brayan Andrés Henao
 */
public interface ScoreService {

    /**
     * Method to obtain the players scores based on the file lines.
     *
     * @param fileLines - File lines containing the game score information.
     * @return List<Player> - List of players with calculated scores.
     */
    List<Player> getPlayersScoresFromLines(List<String> fileLines) throws InvalidScoreException, InvalidFileException;
}