package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.driver.CollisionChecker;
import ru.mipt.bit.platformer.control.Direction;

import java.util.Objects;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

/**
 * Entity
 */
public class Bullet{
    private final float MOVEMENT_SPEED = 0.6f;
    public final float damage = 25;
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private final float rotation;
    private Direction direction = Direction.Up;
    private boolean existent = true;
    private float movementProgress = 1f;

    private final Tank tank;

    private final CollisionChecker collisionChecker;

    public Bullet(GridPoint2 coords, float rotation, CollisionChecker collisionChecker, Tank tank) {
        this.coordinates = new GridPoint2(coords);
        this.destinationCoordinates = new GridPoint2(coords);
        this.rotation = rotation;
        this.direction = direction.mapFromFloat(rotation);

        this.collisionChecker = collisionChecker;

        this.tank = tank;
    }

    public boolean isExistent() {
        return existent;
    }

    public Tank getTank() {
        return tank;
    }

    public GridPoint2 getCoordinates() {
        return this.coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return this.destinationCoordinates;
    }

    public float getMovementProgress() {
        return this.movementProgress;
    }

    public float getDamage() {
        return damage;
    }

    public void setNotExistent() {
        existent = false;
    }

    public void live(float deltaTime) {
        if (hasMoved() && checkCollisions(destinationCoordinates))
            makeMovement(getNextCoords(destinationCoordinates));
        changeMovementProgress(deltaTime);
        reachDestination();
    }

    public boolean checkCollisions(GridPoint2 newPosition) {
        return collisionChecker.checkCollisionsWithBullet(newPosition, this);
    }

    public boolean isMovementPossible(GridPoint2 obstacleCoordinates, GridPoint2 newPosition) {
        return !obstacleCoordinates.equals(newPosition);
    }

    public boolean hasMoved() {
        return isEqual(this.movementProgress, 1f);
    }

    public void makeMovement(GridPoint2 newDestinationCoordinates) {
        this.destinationCoordinates = new GridPoint2(newDestinationCoordinates);
        this.movementProgress = 0f;
    }

    public void changeMovementProgress(float deltaTime) {
        this.movementProgress = continueProgress(this.movementProgress, deltaTime, MOVEMENT_SPEED);
    }

    void setCoordinates() {
        this.coordinates.set(this.destinationCoordinates);
    }

    public void reachDestination() {
        if (this.hasMoved()) {
            this.setCoordinates();
        }
    }

    public GridPoint2 getNextCoords(GridPoint2 coords) {
        GridPoint2 newPosition = new GridPoint2(coords);
        switch (direction) {
            case Up:
                newPosition = incrementedY(newPosition);
                break;
            case Left:
                newPosition = decrementedX(newPosition);
                break;
            case Down:
                newPosition = decrementedY(newPosition);
                break;
            case Right:
                newPosition = incrementedX(newPosition);
                break;
        }
        return newPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bullet bullet = (Bullet) o;
        return (Float.compare(bullet.rotation, rotation) == 0
                && coordinates.equals(bullet.coordinates)
                && destinationCoordinates.equals(bullet.destinationCoordinates));
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, destinationCoordinates, rotation);
    }
}
