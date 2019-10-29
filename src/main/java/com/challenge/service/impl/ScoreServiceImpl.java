package com.challenge.service.impl;

import com.challenge.constants.ApplicationConstants;
import com.challenge.exceptions.InvalidFileException;
import com.challenge.exceptions.InvalidNumbersOfThrowsException;
import com.challenge.exceptions.InvalidScoreException;
import com.challenge.model.Frame;
import com.challenge.model.Player;
import com.challenge.model.Score;
import com.challenge.service.ScoreService;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

/**
 * @author Brayan Andrés Henao
 */
public class ScoreServiceImpl implements ScoreService {

    // METHODS

    /**
     * Method to obtain the players scores based on the file lines.
     *
     * @param fileLines - File lines containing the game score information.
     * @return List<Player> - List of players with calculated scores.
     */
    @Override
    public List<Player> getPlayersScoresFromLines(List<String> fileLines) throws InvalidScoreException, InvalidFileException, InvalidNumbersOfThrowsException {

        Map<String, List<Integer>> scores = readScoresFromLines(fileLines);

        checkValidNumberOfThrows(scores);

        // Player information and helper array to calculate the frame final value.
        Map<Player, Integer[]> players = fillPlayersInformation(scores);

        List<Integer> finalScores = calculateFinalScores(players);

        if (!checkValidScores(finalScores)) {
            throw new InvalidScoreException("Invalid Score");
        }

        return new ArrayList<>(players.keySet());
    }

    /**
     * Method that verifies and throws an exception if the number of throws made by the player are incorrect.
     *
     * @param scores - K : Player name - V : Player throws.
     */
    private void checkValidNumberOfThrows(Map<String, List<Integer>> scores) throws InvalidNumbersOfThrowsException {
        scores.forEach((playerName, playerScores) -> {
                    if (playerScores.size() < 11 || playerScores.size() > 21) {
                        throw new InvalidNumbersOfThrowsException("Invalid number of throws for a player - Check the lines");
                    } else {
                        if (!validThrowsInNormalFrames(playerScores)) {
                            throw new InvalidNumbersOfThrowsException("Invalid number of throws for a player - Check the lines");
                        }
                    }
                }
        );
    }

    /**
     * Method that checks if the number of throws of the player are valid.
     *
     * @param playerScores - File lines containing the game score information.
     * @return Boolean - If the number of throws of the player are valid or not.
     */
    private boolean validThrowsInNormalFrames(List<Integer> playerScores) {
        long nStrikes = playerScores.stream().filter(integer -> integer == 10).count();
        long nNoStrikes = playerScores.stream().filter(integer -> integer != 10).count();

        return (nStrikes * 2) + nNoStrikes != 18;
    }

    /**
     * Method that calculates the final scores and store the score of every frame.
     *
     * @param players - K : Player - V : Player throws raw (used to calculate the final score)
     * @return List<Integer> - List of final scores.
     */
    private List<Integer> calculateFinalScores(Map<Player, Integer[]> players) {
        List<Integer> finalScores = new ArrayList<>();
        players.forEach((player, rawThrows) -> {

            LinkedList<Frame> playerFrames = player.getPlayerScore().getFrames();
            int helperIndex = 0;
            int finalScore = 0;
            for (Frame playerFrame : playerFrames) {
                if (isStrike(rawThrows, helperIndex)) {
                    Integer nextFrameScore = rawThrows[helperIndex + 1];
                    Integer nextNextFrameScore = rawThrows[helperIndex + 2];
                    finalScore += 10 + nextFrameScore + nextNextFrameScore;
                    playerFrame.setFrameScore(finalScore);
                    helperIndex++;
                } else if (isSpare(rawThrows, helperIndex)) {
                    Integer nextNextFrameScore = rawThrows[helperIndex + 2];
                    finalScore += 10 + nextNextFrameScore;
                    playerFrame.setFrameScore(finalScore);
                    helperIndex += 2;
                } else {
                    Integer nextFrameScore = rawThrows[helperIndex + 1];
                    finalScore += rawThrows[helperIndex] + nextFrameScore;
                    playerFrame.setFrameScore(finalScore);
                    helperIndex += 2;
                }
            }
            finalScores.add(finalScore);
            player.getPlayerScore().setFrames(playerFrames);
        });

        return finalScores;
    }

    /**
     * Method that checks if the final scores of each player are valid.
     *
     * @param playersFinalScores - Collection with all the final scores in the game.
     * @return Boolean - If any of the scores in the collection are or not valid.
     */
    private boolean checkValidScores(Collection<Integer> playersFinalScores) {
        return playersFinalScores.stream().anyMatch(playerFinalScore ->
                (playerFinalScore >= 0 && playerFinalScore <= 300)
        );
    }

    /**
     * Method that checks if the line score contains a correct value.
     *
     * @param stringScore - Collection with all the final scores in the game.
     * @return Boolean - If the line score is valid.
     */
    private boolean checkValidLineScore(String stringScore) {
        if (stringScore.equalsIgnoreCase(ApplicationConstants.FAULT_SYMBOL)) {
            return true;
        } else {
            int score = Integer.parseInt(stringScore);
            return score >= 0 && score <= 10;
        }

    }

    /**
     * Method that reads all the file lines and obtain the Player name and the player throws.
     *
     * @param fileLines - File lines containing the game score information.
     * @return Map<String, List < Integer>> - K : Player name - V : Player throws.
     */
    private Map<String, List<Integer>> readScoresFromLines(List<String> fileLines) throws InvalidFileException, InvalidScoreException {
        LinkedHashMap<String, List<Integer>> scores = new LinkedHashMap<>();

        AtomicBoolean errorReadingFile = new AtomicBoolean(false);
        try {
            fileLines.forEach(fileLine -> {
                String[] split = fileLine.split("\t");
                String playerName = split[0].trim();
                boolean isValid = checkValidLineScore(split[1].trim());
                if (isValid) {
                    Integer playerScore = getScoreFromString(split[1].trim());
                    if (!scores.containsKey(playerName)) {
                        List<Integer> playerScores = new ArrayList<>();
                        scores.put(playerName, playerScores);
                    }
                    scores.get(playerName).add(playerScore);
                } else {
                    errorReadingFile.set(true);
                }
            });
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new InvalidFileException("Invalid file - Check the file lines.");
        }

        if (errorReadingFile.get()) {
            throw new InvalidScoreException("Invalid file line score - Check the file lines.");
        }

        return scores;
    }

    /**
     * Method that converts and string representation of the score into an Integer one.
     *
     * @param stringScore - Score in string format.
     * @return Integer - Value of the string score.
     */
    private Integer getScoreFromString(String stringScore) {
        if (stringScore.equalsIgnoreCase("F")) {
            return -2;
        } else {
            return Integer.parseInt(stringScore);
        }
    }

    /**
     * Method that fills all the player information and return it in Objects.
     *
     * @param scores - Map K : Player name - V : Player throws.
     * @return Map<Player, Integer [ ]> - K : Player - V : Player throws.
     */
    private Map<Player, Integer[]> fillPlayersInformation(Map<String, List<Integer>> scores) {

        LinkedHashMap<Player, Integer[]> players = new LinkedHashMap<>();
        scores.forEach((playerName, playerScores) -> {
            Score score = new Score();
            Integer[] playerThrows = score.getPlayerThrows();
            Integer[] playerThrowsRaw = IntStream.range(0, 21).mapToObj(i -> 0).toArray(Integer[]::new);

            int auxPointer = 0;
            for (int i = 0; i < playerThrows.length; ) {
                for (Integer playerScore : playerScores) {
                    if (i < 18) {
                        if (playerScore.equals(ApplicationConstants.STRIKE)) {
                            playerThrows[i] = 0;
                            playerThrows[i + 1] = ApplicationConstants.STRIKE;
                            playerThrowsRaw[auxPointer] = ApplicationConstants.STRIKE;
                            auxPointer++;
                            i += 2;
                        } else {
                            playerThrows[i] = playerScore;
                            playerThrowsRaw[auxPointer] = playerThrows[i].equals(ApplicationConstants.FAULT) ?
                                    ApplicationConstants.NO_POINTS : playerThrows[i];
                            auxPointer++;
                            i += 1;
                        }
                    } else {
                        playerThrows[i] = playerScore;
                        playerThrowsRaw[auxPointer] = playerThrows[i].equals(ApplicationConstants.FAULT) ?
                                ApplicationConstants.NO_POINTS : playerThrows[i];
                        auxPointer++;
                        i += 1;
                    }
                }
            }

            LinkedList<Frame> playerFrames = (LinkedList<Frame>) getScoreInEveryFrame(playerThrows);
            score.setFrames(playerFrames);

            Player currentPlayer = new Player(playerName, score);
            players.put(currentPlayer, playerThrowsRaw);
        });
        return players;
    }

    /**
     * Method that modify the frame throws according to all the player throws.
     *
     * @param playerThrows - Array of all player throws.
     * @return List<Frame> - List of players with calculated scores.
     */
    private List<Frame> getScoreInEveryFrame(Integer[] playerThrows) {

        LinkedList<Frame> frames = new LinkedList<>();

        for (int i = 0; i < playerThrows.length; i += 2) {
            Integer[] throwsPerFrame = new Integer[3];
            throwsPerFrame[0] = playerThrows[i];
            throwsPerFrame[1] = playerThrows[i + 1];

            if (i < playerThrows.length - 3) {
                frames.add(new Frame(throwsPerFrame));
            } else {
                throwsPerFrame[2] = playerThrows[i + 2];
                frames.add(new Frame(throwsPerFrame));
                i++;
            }
        }
        return frames;
    }

    /**
     * Method that checks if the throws of a frame accumulate a strike.
     *
     * @param frameNumber - Frame number that is going to be checked.
     * @return Boolean - If the frame throws are or not a strike.
     */
    private boolean isStrike(Integer[] playerFrames, Integer frameNumber) {
        return playerFrames[frameNumber].equals(ApplicationConstants.STRIKE);
    }


    /**
     * Method that checks if the throws of a frame accumulate a spare.
     *
     * @param frameNumber - Frame number that is going to be checked.
     * @return Boolean - If the frame throws are or not a spare.
     */
    private boolean isSpare(Integer[] playerFrames, Integer frameNumber) {
        Integer throw1 = playerFrames[frameNumber];
        Integer throw2 = playerFrames[frameNumber + 1];

        return throw1 + throw2 == ApplicationConstants.SPARE;
    }
}