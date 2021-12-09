package ru.mipt.bit.platformer.objects.control;

import ru.mipt.bit.platformer.driver.Level;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.control.commands.*;


import java.util.concurrent.ThreadLocalRandom;

public class ControlForBots {
    public Command getRandomCommand(Tank tank, Level level) {
        int num = ThreadLocalRandom.current().nextInt(0, 10000) % 299;
        if (num == 0)
            return new ShootCommand(tank, level);
        return getRandomMoveCommand(tank);
    }

    public Command getRandomMoveCommand(Tank tank) {
        Direction direction = Direction.Up;
        direction = direction.getRandomDirection();
        switch (direction) {
            case Up:
                return new UpCommand(tank);
            case Down:
                return new DownCommand(tank);
            case Left:
                return new LeftCommand(tank);
        }
        return new RightCommand(tank);
    }
}
