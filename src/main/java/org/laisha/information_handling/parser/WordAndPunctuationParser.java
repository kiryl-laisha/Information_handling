package org.laisha.information_handling.parser;

import org.laisha.information_handling.entity.TextComponent;
import org.laisha.information_handling.entity.TextComponentType;
import org.laisha.information_handling.entity.TextComposite;
import org.laisha.information_handling.entity.TextLeaf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordAndPunctuationParser extends AbstractTextParser {

    private static final String WORD_REGEXP = "[\\w]+";
    private static final String WORD_OR_PUNCTUATION_REGEXP = WORD_REGEXP + "|[\\p{Punct}]";

    public WordAndPunctuationParser() {
        nextParser = new LetterParser();
    }

    @Override
    public void parse(TextComponent component, String data) {

        Pattern pattern = Pattern.compile(WORD_OR_PUNCTUATION_REGEXP);
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            String currentComponent = matcher.group();
            if (currentComponent.matches(WORD_REGEXP)) {
                TextComponent wordComponent = new TextComposite(TextComponentType.WORD);
                component.add(wordComponent);
                nextParser.parse(wordComponent, matcher.group());
            } else {
                TextLeaf punctuationMark =
                        new TextLeaf(matcher.group().charAt(0), TextComponentType.PUNCTUATION_MARK);
                component.add(punctuationMark);
            }
        }
    }
}
