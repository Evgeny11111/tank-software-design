package ru.mipt.bit.platformer.AI;

import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import ru.mipt.bit.platformer.objects.move.Command;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

import java.util.ArrayList;
import java.util.List;

public class TankAIController {
    private final AI ai;
    private final GameState gameState;
    private final ArrayList<Command> tanksCommands;

    public TankAIController(AI ai, Tank playerTank, ArrayList<Tree> trees, ArrayList<Tank> tanks, int width, int height) {
        this.ai = ai;
        Creator creator = new Creator(playerTank, trees, tanks);
        this.gameState = creator.createGameState(width, height);
        this.tanksCommands = new ArrayList<>();
    }

    public void makeCommands() {
        recommendCommands();
        for (Command command : tanksCommands) {
            command.make();
        }
    }

    public void recommendCommands() {
        List<Recommendation> recommendations = ai.recommend(gameState);
        RecommendCommand recommendCommand = new RecommendCommand();
        for (Recommendation rec : recommendations) {
            tanksCommands.add(recommendCommand.getCommand(rec));
        }
    }

}