package ru.mipt.bit.platformer.driver;

import ru.mipt.bit.platformer.objects.ObjectByGame;
import ru.mipt.bit.platformer.objects.Event;

public interface Subscriber {
    void update(Event event, ObjectByGame objectByGame);
}
