package org.laisha.information_handling.entity;

public enum TextComponentType {

    TEXT("", ""),
    PARAGRAPH("\t\t\t", "\n"),
    SENTENCE("", ""),
    LEXEME("", " "),
    WORD("", ""),
    LETTER("", ""),
    PUNCTUATION_MARK("", "");

    private final String prefix;
    private final String postfix;

    TextComponentType(String prefix, String postfix) {
        this.prefix = prefix;
        this.postfix = postfix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPostfix() {
        return postfix;
    }
}

