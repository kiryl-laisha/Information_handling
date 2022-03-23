package org.laisha.information_handling.entity;

import java.util.List;

public interface TextComponent {

    boolean add(TextComponent textComponent);

    boolean remove(TextComponent textComponent);

    List<TextComponent> getComponents();

    TextComponentType getType();

    String toString();
}
