package ru.mipt.bit.platformer.driver;

import com.badlogic.gdx.Gdx;
import org.awesome.ai.AI;
import ru.mipt.bit.platformer.LevelRenderer;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Event;
import ru.mipt.bit.platformer.Observer;

import java.util.List;

/**
 * Adapter
 */
public class DriverByGame implements Observer {

    private final List<Tank> tanks;
    private final GeneratorCommands generatorCommands;
    private final MakerCommands makerCommands;

    public DriverByGame(Level level, AI ai, LevelRenderer levelRenderer) {
        this.tanks = level.getTanks();
        generatorCommands = new GeneratorCommands(level,levelRenderer);
        makerCommands = new MakerCommands(generatorCommands.getCommands());
    }

    public GeneratorCommands getGeneratorCommands() {
        return generatorCommands;
    }

    public MakerCommands getMakerCommands() {
        return makerCommands;
    }

    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    @Override
    public void update(Event event, Object gameObject) {
        if (event == Event.RemoveTank) {
            tanks.remove((Tank) gameObject);
        }
    }
}
