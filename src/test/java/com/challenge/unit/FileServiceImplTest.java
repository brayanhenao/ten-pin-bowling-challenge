package com.challenge.unit;

import com.challenge.exceptions.InvalidFileException;
import com.challenge.service.FileService;
import com.challenge.service.impl.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Brayan Andrés Henao
 */
class FileServiceImplTest {

    private static final String CORRECT_FILE_NAME = "src/test/resources/sample_score_file.txt";
    private static final String ERROR_FILE_NAME = "src/test/resources/invalid_line_file.txt";

    private FileService fileService;

    @BeforeEach
    void init() {
        fileService = new FileServiceImpl();
    }

    @Test
    void processFile() throws IOException, InvalidFileException {
        assertNotNull(fileService.processFile(CORRECT_FILE_NAME));
    }

    @Test
    void processWrongFile() {
        assertThrows(InvalidFileException.class,
                () -> fileService.processFile(ERROR_FILE_NAME));
    }
}