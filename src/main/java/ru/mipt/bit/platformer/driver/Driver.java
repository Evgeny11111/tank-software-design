package ru.mipt.bit.platformer.driver;

import com.badlogic.gdx.Gdx;
import org.awesome.ai.AI;
import ru.mipt.bit.platformer.AI.AIController;
import ru.mipt.bit.platformer.LevelRenderer;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.ObjectByGame;
import ru.mipt.bit.platformer.control.commands.Command;
import ru.mipt.bit.platformer.control.ControlBots;
import ru.mipt.bit.platformer.control.ControlPlayer;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.objects.Event;
import ru.mipt.bit.platformer.Observer;

import java.util.ArrayList;

/**
 * Adapter
 */
public class Driver implements Observer {
    private final int width = 10;
    private final int height = 8;

    private final Tank playerTank;
    private final ArrayList<Tank> tanks;
    private final ArrayList<Tree> trees;

    private final ControlPlayer controlForPlayer;
    private final ControlBots controlBots;
    private final AIController AIController;
    private final Level level;

    private final ArrayList<Command> commands;

    public Driver(Tank playerTank, ArrayList<Tree> trees, ArrayList<Tank> tanks, ArrayList<Bullet> bullets, Level level, AI ai, LevelRenderer levelRenderer) {
        this.playerTank = playerTank;
        this.trees = trees;
        this.tanks = tanks;

        this.controlForPlayer = new ControlPlayer();
        this.controlForPlayer.subscribe(levelRenderer);
        this.controlBots = new ControlBots();
        this.AIController = new AIController(ai, playerTank, tanks, trees, width, height);

        this.commands = new ArrayList<>();
        this.level = level;
    }

    public void generateCommands() {
        generateCommandsPlayer();
        generateCommandsBots();
    }

    public void executeCommands() {
        for (Command command : commands) {
            command.make();
        }
        commands.clear();
    }

    public void generateCommandsPlayer() {
        commands.add(controlForPlayer.processKey(playerTank, level));
    }

    public void generateCommandsBots() {
        for (Tank tank : tanks) {
            commands.add(controlBots.getRandomCommand(tank, level));
        }
    }

    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    @Override
    public void update(Event event, ObjectByGame objectByGame) {
        if (event == Event.RemoveTank) {
            tanks.remove((Tank) objectByGame);
        }
    }
}
