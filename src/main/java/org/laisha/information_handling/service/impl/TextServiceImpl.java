package org.laisha.information_handling.service.impl;

import org.laisha.information_handling.entity.TextComponent;
import org.laisha.information_handling.entity.TextComponentType;
import org.laisha.information_handling.entity.TextComposite;
import org.laisha.information_handling.exception.ProjectException;
import org.laisha.information_handling.service.TextService;

import java.util.*;
import java.util.stream.Collectors;

public class TextServiceImpl implements TextService {

    private static final String VOWEL_REGEX = "(?i)[aeiouy]";
    private static final String CONSONANT_REGEX = "(?i)[a-z&&[^aeiouy]]";
    private static final TextServiceImpl instance = new TextServiceImpl();

    private TextServiceImpl() {
    }

    public static TextServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<TextComponent> sortParagraphsBySentenceNumber(TextComponent text)
            throws ProjectException {

        validateTextComponent(text);
        List<TextComponent> paragraphs = text.getComponents();
        paragraphs = paragraphs.stream()
                .sorted(Comparator.comparingInt(c -> c.getComponents().size()))
                .collect(Collectors.toList());
        return paragraphs;
    }

    @Override
    public List<TextComponent> findSentenceWithLongestWord(TextComponent text)
            throws ProjectException {

        validateTextComponent(text);
        OptionalInt optionalMaxWordLength = text.getComponents().stream()
                .flatMap(paragraph -> paragraph.getComponents().stream())
                .flatMap(sentence -> sentence.getComponents().stream())
                .flatMap(lexeme -> lexeme.getComponents().stream())
                .filter(wap -> wap.getType() == TextComponentType.WORD)
                .mapToInt(word -> word.getComponents().size())
                .max();
        int maxWordLength = optionalMaxWordLength.orElseThrow(() -> new ProjectException(
                "Maximum word length can not be defined."));
        List<TextComponent> satisfyingSentences = text.getComponents().stream()
                .flatMap(paragraph -> paragraph.getComponents().stream())
                .filter(sentence -> sentence.getComponents().stream()
                        .anyMatch(lexeme -> lexeme.getComponents().stream()
                                .filter(wap -> wap.getType() == TextComponentType.WORD)
                                .anyMatch(word -> word.getComponents().size() == maxWordLength)))
                .collect(Collectors.toList());
        return satisfyingSentences;
    }

    @Override
    public void removeSentencesWithWordNumberLessThen(TextComponent text,
                                                      int minWordNumber)
            throws ProjectException {

        validateTextComponent(text);
        if (minWordNumber < 1) {
            throw new ProjectException("The number of words is less than 1.");
        }
        for (TextComponent paragraph : text.getComponents()) {
            List<TextComponent> removedSentences = new ArrayList<>();
            for (TextComponent sentence : paragraph.getComponents()) {
                int wordNumber = 0;
                for (TextComponent lexeme : sentence.getComponents()) {
                    for (TextComponent lexemeComponent : lexeme.getComponents()) {
                        if (lexemeComponent.getType() == TextComponentType.WORD) {
                            wordNumber++;
                        }
                    }
                }
                if (wordNumber < minWordNumber) {
                    removedSentences.add(sentence);
                }
            }
            for (TextComponent removingSentence : removedSentences) {
                paragraph.remove(removingSentence);
            }
        }
    }

    @Override
    public Map<String, Integer> findAndCountSameWords(TextComponent text)
            throws ProjectException {

        validateTextComponent(text);
        Map<String, Integer> countedUniqueWords = text.getComponents().stream()
                .flatMap(paragraph -> paragraph.getComponents().stream())
                .flatMap(sentence -> sentence.getComponents().stream())
                .flatMap(lexeme -> lexeme.getComponents().stream())
                .filter(wap -> wap.getType() == TextComponentType.WORD)
                .map(word -> word.toString().toLowerCase())
                .collect(Collectors.toMap(word -> word, i -> 1, Integer::sum));
        Map<String, Integer> sameWords = new HashMap<>();
        Iterator<Map.Entry<String, Integer>> iterator = countedUniqueWords.entrySet().iterator();
        Map.Entry<String, Integer> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            int currentValue = entry.getValue();
            if (currentValue > 1) {
                sameWords.put(entry.getKey(), currentValue);
            }
        }
        return sameWords;
    }

    @Override
    public int countVowelNumber(TextComponent sentence) throws ProjectException {

        validateSentenceComponent(sentence);
        List<String> letterList = collectLetterListFromSentence(sentence);
        int vowelNumber = (int) letterList.stream()
                .filter(letter -> letter.matches(VOWEL_REGEX))
                .count();
        return vowelNumber;
    }

    @Override
    public int countConsonantNumber(TextComponent sentence) throws ProjectException {

        validateSentenceComponent(sentence);
        List<String> letterList = collectLetterListFromSentence(sentence);

        int consonantNumber = (int) letterList.stream()
                .filter(letter -> letter.matches(CONSONANT_REGEX))
                .count();
        return consonantNumber;
    }

    private void validateTextComponent(TextComponent component) throws ProjectException {

        if (!(component instanceof TextComposite)) {
            throw new ProjectException("Invalid object class.");
        }
        TextComposite composite = (TextComposite) component;
        if (composite.getType() != TextComponentType.TEXT) {
            throw new ProjectException("Invalid TextComponent type.");
        }
    }

    private void validateSentenceComponent(TextComponent component) throws ProjectException {

        if (!(component instanceof TextComposite)) {
            throw new ProjectException("Invalid object class.");
        }
        TextComposite composite = (TextComposite) component;
        if (composite.getType() != TextComponentType.SENTENCE) {
            throw new ProjectException("Invalid TextComponent type.");
        }
    }

    private List<String> collectLetterListFromSentence(TextComponent sentence) {

        List<String> letterList = sentence.getComponents().stream()
                .flatMap(lexeme -> lexeme.getComponents().stream())
                .filter(wap -> wap.getType() == TextComponentType.WORD)
                .flatMap(word -> word.getComponents().stream())
                .map(TextComponent::toString)
                .collect(Collectors.toList());
        return letterList;
    }
}



