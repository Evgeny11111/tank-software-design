package ru.mipt.bit.platformer.objects.control.commands;

import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.control.commands.Command;

public class NoCommand implements Command {
    private final Tank tank;

    public NoCommand(Tank tank) {
        this.tank = tank;
    }


    @Override
    public void make() {

    }
}