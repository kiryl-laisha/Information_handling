package org.laisha.information_handling.parser;

import org.laisha.information_handling.entity.TextComponent;
import org.laisha.information_handling.entity.TextComponentType;
import org.laisha.information_handling.entity.TextComposite;
import org.laisha.information_handling.entity.Symbol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordAndPunctuationParser extends AbstractTextParser {

    private static final String PUNCTUATION_AND_NEW_LINE_MARK_REGEXP = "[\\p{Punct}\\n]";
    private static final String WORD_REGEXP = "[\\w]+";
    private static final String COMPLEX_REGEXP =
            WORD_REGEXP + "|" + PUNCTUATION_AND_NEW_LINE_MARK_REGEXP;

    public WordAndPunctuationParser() {
        nextParser = new LetterParser();
    }

    @Override
    public void parse(TextComponent component, String data) {

        Pattern pattern = Pattern.compile(COMPLEX_REGEXP);
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            String currentComponent = matcher.group();
            if (currentComponent.matches(WORD_REGEXP)) {
                TextComponent wordComponent = new TextComposite(TextComponentType.WORD);
                component.add(wordComponent);
                nextParser.parse(wordComponent, matcher.group());
            } else {
                Symbol punctuationMark =
                        new Symbol(matcher.group().charAt(0), TextComponentType.PUNCTUATION_MARK);
                component.add(punctuationMark);
            }
        }
    }
}
