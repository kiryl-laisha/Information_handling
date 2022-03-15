package org.laisha.information_handling.parser;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.laisha.information_handling.entity.TextComponent;
import org.laisha.information_handling.entity.TextComponentType;
import org.laisha.information_handling.entity.TextComposite;

import static org.junit.jupiter.api.Assertions.*;

class LexemeParserTest {

    static AbstractTextParser parser;
    TextComponent component;

    @BeforeAll
    static void setUpClass() {
        parser = new LexemeParser();
    }

    @BeforeEach
    void setUp() {
        component = new TextComposite(TextComponentType.SENTENCE);
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
    @DisplayName("Test parses provided sentence to lexemes.")
    @ValueSource(strings = {"Wind and warm, hot and pretty.",
            "He and I - old persons",
            "He, she, it, they and we..."})
    void parsePositiveTest(String data) {

        parser.parse(component, data);
        int expected = 6;
        int actual = component.getComponents().size();
        assertEquals(expected, actual);
    }
}