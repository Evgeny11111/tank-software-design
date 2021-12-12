package ru.mipt.bit.platformer.objects;

/**
 * Entity
 */
public interface ObjectByGame {
    @Override
    boolean equals(Object obj);

    @Override
    int hashCode();
}
