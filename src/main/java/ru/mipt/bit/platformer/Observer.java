package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.objects.ObjectByGame;
import ru.mipt.bit.platformer.objects.Event;

public interface Observer {
    void update(Event event, ObjectByGame objectByGame);
}
