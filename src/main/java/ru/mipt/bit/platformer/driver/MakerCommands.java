package ru.mipt.bit.platformer.driver;

import ru.mipt.bit.platformer.control.commands.Command;

import java.util.List;

public class MakerCommands {
    private final List<Command> commands;

    public MakerCommands(List<Command> commands) {
        this.commands = commands;
    }

    public void make() {
        for (Command command : commands) {
            command.make();
        }
        commands.clear();
    }
}
