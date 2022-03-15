package org.laisha.information_handling.parser;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.laisha.information_handling.entity.TextComponent;
import org.laisha.information_handling.entity.TextComponentType;
import org.laisha.information_handling.entity.TextComposite;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LetterParserTest {

    static AbstractTextParser parser;
    TextComponent component;

    @BeforeAll
    static void setUpClass() {
        parser = new LetterParser();
    }

    @BeforeEach
    void setUp() {
        component = new TextComposite(TextComponentType.WORD);
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
    @ValueSource(strings = {"words",
            "mark9"})
    void parsePositiveTest(String data) {

        parser.parse(component, data);
        int expected = 5;
        int actual = component.getComponents().size();
        assertEquals(expected, actual);
    }
}