package ru.mipt.bit.platformer.objects.move;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class ControlPanelPlayer {
    public static Movement chooseKeyToDirection(Input inputKey){
        if (inputKey.isKeyPressed(UP) || inputKey.isKeyPressed(W)) {
            return new Movement(new GridPoint2(Direction.UP.vector), Direction.UP.rotation);
        }
        if (inputKey.isKeyPressed(LEFT) || inputKey.isKeyPressed(A)) {
            return new Movement(new GridPoint2(Direction.LEFT.vector), Direction.LEFT.rotation);
        }
        if (inputKey.isKeyPressed(DOWN) || inputKey.isKeyPressed(S)) {
            return new Movement(new GridPoint2(Direction.DOWN.vector), Direction.DOWN.rotation);
        }
        if (inputKey.isKeyPressed(RIGHT) || inputKey.isKeyPressed(D)) {
            return new Movement(new GridPoint2(Direction.RIGHT.vector), Direction.RIGHT.rotation);
        }
        return new Movement();
    }
    private static boolean isMovementKey(Input inputKey) {
        return inputKey.isKeyPressed(UP) ||
                inputKey.isKeyPressed(LEFT) ||
                inputKey.isKeyPressed(RIGHT) ||
                inputKey.isKeyPressed(DOWN) ||
                inputKey.isKeyPressed(W) ||
                inputKey.isKeyPressed(A) ||
                inputKey.isKeyPressed(S) ||
                inputKey.isKeyPressed(D);
    }
}
