package com.challenge.model;

import lombok.Data;

/**
 * @author Brayan Andrés Henao
 */
public @Data
class Player {

    // ATTRIBUTES

    /**
     * Player name.
     */
    private String playerName;

    /**
     * Player score.
     */
    private Score playerScore;

    /**
     * Constructor of the Player class.
     *
     * @param playerName  - the name of the player to create.
     * @param playerScore - the score of the player to create.
     */
    public Player(String playerName, Score playerScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
    }
}
