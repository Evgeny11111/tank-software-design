package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.control.commands.Command;
import ru.mipt.bit.platformer.driver.Level;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.List;

public interface Controller {
    List<Command> getCommands(List<Tank> tank, Level level);
}
