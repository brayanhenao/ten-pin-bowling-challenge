package com.challenge.service.impl;

import com.challenge.exceptions.InvalidFileException;
import com.challenge.service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Brayan Andrés Henao
 */
public class FileServiceImpl implements FileService {

    // METHODS

    /**
     * Process the game scores file.
     *
     * @param fileName - the file name.
     * @return List all the file lines.
     */
    @Override
    public List<String> processFile(String fileName) throws InvalidFileException, IOException {

        List<String> fileLines;
        Stream<String> stream = Files.lines(Paths.get(fileName));

        fileLines = stream.collect(Collectors.toList());

        if (fileLines.stream().anyMatch(this::isInvalidFileLine)) {
            throw new InvalidFileException("Invalid file");
        }

        stream.close();

        return fileLines;
    }

    /**
     * Method that checks if a like is valid or not.
     *
     * @param fileLine - a line of the file.
     * @return Boolean - If is or not a valid file line.
     */
    private boolean isInvalidFileLine(String fileLine) {
        return fileLine == null || fileLine.trim().isEmpty();
    }
}
