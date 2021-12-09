package ru.mipt.bit.platformer.objects.move;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.objects.Tank;

import static com.badlogic.gdx.Input.Keys.*;

public class ControlForPlayer {

    public Command processKey(Tank tank) {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return new UpCommand(tank);
        }
        else if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return new LeftCommand(tank);
        }
        else if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return new DownCommand(tank);
        }
        else if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return new RightCommand(tank);
        }
        return new NoCommand(tank);
    }
}
