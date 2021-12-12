package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.objects.Event;
import ru.mipt.bit.platformer.objects.ObjectByGame;

/**
 * Entity
 */
public interface Issuer {
    void subscribe(Observer observer);

    void unsubscribe(Observer observer);

    void notifySubs(Event event, ObjectByGame objectByGame);
}
