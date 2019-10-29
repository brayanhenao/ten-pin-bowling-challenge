package com.challenge.unit;

import com.challenge.exceptions.InvalidFileException;
import com.challenge.exceptions.InvalidScoreException;
import com.challenge.model.Player;
import com.challenge.service.FileService;
import com.challenge.service.ScoreService;
import com.challenge.service.impl.FileServiceImpl;
import com.challenge.service.impl.ScoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Brayan Andrés Henao
 */
class ScoreServiceImplTests {

    private static final String INVALID_LINE_CONTENT_FILE = "src/test/resources/invalid_line_content_file.txt";
    private static final String INVALID_SCORE_FILE = "src/test/resources/invalid_score_file.txt";
    private static final String CORRECT_FILE_NAME = "src/test/resources/sample_score_file.txt";

    ScoreService scoreService;
    FileService fileService;

    @BeforeEach
    void init() {
        scoreService = new ScoreServiceImpl();
        fileService = new FileServiceImpl();
    }

    @Test
    void getPlayersScoresFromLinesTest() throws IOException, InvalidFileException, InvalidScoreException {
        List<Player> players = scoreService.getPlayersScoresFromLines(fileService.processFile(CORRECT_FILE_NAME));
        assertNotNull(players);
        assertEquals(167, getPlayerScore(players.get(0)));
        assertEquals(151, getPlayerScore(players.get(1)));
    }

    @Test
    void invalidScoreFromLinesTest() {
        assertThrows(InvalidScoreException.class, () ->
                scoreService.getPlayersScoresFromLines(fileService.processFile(INVALID_SCORE_FILE)));
    }

    @Test
    void invalidLineContentFromLinesTest() {
        assertThrows(InvalidFileException.class, () ->
                scoreService.getPlayersScoresFromLines(fileService.processFile(INVALID_LINE_CONTENT_FILE)));
    }

    private Integer getPlayerScore(Player player) {
        return player.getPlayerScore().getFrames().getLast().getFrameScore();
    }
}
