package com.challenge.integration;

import com.challenge.service.FileService;
import com.challenge.view.BowlingGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Brayan Andrés Henao
 */
class IntegrationTest {


    private final static String PATH_PERFECT_SCORE = "/test-perfect-score.txt";
    private final static String PATH_ZERO_SCORE = "/test-zero-score.txt";
    private final static String PATH_SAMPLE_SCORE = "/test-sample-score.txt";
    private final static String PATH_ONLY_FAULT_SCORE = "/test-sample-score.txt";
    private final static String PATH_ONLY_ONES_SCORE = "/test-sample-score.txt";

    @Mock
    private FileService fileService;

    private BowlingGame bowlingGame;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        bowlingGame = new BowlingGame(fileService);
    }

    @Test
    void testPerfectScore() throws Exception {
        List<String> fileLines = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            fileLines.add("Joe\t10");
        }
        when(fileService.processFile(PATH_PERFECT_SCORE)).thenReturn(fileLines);
        List<Integer> scores = bowlingGame.play(PATH_PERFECT_SCORE);
        assertEquals(300, scores.get(0));
    }

    @Test
    void testZeroScore() throws Exception {
        List<String> fileLines = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            fileLines.add("Peter\t0");
        }
        when(fileService.processFile(PATH_ZERO_SCORE)).thenReturn(fileLines);
        List<Integer> scores = bowlingGame.play(PATH_ZERO_SCORE);
        assertEquals(0, scores.get(0));
    }

    @Test
    void testSampleScore() throws Exception {
        List<String> fileLines = Stream.concat(jeffSampleScore().stream(), johnSampleScore()
                .stream()).collect(Collectors.toList());

        when(fileService.processFile(PATH_SAMPLE_SCORE)).thenReturn(fileLines);
        List<Integer> scores = bowlingGame.play(PATH_SAMPLE_SCORE);
        assertEquals(151, scores.get(0));
        assertEquals(167, scores.get(1));
    }

    @Test
    void testOnlyFaultScore() throws Exception {
        List<String> fileLines = generateOnlyFaultScore();
        when(fileService.processFile(PATH_ONLY_FAULT_SCORE)).thenReturn(fileLines);
        List<Integer> scores = bowlingGame.play(PATH_ONLY_FAULT_SCORE);
        assertEquals(0, scores.get(0));
    }

    @Test
    void testOnlyOnesScore() throws Exception {
        List<String> fileLines = generateOnlyOneScores();
        when(fileService.processFile(PATH_ONLY_ONES_SCORE)).thenReturn(fileLines);
        List<Integer> scores = bowlingGame.play(PATH_ONLY_ONES_SCORE);
        assertEquals(20, scores.get(0));
    }

    private List<String> jeffSampleScore() {
        List<String> sampleScore = new ArrayList<>();
        sampleScore.add("Jeff\t10");
        sampleScore.add("Jeff\t7");
        sampleScore.add("Jeff\t3");
        sampleScore.add("Jeff\t9");
        sampleScore.add("Jeff\t0");
        sampleScore.add("Jeff\t10");
        sampleScore.add("Jeff\t0");
        sampleScore.add("Jeff\t8");
        sampleScore.add("Jeff\t8");
        sampleScore.add("Jeff\t2");
        sampleScore.add("Jeff\t0");
        sampleScore.add("Jeff\t6");
        sampleScore.add("Jeff\t10");
        sampleScore.add("Jeff\t10");
        sampleScore.add("Jeff\t10");
        sampleScore.add("Jeff\t8");
        sampleScore.add("Jeff\t1");

        return sampleScore;
    }

    private List<String> johnSampleScore() {
        List<String> sampleScore = new ArrayList<>();
        sampleScore.add("John\t3");
        sampleScore.add("John\t7");
        sampleScore.add("John\t6");
        sampleScore.add("John\t3");
        sampleScore.add("John\t10");
        sampleScore.add("John\t8");
        sampleScore.add("John\t1");
        sampleScore.add("John\t10");
        sampleScore.add("John\t10");
        sampleScore.add("John\t9");
        sampleScore.add("John\t0");
        sampleScore.add("John\t7");
        sampleScore.add("John\t3");
        sampleScore.add("John\t4");
        sampleScore.add("John\t4");
        sampleScore.add("John\t10");
        sampleScore.add("John\t9");
        sampleScore.add("John\t0");

        return sampleScore;
    }

    private List<String> generateOnlyOneScores() {
        List<String> fileLines = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            fileLines.add("Bob\t1");
        }
        return fileLines;
    }

    private List<String> generateOnlyFaultScore() {
        List<String> fileLines = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            fileLines.add("Marcus\tF");
        }

        return fileLines;
    }
}
