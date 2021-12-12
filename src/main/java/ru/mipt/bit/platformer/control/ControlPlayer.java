package ru.mipt.bit.platformer.control;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.Issuer;
import ru.mipt.bit.platformer.Observer;
import ru.mipt.bit.platformer.driver.Level;
import ru.mipt.bit.platformer.objects.Event;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.control.commands.*;

import java.util.ArrayList;
import java.util.Date;

import static com.badlogic.gdx.Input.Keys.*;

/**
 * Use case
 */
public class ControlPlayer implements Issuer {


    private final ArrayList<Observer> observers = new ArrayList<>();
    private long lastTimeChanged =  new Date().getTime();

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
            return new ShootCommand(tank, level);
        } else if (Gdx.input.isKeyPressed(L)) {
            long time = new Date().getTime();
            if (time - lastTimeChanged > 300) {
                notifySubs(Event.ChangeHealth, null);
                lastTimeChanged = time;
            }
        }
        return new NoCommand(tank);
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifySubs(Event event, Object objectByGame) {
        for (Observer obs : observers)
            obs.update(event, objectByGame);
    }
}
