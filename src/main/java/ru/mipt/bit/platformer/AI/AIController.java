package ru.mipt.bit.platformer.AI;

import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import ru.mipt.bit.platformer.control.Controller;
import ru.mipt.bit.platformer.driver.Level;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.control.commands.Command;
import ru.mipt.bit.platformer.objects.state.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Use case
 */
public class AIController implements Controller {

    private final AI ai;
    private GameState gameState;
    private final List<Command> tanksCommands;

    private final Tank playerTank;
    private final List<Tank> tanks;
    private final List<Tree> trees;

    private final int width;
    private final int height;

    public AIController(AI ai, Tank playerTank, List<Tree> trees, List<Tank> tanks, int width, int height) {
        this.ai = ai;
        this.playerTank = playerTank;
        this.trees = trees;
        this.tanks = tanks;
        this.width = width;
        this.height = height;
        Creator creator = new Creator(playerTank,trees,tanks);
        this.gameState = creator.createGameState(width, height);
        this.tanksCommands = new ArrayList<>();
    }

    @Override
    public List<Command> getCommands(List<Tank> tank, Level level) {
        recommendCommands();
        return tanksCommands;
    }

    public void createGameState() {
        Creator creator = new Creator(playerTank,trees,tanks);
        this.gameState = creator.createGameState(width, height);
    }

    public void recommendCommands() {
        createGameState();
        List<Recommendation> recommendations = ai.recommend(gameState);
        RecommendCommand recommendCommand = new RecommendCommand();
        for (Recommendation rec : recommendations) {
            tanksCommands.add(recommendCommand.getCommand(rec));
        }
    }

}