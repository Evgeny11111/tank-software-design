package ru.mipt.bit.platformer.objects.move;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Tank;

import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementedY;

public class UpCommand implements Command {

    private final Tank tank;

    public UpCommand(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void make() {
        GridPoint2 newPosition = incrementedY(tank.getCoordinates());
        GridPoint2 newDestinationCoordinates = incrementedY(tank.getDestinationCoordinates());

        if (tank.hasMoved()) {
            if (tank.checkCollisions(newPosition)) {
                tank.makeMovement(newDestinationCoordinates);
            }
            tank.changeRotation(Direction.Up);
        }
    }
}
