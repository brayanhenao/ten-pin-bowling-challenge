package com.challenge.service;


import com.challenge.model.Player;

import java.util.List;

/**
 * @author Brayan Andrés Henao
 */
public interface BoardService {

    /**
     * Method to get a list of Players and generates the board to be printed.
     *
     * @return String - the general board of the game for all players.
     */
    String generateBoard(List<Player> players);
}