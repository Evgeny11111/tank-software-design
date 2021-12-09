package ru.mipt.bit.platformer.objects.generators;

import ru.mipt.bit.platformer.driver.Driver;
import ru.mipt.bit.platformer.driver.Level;

public interface LevelGenerator {
    Level generateLevel();
}
