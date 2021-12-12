package ru.mipt.bit.platformer.control.commands;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.control.Direction;

/**
 * Use case
 */
public class RightCommand implements Command {

    private final Tank tank;

    public RightCommand(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void make() {
        GridPoint2 newPosition = tank.getCoordsByDirection(tank.getCoordinates(), Direction.Right);
        GridPoint2 newDestinationCoordinates = tank.getCoordsByDirection(tank.getDestinationCoordinates(), Direction.Right);

        if (tank.hasMoved()) {
            if (tank.checkCollisions(newPosition)) {
                tank.makeMovement(newDestinationCoordinates);
            }
            tank.changeRotation(Direction.Right);
        }
    }
}
