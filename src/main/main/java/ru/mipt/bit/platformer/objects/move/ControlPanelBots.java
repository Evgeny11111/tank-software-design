package ru.mipt.bit.platformer.objects.move;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;



public class ControlPanelBots {

    public static Movement chooseRandomlyDirection(Input inputKey){
        int random = (int) (Math.random() * 4);
        switch (random){
            case 0:
                return new Movement(new GridPoint2(Direction.UP.vector), Direction.UP.rotation);
            case 1:
                return new Movement(new GridPoint2(Direction.LEFT.vector), Direction.LEFT.rotation);
            case 2:
                return new Movement(new GridPoint2(Direction.DOWN.vector), Direction.DOWN.rotation);
            case 3:
                return new Movement(new GridPoint2(Direction.RIGHT.vector), Direction.RIGHT.rotation);
        }
        return new Movement();
    }

}
