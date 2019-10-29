package com.challenge.model;

import lombok.Data;

/**
 * @author Brayan Andrés Henao
 */
public @Data
class Frame {

    // ATTRIBUTES

    /**
     * Score of the frame.
     */
    private Integer frameScore;

    /**
     * Throws score in the frame.
     * There is 2 throws in the frames 1 to 9, and 3 throws in the 10th frame.
     */
    private Integer[] frameThrows;


    /**
     * Constructor of the Frame class.
     *
     * @param frameThrows - the frame throws.
     */
    public Frame(Integer[] frameThrows) {
        this.frameThrows = frameThrows;
    }
}
