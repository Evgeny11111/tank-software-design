package ru.mipt.bit.platformer.AI;

import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.objects.control.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class AIController {
    private final AI ai;
    private GameState gameState;
    private final ArrayList<Command> tanksCommands;

    private final Tank playerTank;
    private final ArrayList<Tank> tanks;
    private final ArrayList<Tree> trees;

    private final int width;
    private final int height;

    public AIController(AI ai, Tank playerTank, ArrayList<Tank> tanks, ArrayList<Tree> trees, int levelWidth, int levelHeight) {
        this.ai = ai;
        this.playerTank = playerTank;
        this.trees = trees;
        this.tanks = tanks;
        this.width = levelWidth;
        this.height = levelHeight;
        Creator creator = new Creator(playerTank,trees,tanks);
        this.gameState = creator.createGameState(width, height);
        this.tanksCommands = new ArrayList<>();
    }

    public ArrayList<Command> getCommands() {
        recommendCommands();
        return tanksCommands;
    }

    public void executeCommands() {
        recommendCommands();
        for (Command command : tanksCommands) {
            command.make();
        }
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