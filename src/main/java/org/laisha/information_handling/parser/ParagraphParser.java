package org.laisha.information_handling.parser;

import org.laisha.information_handling.entity.TextComponent;
import org.laisha.information_handling.entity.TextComponentType;
import org.laisha.information_handling.entity.TextComposite;

public class ParagraphParser extends AbstractTextParser {

    private static final String PARAGRAPH_DELIMITER_REGEX = "\\n(\\t|\\s{4})";

    public ParagraphParser() {
        this.nextParser = new SentenceParser();
    }

    @Override
    public void parse(TextComponent component, String data) {

        data = data.strip();
        String[] paragraphs = data.split(PARAGRAPH_DELIMITER_REGEX);
        for (String paragraph : paragraphs) {
            TextComponent paragraphComponent = new TextComposite(TextComponentType.PARAGRAPH);
            component.add(paragraphComponent);
            nextParser.parse(paragraphComponent, paragraph);
        }
    }
}
