package org.laisha.information_handling.service.impl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.laisha.information_handling.entity.TextComponent;
import org.laisha.information_handling.entity.TextComponentType;
import org.laisha.information_handling.entity.TextComposite;
import org.laisha.information_handling.entity.Symbol;
import org.laisha.information_handling.exception.ProjectException;
import org.laisha.information_handling.parser.AbstractTextParser;
import org.laisha.information_handling.parser.LexemeParser;
import org.laisha.information_handling.parser.ParagraphParser;
import org.laisha.information_handling.reader.impl.ReaderFromFileImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TextServiceImplTest {

    static final String TEST_TEXT_FILE_PATH = "data/test_text.txt";
    static ReaderFromFileImpl reader;
    static TextServiceImpl service;
    AbstractTextParser parser;
    TextComponent component;

    @BeforeAll
    static void setUpClass() {

        reader = ReaderFromFileImpl.getInstance();
        service = TextServiceImpl.getInstance();
    }

    @AfterEach
    void tearDown() {

        component = null;
        parser = null;
    }

    @AfterAll
    static void tearDownClass() {

        reader = null;
        service = null;
    }

    @ParameterizedTest
    @DisplayName("Test sorts paragraphs of the text from provided file path " +
            "by number of sentences.")
    @ValueSource(strings = {TEST_TEXT_FILE_PATH})
    void sortParagraphsBySentenceNumberPositiveTest(String filepath) {

        component = new TextComposite(TextComponentType.TEXT);
        parser = new ParagraphParser();
        List<TextComponent> componentList = new ArrayList<>();
        String data;
        try {
            data = reader.readTextFromFile(filepath);
            parser.parse(component, data);
            componentList = service.sortParagraphsBySentenceNumber(component);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
        int expected = 1;//The least number of sentences into paragraphs.
        int actual = componentList.get(0).getComponents().size();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test checks a validation of provided instance which is not " +
            "an instance of TextComposite.")
    void sortParagraphsBySentenceNumberFirstNegativeTest() {

        component = new Symbol('a', TextComponentType.LETTER);
        String expectedExceptionMessage = "Invalid object class.";
        String actualExceptionMessage = null;
        try {
            service.sortParagraphsBySentenceNumber(component);
        } catch (ProjectException e) {
            actualExceptionMessage = e.getMessage();
        }
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

    @Test
    @DisplayName("Test checks a validation of provided instance. The text component " +
            "type of this instance is not \"TEXT\".")
    void sortParagraphsBySentenceNumberSecondNegativeTest() {

        int minWordNumber = 3;
        component = new TextComposite(TextComponentType.LEXEME);
        String expectedExceptionMessage = "Invalid TextComponent type.";
        String actualExceptionMessage = null;
        try {
            service.removeSentencesWithWordNumberLessThen(component, minWordNumber);
        } catch (ProjectException e) {
            actualExceptionMessage = e.getMessage();
        }
        assertEquals(expectedExceptionMessage, actualExceptionMessage);
    }

/*
    These negative tests should be created for all service methods because they control
    a correctness of using a private validate method.
*/

    @ParameterizedTest
    @DisplayName("Test finds sentence with longest word of the text " +
            "from provided file path.")
    @ValueSource(strings = {TEST_TEXT_FILE_PATH})
    void findSentenceWithLongestWordPositiveTest(String filepath) {

        component = new TextComposite(TextComponentType.TEXT);
        parser = new ParagraphParser();
        List<TextComponent> componentList = new ArrayList<>();
        String data;
        try {
            data = reader.readTextFromFile(filepath);
            parser.parse(component, data);
            componentList = service.findSentenceWithLongestWord(component);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
        String expected = "The point of using Ipsum is that it has a\n" +
                "more-or-less normal distribution ob.toString(a?b:c), as opposed to using (Content here),\n" +
                "content here's, making it look like readable English? ";
        String actual = componentList.get(0).toString();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Test removes sentences containing number of words less than 3." +
            "Parameter - file path for text.")
    @ValueSource(strings = {TEST_TEXT_FILE_PATH})
    void removeSentencesWithWordNumberLessThenPositiveTest(String filepath) {

        component = new TextComposite(TextComponentType.TEXT);
        parser = new ParagraphParser();
        String data;
        int minWordNumber = 3;
        try {
            data = reader.readTextFromFile(filepath);
            parser.parse(component, data);
            service.removeSentencesWithWordNumberLessThen(component, minWordNumber);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
        int expected = 5;//The number of sentences containing at least 3 words
        int actual = component.getComponents().stream()
                .mapToInt(paragraph -> paragraph.getComponents().size())
                .sum();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Test finds and counts same words of the text " +
            "from provided file path.")
    @ValueSource(strings = {TEST_TEXT_FILE_PATH})
    void findAndCountSameWordsPositiveTest(String filepath) {

        component = new TextComposite(TextComponentType.TEXT);
        parser = new ParagraphParser();
        String data;
        Map<String, Integer> sameWords = new HashMap<>();
        try {
            data = reader.readTextFromFile(filepath);
            parser.parse(component, data);
            sameWords = service.findAndCountSameWords(component);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
        int expected = 31;//The number of words that occur more than once
        int actual = sameWords.size();
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Test counts the number of vowels in provided sentence.")
    @ValueSource(strings = "Provided sentence!")
    void countVowelNumberPositiveTest(String data) {

        component = new TextComposite(TextComponentType.SENTENCE);
        parser = new LexemeParser();
        int expected = 6;//The number of vowels in provided sentence
        int actual = 0;
        try {
            parser.parse(component, data);
            actual = service.countVowelNumber(component);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Test counts the number of consonants in provided sentence.")
    @ValueSource(strings = "Provided sentence!")
    void countConsonantNumberPositiveTest(String data) {

        component = new TextComposite(TextComponentType.SENTENCE);
        parser = new LexemeParser();
        int expected = 10;//The number of consonants in provided sentence
        int actual = 0;
        try {
            parser.parse(component, data);
            actual = service.countConsonantNumber(component);
        } catch (ProjectException e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);
    }
}