package org.laisha.information_handling.service;

import org.laisha.information_handling.entity.TextComponent;
import org.laisha.information_handling.exception.ProjectException;

import java.util.List;
import java.util.Map;

public interface TextService {

    List<TextComponent> sortParagraphsBySentenceNumber(TextComponent text)
            throws ProjectException;

    List<TextComponent> findSentenceWithLongestWord(TextComponent text)
            throws ProjectException;

    void removeSentencesWithWordNumberLessThen(TextComponent text,
                                                              int minWordNumber)
            throws ProjectException;

    Map<String, Integer> findAndCountSameWords(TextComponent text)
            throws ProjectException;

    int countVowelNumber(TextComponent text) throws ProjectException;

    int countConsonantNumber(TextComponent text) throws ProjectException;
}
