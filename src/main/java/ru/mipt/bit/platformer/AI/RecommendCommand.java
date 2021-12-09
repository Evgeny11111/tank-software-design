package ru.mipt.bit.platformer.AI;

import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.control.commands.*;

public class RecommendCommand {
    Command getCommand(Recommendation recommendation) {
        Tank actor = (Tank) recommendation.getActor().getSource();
        Action action = recommendation.getAction();
        return switch (action) {
            case MoveEast -> new RightCommand(actor);
            case MoveWest -> new LeftCommand(actor);
            case MoveNorth -> new UpCommand(actor);
            default -> new DownCommand(actor);
        };
    }
}
