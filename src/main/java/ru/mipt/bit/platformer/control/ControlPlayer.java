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
import java.util.List;

import static com.badlogic.gdx.Input.Keys.*;

/**
 * Use case
 */
public class ControlPlayer implements Issuer,Controller {


    private final List<Observer> observers = new ArrayList<>();
    private long lastChanged =  new Date().getTime();

    @Override
    public List<Command> getCommands(List<Tank> tanks, Level level) {
        Tank tank = tanks.get(0);
        ArrayList<Command> commands = new ArrayList<>();
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            commands.add(new UpCommand(tank));
        }
        else if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            commands.add(new LeftCommand(tank));
        }
        else if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            commands.add(new DownCommand(tank));
        }
        else if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            commands.add(new RightCommand(tank));
        }
        else if (Gdx.input.isKeyPressed(SPACE)) {
            commands.add(new ShootCommand(tank, level));
        } else if (Gdx.input.isKeyPressed(L)) {
            long time = new Date().getTime();
            if (time - lastChanged > 300) {
                notifySubs(Event.ChangeHealth, null);
                lastChanged = time;
            }
            commands.add(new NoCommand(tank));
        } else {
            commands.add(new NoCommand(tank));
        }
        return commands;
    }

    @Override
    public void observe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unobserve(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifySubs(Event event, Object object) {
        for (Observer sub : observers)
            sub.update(event, object);
    }
}
