package com.challenge.service.impl;

import com.challenge.constants.ApplicationConstants;
import com.challenge.model.Frame;
import com.challenge.model.Player;
import com.challenge.service.BoardService;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Brayan Andrés Henao
 */
public class BoardServiceImpl implements BoardService {

    // METHODS

    /**
     * Method to get a list of Players and generates the board to be printed.
     *
     * @return String - the general board of the game for all players.
     */
    @Override
    public String generateBoard(List<Player> players) {

        StringBuilder stringBuilder = new StringBuilder("-------- GAME FINAL SCORES ---------\n");
        players.forEach(player -> {
            LinkedList<Frame> frames = player.getPlayerScore().getFrames();

            stringBuilder.append(ApplicationConstants.BOARD_FRAME_STRING);
            stringBuilder.append(player.getPlayerName()).append('\n');
            stringBuilder.append(getPinFallsLine(frames)).append('\n');
            stringBuilder.append(getScoreLine(frames)).append("\n");
        });

        return stringBuilder.toString().trim();
    }

    /**
     * Method that generate the pin falls line of a player.
     *
     * @return String - the generated pin falls line.
     */
    private String getPinFallsLine(LinkedList<Frame> frames) {
        StringBuilder stringBuilder = new StringBuilder("Pinfalls ");

        frames.forEach(frame -> {
            Integer[] frameThrows = frame.getFrameThrows();
            if (frames.getLast().equals(frame)) {
                Integer throw1 = frameThrows[0];
                Integer throw2 = frameThrows[1];
                Integer throw3 = frameThrows[2];
                stringBuilder.append(stringFromLastFrame(throw1, throw2, throw3));
            } else {
                Integer throw1 = frameThrows[0];
                Integer throw2 = frameThrows[1];
                stringBuilder.append(stringFromNormalFrame(throw1, throw2));
            }
        });

        return stringBuilder.append("\n").toString().trim();
    }

    /**
     * Method that generate the score line of a player.
     *
     * @return String - the generated score line.
     */
    private String getScoreLine(LinkedList<Frame> frames) {
        StringBuilder stringBuilder = new StringBuilder("Score");
        frames.forEach(frame -> stringBuilder.append("  ").append(frame.getFrameScore()));
        return stringBuilder.toString().trim();
    }

    /**
     * Method that checks if a throw is fault.
     *
     * @return Boolean - If is or not a fault throw.
     */
    private boolean isFaultThrow(Integer throw1) {
        return throw1.equals(ApplicationConstants.FAULT);
    }

    /**
     * Method that checks if a spare is fault.
     *
     * @return Boolean - If is or not a spare throw.
     */
    private boolean isSpareThrow(Integer throw1, Integer throw2) {
        throw1 = throw1.equals(ApplicationConstants.FAULT) ? ApplicationConstants.NO_POINTS : throw1;
        return throw1 + throw2 == ApplicationConstants.SPARE;
    }

    /**
     * Method that checks if a strike is fault.
     *
     * @return Boolean - If is or not a strike throw.
     */
    private boolean isStrikeThrow(Integer throw1) {
        return throw1.equals(ApplicationConstants.STRIKE);
    }

    /**
     * Method that generates the string value of a frame in the 1 to 9th frame.
     *
     * @return String - the generated score (formatted) of a frame.
     */
    private String stringFromNormalFrame(Integer throw1, Integer throw2) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isStrikeThrow(throw2)) {
            stringBuilder.append(" ").append(ApplicationConstants.STRIKE_SYMBOL).append(" ");
        } else if (isSpareThrow(throw1, throw2)) {
            stringBuilder.append(throw1).append(" ").append(ApplicationConstants.SPARE_SYMBOL).append(" ");
        } else {
            if (isFaultThrow(throw1) && isFaultThrow(throw2)) {
                stringBuilder.append(ApplicationConstants.FAULT_SYMBOL).append(" ")
                        .append(ApplicationConstants.FAULT_SYMBOL).append(" ");
            } else if (isFaultThrow(throw1)) {
                stringBuilder.append(ApplicationConstants.FAULT_SYMBOL).append(" ").append(throw2).append(" ");
            } else if (isFaultThrow(throw2)) {
                stringBuilder.append(throw1).append(" ").append(ApplicationConstants.FAULT_SYMBOL).append(" ");
            } else {
                stringBuilder.append(throw1).append(" ").append(throw2).append(" ");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Method that generates the string value of a frame in the 10th frame.
     *
     * @return String - the generated score (formatted) of a frame.
     */
    private String stringFromLastFrame(Integer throw1, Integer throw2, Integer throw3) {
        StringBuilder stringBuilder = new StringBuilder();

        if (isStrikeThrow(throw1)) {
            stringBuilder.append(" ").append(ApplicationConstants.STRIKE_SYMBOL).append(" ")
                    .append(stringFromNormalFrame(throw2, throw3));
        } else if (isSpareThrow(throw1, throw2)) {
            stringBuilder.append(stringFromNormalFrame(throw1, throw2)).append(" ").append(throw3);
        } else {
            stringBuilder.append(stringFromNormalFrame(throw1, throw2));
        }

        return stringBuilder.toString();
    }
}
