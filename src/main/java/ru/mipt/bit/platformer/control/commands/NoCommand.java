package ru.mipt.bit.platformer.control.commands;

import ru.mipt.bit.platformer.objects.Tank;

public class NoCommand implements Command {
    private final Tank tank;

    public NoCommand(Tank tank) {
        this.tank = tank;
    }


    @Override
    public void make() {

    }
}