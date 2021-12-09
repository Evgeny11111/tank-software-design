package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.driver.CollisionChecker;
import ru.mipt.bit.platformer.objects.control.Direction;

import java.util.Date;
import java.util.Objects;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tank implements ObjectByGame{
    private float MOVEMENT_SPEED = 0.4f;

    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private GridPoint2 coordinates;
    // which tile the player want to go next
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    private final CollisionChecker collisionChecker;

    private float life = 99f;

    private boolean alive;
    private long lastTimeShooting = new Date().getTime();

    public Tank(GridPoint2 coords, CollisionChecker collisionChecker) {
        this.destinationCoordinates = new GridPoint2(coords);
        this.coordinates = new GridPoint2(this.destinationCoordinates);
        this.rotation = 0f;

        this.collisionChecker = collisionChecker;
        alive = true;
    }

    public boolean isAlive() {
        return this.alive;
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

    public long getLastTimeShooting() {
        return lastTimeShooting;
    }

    public void setLastTimeShooting(long time) {
        lastTimeShooting = time;
    }

    public boolean hasMoved() {
        return isEqual(this.movementProgress, 1f);
    }

    public CollisionChecker getCollisionChecker() {
        return this.collisionChecker;
    }

    public GridPoint2 getCoordsByDirection(GridPoint2 coords, Direction direction) {
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

    public boolean isMovementPossible(GridPoint2 obstacleCoordinates, GridPoint2 newPosition) {
        return !obstacleCoordinates.equals(newPosition);
    }

    public boolean checkCollisions(GridPoint2 newPosition) {
        return collisionChecker.checkCollisionsWithTank(newPosition, this);
    }

    public void makeMovement(GridPoint2 newDestinationCoordinates) {
        this.destinationCoordinates = new GridPoint2(newDestinationCoordinates);
        this.movementProgress = 0f;
    }

    public void changeRotation(Direction direction) {
        this.rotation = direction.mapFromDirection(direction);
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

    public void live(float deltaTime) {
        changeMovementProgress(deltaTime);
        reachDestination();
    }

    public void takeDamage(Bullet bullet) {
        System.out.println("Tank " + coordinates.x + " " + coordinates.y + " is getting damage from " + life + " to " + (life - bullet.getDamage()));
        life -= bullet.getDamage();
        if (life <= 0f)
            alive = false;
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
                & ((Tank) obj).getMovementProgress() == this.getMovementProgress()
                & ((Tank) obj).getRotation() == this.getRotation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, destinationCoordinates, movementProgress, rotation);
    }
}