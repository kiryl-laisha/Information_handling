package org.laisha.information_handling.entity;

import java.util.List;

public class Symbol implements TextComponent {

    private final char character;
    private final TextComponentType type;

    public Symbol(char character, TextComponentType type) {

        this.character = character;
        this.type = type;
    }

    @Override
    public boolean add(TextComponent textComponent) {
        throw new UnsupportedOperationException("Unavailable operation for this component.");
    }

    @Override
    public boolean remove(TextComponent textComponent) {
        throw new UnsupportedOperationException("Unavailable operation for this component.");
    }

    @Override
    public List<TextComponent> getComponents() {
        throw new UnsupportedOperationException("Unavailable operation for this component.");
    }

    @Override
    public TextComponentType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Symbol symbol = (Symbol) obj;
        if (character != symbol.character) {
            return false;
        }
        return type == symbol.type;
    }

    @Override
    public int hashCode() {

        int result = character;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.valueOf(character);
    }
}
