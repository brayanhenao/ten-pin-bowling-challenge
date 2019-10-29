package com.challenge.unit;

import com.challenge.exceptions.InvalidFileException;
import com.challenge.exceptions.InvalidScoreException;
import com.challenge.model.Player;
import com.challenge.service.GameService;
import com.challenge.service.impl.GameServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Brayan Andrés Henao
 */
class GmeServiceImplTest {

    private GameService gameService;

    @Test
    void generateScoresForGameTest() throws InvalidScoreException, InvalidFileException {
        List<String> fileLines = generateLines();
        gameService = new GameServiceImpl(fileLines);
        Map<Player, Integer> map = gameService.generateScoresForGame();

        List<Player> players = new ArrayList<>(map.keySet());
        List<Integer> scores = new ArrayList<>(map.values());

        assertEquals("Bob", players.get(0).getPlayerName());

        assertEquals(20, scores.get(0));
    }

    private List<String> generateLines() {
        List<String> fileLines = new ArrayList<>();

        for (int i = 0; i < 21; i++) {
            fileLines.add("Bob\t1");
        }

        return fileLines;
    }
}
