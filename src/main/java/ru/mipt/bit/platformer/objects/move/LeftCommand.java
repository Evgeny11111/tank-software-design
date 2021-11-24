package ru.mipt.bit.platformer.objects.move;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Tank;

import static ru.mipt.bit.platformer.util.GdxGameUtils.decrementedX;

public class LeftCommand implements Command {

    private final Tank tank;

    public LeftCommand(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void make() {
        GridPoint2 newPosition = decrementedX(tank.getCoordinates());
        GridPoint2 newDestinationCoordinates = decrementedX(tank.getDestinationCoordinates());

        if (tank.hasMoved()) {
            if (tank.checkCollisions(newPosition)) {
                tank.makeMovement(newDestinationCoordinates);
            }
            tank.changeRotation(Direction.Left);
        }
    }
}
