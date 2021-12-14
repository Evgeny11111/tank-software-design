package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.driver.Level;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.control.commands.*;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Use case
 */
public class ControlBots implements Controller {
    @Override
    public List<Command> getCommands(List<Tank> tanks, Level level) {
        List<Command> commands = new ArrayList<>();
        for (Tank tank : tanks) {
            int num = (int) (Math.random() * 100);
            if (num == 0)
                commands.add(new ShootCommand(tank, level));
            commands.add(getRandomMoveCommand(tank));
        }
        return commands;
    }

    public Command getRandomMoveCommand(Tank tank) {
        Direction direction = Direction.Up;
        direction = direction.getRandomDirection();
        return switch (direction) {
            case Up -> new UpCommand(tank);
            case Down -> new DownCommand(tank);
            case Left -> new LeftCommand(tank);
            default -> new RightCommand(tank);
        };
    }
}
