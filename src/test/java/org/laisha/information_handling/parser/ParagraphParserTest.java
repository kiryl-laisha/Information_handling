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

class ParagraphParserTest {

    static final String TEST_TEXT_FILE_PATH = "data/test_text.txt";
    static AbstractTextParser parser;
    static ReaderFromFileImpl reader;
    TextComponent component;

    @BeforeAll
    static void setUpClass() {

        parser = new ParagraphParser();
        reader = ReaderFromFileImpl.getInstance();
    }

    @BeforeEach
    void setUp() {
        component = new TextComposite(TextComponentType.TEXT);
    }

    @AfterEach
    void tearDown() {
        component = null;
    }

    @AfterAll
    static void tearDownClass() {

        parser = null;
        reader = null;
    }

    @ParameterizedTest
    @DisplayName("Test parses a text from provided file path to paragraphs.")
    @ValueSource(strings = {TEST_TEXT_FILE_PATH})
    void parseFirstPositiveTest(String filepath) {

        String data = null;
        try {
            data = reader.readTextFromFile(filepath);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
        parser.parse(component, data);
        int expected = 4;
        int actual = component.getComponents().size();
        System.out.println(component.getComponents().toString());
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Test parses provided text to paragraphs.")
    @ValueSource(strings = {"Wind and warm!\n\tHot and pretty...\n    Accord!.."})
    void parseSecondPositiveTest(String data) {

        parser.parse(component, data);
        int expected = 3;
        int actual = component.getComponents().size();
        assertEquals(expected, actual);
    }
}