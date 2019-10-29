package com.challenge.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.stream.IntStream;

/**
 * @author Brayan Andrés Henao
 */
public @Data
class Score {

    // ATTRIBUTES

    /**
     * All throws made by the player.
     */
    private Integer[] playerThrows;

    /**
     * Frames in the game.
     */
    private LinkedList<Frame> frames;

    /**
     * Constructor of the Score class.
     */
    public Score() {
        // There is a total of 21 throws in a game :
        // 10 frames, 9 with 2 throws and the las one with 3 shots.
        // 9 x 2 = 18 + 3 = 21;
        playerThrows = IntStream.range(0, 21).mapToObj(value -> 0).toArray(Integer[]::new);
    }
}
