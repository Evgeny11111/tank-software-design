package ru.mipt.bit.platformer.driver;

import ru.mipt.bit.platformer.LevelRenderer;
import ru.mipt.bit.platformer.control.ControlBots;
import ru.mipt.bit.platformer.control.ControlPlayer;
import ru.mipt.bit.platformer.control.Controller;
import ru.mipt.bit.platformer.control.commands.Command;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.List;

public class GeneratorCommands {

    private final Tank playerTank;
    private final List<Tank> tanks;
    private final Controller controlForPlayer;
    private final Controller controlForBots;
    private final Level level;
    private final List<Command> commands;

    public GeneratorCommands(Level level, LevelRenderer levelRenderer) {
        this.playerTank = level.getPlayerTank();
        this.tanks = level.getTanks();
        this.controlForPlayer = new ControlPlayer();
        ((ControlPlayer)this.controlForPlayer).observe(levelRenderer);
        this.controlForBots = new ControlBots();
        this.level = level;
        this.commands = new ArrayList<>();
    }

    public void generate() {
        generateCommandsPlayer();
        generateCommandsBots();
    }

    private void generateCommandsPlayer() {
        ArrayList<Tank> playerList = new ArrayList<>();
        playerList.add(playerTank);
        commands.addAll(controlForPlayer.getCommands(playerList, level));
    }
    private void generateCommandsBots() {
        commands.addAll(controlForBots.getCommands(tanks, level));
    }

    public List<Command> getCommands() {
        return commands;
    }
}
