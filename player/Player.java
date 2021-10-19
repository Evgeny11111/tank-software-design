package ru.mipt.bit.platformer.player;

import com.badlogic.gdx.Gdx;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private final Rectangle rectangle;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private static GridPoint2 coordinates;
    private float movementProgress = 1f;
    private float rotation = 0f;


    public Player(GridPoint2 coordinates) {
        this.coordinates = coordinates;
        rectangle = createBoundingRectangle(GraphicsPlayer.getPlayer());
    }

    // get time passed since the last render
    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public static GridPoint2 getCoordinates() {
        return coordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return rotation;
    }

    public void setCoordinates(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    public void setMovementProgress(float movementProgress) {
        this.movementProgress = movementProgress;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

}
