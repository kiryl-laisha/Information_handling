package org.laisha.information_handling.parser;

import org.laisha.information_handling.entity.TextComponent;
import org.laisha.information_handling.entity.TextComponentType;
import org.laisha.information_handling.entity.Symbol;

public class LetterParser extends AbstractTextParser {

    @Override
    public void parse(TextComponent component, String data) {

        char[] letters = data.toCharArray();
        for (char letter : letters) {
            component.add(new Symbol(letter, TextComponentType.LETTER));
        }
    }
}
