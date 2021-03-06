package org.laisha.information_handling.validator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class FilePathValidatorTest {

    static FilePathValidator validator = FilePathValidator.getInstance();

    @AfterAll
    static void tearDownClass() {
        validator = null;
    }

    @ParameterizedTest
    @DisplayName("The file path is valid.")
    @ValueSource(strings = {"data\\test_text.txt"})
    void validateFilePathPositiveTest(String filepath) {

        boolean isValidFilePath = validator.validateFilePath(filepath);
        assertTrue(isValidFilePath);
    }

    @ParameterizedTest
    @DisplayName("The file path is null or empty or not exist.")
    @NullAndEmptySource
    @ValueSource(strings = {"ata/empty_test_file.txt",
            "data\\file.txt",
            " ",
            "\n  \t \r  "})
    void validateFilePathNegativeTest(String filepath) {

        boolean isValidFilePath = validator.validateFilePath(filepath);
        assertFalse(isValidFilePath);
    }
}