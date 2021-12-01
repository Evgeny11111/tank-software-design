package ru.mipt.bit.platformer.objects.move;

import ru.mipt.bit.platformer.objects.Tank;

public class ControlForBots {
    public Command processRandom(Tank tank) {
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
