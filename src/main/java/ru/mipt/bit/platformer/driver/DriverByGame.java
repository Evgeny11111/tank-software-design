package ru.mipt.bit.platformer.driver;

import com.badlogic.gdx.Gdx;
import org.awesome.ai.AI;
import ru.mipt.bit.platformer.AI.AIController;
import ru.mipt.bit.platformer.LevelRenderer;
import ru.mipt.bit.platformer.control.Controller;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.control.commands.Command;
import ru.mipt.bit.platformer.control.ControlBots;
import ru.mipt.bit.platformer.control.ControlPlayer;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.objects.Event;
import ru.mipt.bit.platformer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter
 */
public class DriverByGame implements Observer {

    private final Tank playerTank;
    private final List<Tank> tanks;
    private final List<Tree> trees;

    private final Controller controlForPlayer;
    private final Controller controlForBots;
    private final Level level;

    private final List<Command> commands;

    public DriverByGame(Level level, AI ai, LevelRenderer levelRenderer) {
        this.playerTank = level.getPlayerTank();
        this.trees = level.getTreeObstacles();
        this.tanks = level.getTanks();

        this.controlForPlayer = new ControlPlayer();
        ((ControlPlayer)this.controlForPlayer).observe(levelRenderer);
        this.controlForBots = new ControlBots();

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
        ArrayList<Tank> playerList = new ArrayList<>();
        playerList.add(playerTank);
        commands.addAll(controlForPlayer.getCommands(playerList, level));
    }

    public void generateCommandsBots() {
        commands.addAll(controlForBots.getCommands(tanks, level));
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
