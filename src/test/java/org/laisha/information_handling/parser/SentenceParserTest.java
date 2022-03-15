package org.laisha.information_handling.parser;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.laisha.information_handling.entity.TextComponent;
import org.laisha.information_handling.entity.TextComponentType;
import org.laisha.information_handling.entity.TextComposite;
import org.laisha.information_handling.exception.ProjectException;
import org.laisha.information_handling.reader.impl.ReaderFromFileImpl;

import static org.junit.jupiter.api.Assertions.*;

class SentenceParserTest {

    static ReaderFromFileImpl reader;

    static AbstractTextParser parser;
    TextComponent component;

    @BeforeAll
    static void setUpClass() {

        reader = ReaderFromFileImpl.getInstance();
        parser = new SentenceParser();
    }

    @BeforeEach
    void setUp() {
        component = new TextComposite(TextComponentType.PARAGRAPH);
    }

    @AfterEach
    void tearDown() {
        component = null;
    }

    @AfterAll
    static void tearDownClass() {

        reader = null;
        parser = null;
    }

    @ParameterizedTest
    @DisplayName("Test parses provided paragraph to sentences.")
    @ValueSource(strings = {"Wind and warm! Hot and pretty...",
            "He and I!!! Old persons.",
            "He, she, it. They and we, you and you?"})
    void parseFirstPositiveTest(String data) {

        parser.parse(component, data);
        int expected = 2;
        int actual = component.getComponents().size();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Test parses a paragraph from provided file to sentences.")
    @ValueSource(strings = {"data\\test_paragraph.txt"})
    void parseSecondPositiveTest(String filepath) {

        String data = null;
        try {
            data = reader.readTextFromFile(filepath);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
        parser.parse(component, data);
        int expected = 2;
        int actual = component.getComponents().size();
        assertEquals(expected, actual);
    }
}