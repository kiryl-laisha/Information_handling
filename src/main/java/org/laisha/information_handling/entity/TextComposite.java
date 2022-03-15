package org.laisha.information_handling.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextComposite implements TextComponent {

    private final TextComponentType type;
    private final List<TextComponent> components = new ArrayList<>();

    public TextComposite(TextComponentType type) {
        this.type = type;
    }

    @Override
    public boolean add(TextComponent textComponent) {
        return components.add(textComponent);
    }

    @Override
    public boolean remove(TextComponent textComponent) {
        return components.remove(textComponent);
    }

    @Override
    public List<TextComponent> getComponents() {
        return Collections.unmodifiableList(components);
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
        TextComposite that = (TextComposite) obj;
        if (type != that.type) {
            return false;
        }
        return components.equals(that.components);
    }

    @Override
    public int hashCode() {

        int result = type.hashCode();
        result = 31 * result + components.hashCode();
        return result;
    }

    @Override
    public String toString() {

        StringBuilder text = new StringBuilder();
        text.append(type.getPrefix());
        components.forEach(component -> text.append(component.toString()));
        text.append(type.getPostfix());
        return text.toString();
    }
}
