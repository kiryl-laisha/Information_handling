package org.laisha.information_handling.parser;

import org.laisha.information_handling.entity.TextComponent;
import org.laisha.information_handling.entity.TextComponentType;
import org.laisha.information_handling.entity.TextComposite;

public class LexemeParser extends AbstractTextParser {

    private static final String LEXEME_DELIMITER_REGEX = "\\p{Blank}";

    public LexemeParser() {
        this.nextParser = new WordAndPunctuationParser();
    }

    @Override
    public void parse(TextComponent component, String data) {

        String[] lexemes = data.split(LEXEME_DELIMITER_REGEX);
        for (String lexeme : lexemes) {
            TextComponent lexemeComponent = new TextComposite(TextComponentType.LEXEME);
            component.add(lexemeComponent);
            nextParser.parse(lexemeComponent, lexeme);
        }
    }
}
