package ru.mipt.bit.platformer.objects.control;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.driver.Level;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.control.commands.*;

import static com.badlogic.gdx.Input.Keys.*;

public class ControlForPlayer {

    public Command processKey(Tank tank, Level level) {
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
        else if (Gdx.input.isKeyPressed(SPACE)) {
            return new ShootCommand(tank,level);
        }
        return new NoCommand(tank);
    }
}
