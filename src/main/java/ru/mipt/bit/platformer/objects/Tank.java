package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.driver.CollisionChecker;
import ru.mipt.bit.platformer.objects.move.Direction;

import java.util.HashMap;
import java.util.Objects;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank {
    private final float MOVEMENT_SPEED = 0.4f;

    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 coordinates;
    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;
    private final HashMap<Direction, Float> rotates;

    private final CollisionChecker collisionChecker;

    public Tank(GridPoint2 coords, CollisionChecker collisionChecker) {
        this.destinationCoordinates = new GridPoint2(coords);
        this.coordinates = new GridPoint2(this.destinationCoordinates);
        this.rotation = 0f;
        rotates = new HashMap<>();
        rotates.put(Direction.Up, 90f);
        rotates.put(Direction.Left, -180f);
        rotates.put(Direction.Down, -90f);
        rotates.put(Direction.Right, 0f);
        this.collisionChecker = collisionChecker;
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

    public float getRotation() {
        return this.rotation;
    }

    public boolean hasMoved() {
        return isEqual(this.movementProgress, 1f);
    }

    public boolean isMovementPossible(GridPoint2 obstacleCoordinates, GridPoint2 newPosition) {
        return !obstacleCoordinates.equals(newPosition);
    }

    public boolean checkCollisions(GridPoint2 newPosition) {
        return collisionChecker.checkCollisions(newPosition, this);
    }

    public void makeMovement(GridPoint2 newDestinationCoordinates) {
        this.destinationCoordinates = new GridPoint2(newDestinationCoordinates);
        this.movementProgress = 0f;
    }

    public void changeRotation(Direction direction) {
        this.rotation = rotates.get(direction);
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

    public float getMovementSpeed() {
        return MOVEMENT_SPEED;
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    public void setDestinationCoordinates(GridPoint2 coordinates) {
        this.destinationCoordinates = coordinates;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Tank)) return false;
        if (obj == this) return true;
        return (((Tank) obj).getCoordinates() == this.getCoordinates()
                & ((Tank) obj).getDestinationCoordinates() == this.getDestinationCoordinates()
                & ((Tank) obj).getRotation() == this.getRotation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(MOVEMENT_SPEED, coordinates, destinationCoordinates, movementProgress, rotation, rotates);
    }
}