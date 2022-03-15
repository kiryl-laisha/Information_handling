package org.laisha.information_handling.entity;

import java.util.List;

public class TextLeaf implements TextComponent {

    private final char character;
    private final TextComponentType type;

    public TextLeaf(char character, TextComponentType type) {

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
        TextLeaf textLeaf = (TextLeaf) obj;
        if (character != textLeaf.character) {
            return false;
        }
        return type == textLeaf.type;
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
