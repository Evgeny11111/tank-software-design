package ru.mipt.bit.platformer.objects.control.commands;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.control.Direction;
import ru.mipt.bit.platformer.objects.control.commands.Command;

public class LeftCommand implements Command {

    private final Tank tank;

    public LeftCommand(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void make() {
        GridPoint2 newPosition = tank.getCoordsByDirection(tank.getCoordinates(), Direction.Left);
        GridPoint2 newDestinationCoordinates = tank.getCoordsByDirection(tank.getDestinationCoordinates(), Direction.Left);

        if (tank.hasMoved()) {
            if (tank.checkCollisions(newPosition)) {
                tank.makeMovement(newDestinationCoordinates);
            }
            tank.changeRotation(Direction.Left);
        }
    }
}
