package com.challenge.service;

import com.challenge.exceptions.InvalidFileException;

import java.io.IOException;
import java.util.List;

/**
 * @author Brayan Andrés Henao
 */
public interface FileService {

    /**
     * Process the game scores file.
     *
     * @param fileName - the file name.
     * @return List all the file lines.
     */
    List<String> processFile(String fileName) throws IOException, InvalidFileException;
}
