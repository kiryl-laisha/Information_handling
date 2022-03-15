package org.laisha.information_handling.parser;

import org.laisha.information_handling.entity.TextComponent;

public abstract class AbstractTextParser {

    protected AbstractTextParser nextParser;

    protected AbstractTextParser() {
    }

    public abstract void parse(TextComponent component, String data);
}
