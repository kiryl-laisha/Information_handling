package org.laisha.information_handling.parser;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.laisha.information_handling.entity.TextComponent;
import org.laisha.information_handling.entity.TextComponentType;
import org.laisha.information_handling.entity.TextComposite;

import static org.junit.jupiter.api.Assertions.*;

class WordAndPunctuationParserTest {

    static AbstractTextParser parser;
    TextComponent component;

    @BeforeAll
    static void setUpClass() {
        parser = new WordAndPunctuationParser();
    }

    @BeforeEach
    void setUp() {
        component = new TextComposite(TextComponentType.LEXEME);
    }

    @AfterEach
    void tearDown() {
        component = null;
    }

    @AfterAll
    static void tearDownClass() {
        parser = null;
    }

    @ParameterizedTest
    @DisplayName("Test parses provided word to letters and numbers.")
    @ValueSource(strings = {"Wind",
            "vocabulary",
            "Falcon9"})
    void parseFirstPositiveTest(String data) {

        parser.parse(component, data);
        int expected = 1;
        int actual = component.getComponents().size();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Test parses provided lexeme to letters and punctuation marks.")
    @ValueSource(strings = {"Wind!",
            "vocabulary,",
            "Falcon9!"})
    void parseSecondPositiveTest(String data) {

        parser.parse(component, data);
        int expected = 2;
        int actual = component.getComponents().size();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Test parses provided lexeme to letters and punctuation marks.")
    @ValueSource(strings = {"\"Wind\"",
            "(vocabulary)"})
    void parseThirdPositiveTest(String data) {

        parser.parse(component, data);
        int expected = 3;
        int actual = component.getComponents().size();
        assertEquals(expected, actual);
    }
}