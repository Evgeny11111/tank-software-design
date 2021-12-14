package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.objects.Event;

/**
 * Entity
 */
public interface Issuer {
    void observe(Observer observer);

    void unobserve(Observer observer);

    void notifySubs(Event event, Object objectByGame);
}
