package ru.mipt.bit.platformer.player.move;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(new GridPoint2(0, 1),90f),
    DOWN(new GridPoint2(0, -1),-90f),
    LEFT(new GridPoint2(-1, 0),180f),
    RIGTH(new GridPoint2(1, 0),0f);

    public GridPoint2 vector = new GridPoint2(0, 0);
    public float rotation;


    Direction(GridPoint2 vector, float rotation) {
        this.vector = vector;
        this.rotation = rotation;
    }
}
