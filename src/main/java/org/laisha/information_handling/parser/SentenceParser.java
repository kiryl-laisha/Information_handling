package org.laisha.information_handling.parser;

import org.laisha.information_handling.entity.TextComponent;
import org.laisha.information_handling.entity.TextComponentType;
import org.laisha.information_handling.entity.TextComposite;

public class SentenceParser extends AbstractTextParser {

    private static final String SENTENCE_DELIMITER_REGEX = "(?<=[.!?])\\s(?=\\S)";

    public SentenceParser() {
        this.nextParser = new LexemeParser();
    }

    @Override
    public void parse(TextComponent component, String data) {

        String[] sentences = data.split(SENTENCE_DELIMITER_REGEX);
        for (String sentence : sentences) {
            TextComponent sentenceComponent = new TextComposite(TextComponentType.SENTENCE);
            component.add(sentenceComponent);
            nextParser.parse(sentenceComponent, sentence);
        }
    }
}
