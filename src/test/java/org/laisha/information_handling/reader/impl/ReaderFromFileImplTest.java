package org.laisha.information_handling.reader.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.laisha.information_handling.exception.ProjectException;

import static org.junit.jupiter.api.Assertions.*;

class ReaderFromFileImplTest {

    static final String TEST_TEXT_FILE_PATH = "data/test_text.txt";
    static final String TEST_EMPTY_FILE_FILE_PATH = "data/test_empty_file.txt";
    static final int TEST_TEXT_FILE_LENGTH = 794;
    static ReaderFromFileImpl reader = ReaderFromFileImpl.getInstance();

    @AfterAll
    static void tearDownClass() {
        reader = null;
    }

    @ParameterizedTest
    @DisplayName("Reading from valid test file.")
    @ValueSource(strings = {TEST_TEXT_FILE_PATH})
    void readTextFromFilePositiveTest(String filepath) {

        String text = "";
        try {
            text = reader.readTextFromFile(filepath);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
        int expected = TEST_TEXT_FILE_LENGTH;
        int actual = text.length();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Test catches an exception when filepath is null or empty string.")
    @NullAndEmptySource
    void readTextFromFileFirstNegativeTest(String filepath) {

        String expectedExceptionMessage = "Working with file is impossible.";
        String actualExceptionMessage = null;
        try {
            reader.readTextFromFile(filepath);
        } catch (ProjectException e) {
            actualExceptionMessage = e.getMessage();
        }
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @ParameterizedTest
    @DisplayName("Test catches an exception when file is empty.")
    @ValueSource(strings = {TEST_EMPTY_FILE_FILE_PATH})
    void readTextFromFileSecondNegativeTest(String filepath) {

        String expectedExceptionMessage = "File \"" + filepath + "\" " +
                "is empty. Reading from the file is impossible.";
        String actualExceptionMessage = null;
        try {
            reader.readTextFromFile(filepath);
        } catch (ProjectException e) {
            actualExceptionMessage = e.getMessage();
        }
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }
}