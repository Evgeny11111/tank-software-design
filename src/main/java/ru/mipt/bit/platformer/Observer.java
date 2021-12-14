package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.objects.Event;

/**
 * Entity
 */
public interface Observer {
    void update(Event event, Object objectByGame);
}
