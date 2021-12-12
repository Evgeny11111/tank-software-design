package ru.mipt.bit.platformer.AI;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.immovable.Obstacle;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

import java.util.ArrayList;

/**
 * Adapter
 */
public class Creator {
    private final Tank playerTank;
    private final ArrayList<Tree> trees;
    private final java.util.ArrayList<Tank> tanks;

    public Creator(Tank playerTank, ArrayList<Tree> trees, ArrayList<Tank> tanks) {
        this.playerTank = playerTank;
        this.trees = trees;
        this.tanks = tanks;
    }
    public GameState createGameState(int width, int height) {
        GameState.GameStateBuilder gameStateBuilder = new GameState.GameStateBuilder();
        return gameStateBuilder
                .obstacles(getObstacles())
                .bots(getBots())
                .player(getPlayer())
                .levelWidth(width)
                .levelHeight(height)
                .build();
    }
    Player getPlayer() {
        return createPlayer();
    }

    ArrayList<Bot> getBots() {
        ArrayList<Bot> bots = new ArrayList<>();
        for (Tank tank : tanks) {
            bots.add(createBot(tank));
        }
        return bots;
    }

    ArrayList<Obstacle> getObstacles() {
        ArrayList<Obstacle> obstacles = new ArrayList<>();
        for (Tree tree : trees) {
            obstacles.add(createObstacle(tree));
        }
        return obstacles;
    }

    public Player createPlayer() {
        return new Player.PlayerBuilder()
                .source(playerTank)
                .x(playerTank.getCoordinates().x)
                .y(playerTank.getCoordinates().y)
                .destX(playerTank.getDestinationCoordinates().x)
                .destY(playerTank.getDestinationCoordinates().y)
                .orientation(createOrientation(playerTank.getCoordinates(), playerTank.getDestinationCoordinates()))
                .build();
    }
    public Obstacle createObstacle(Tree tree) {
        return new Obstacle(tree.getCoordinates().x, tree.getCoordinates().y);
    }
    public Bot createBot(Tank tank) {
        return new Bot.BotBuilder()
                .source(tank)
                .x(tank.getCoordinates().x)
                .y(tank.getCoordinates().y)
                .destX(tank.getDestinationCoordinates().x)
                .destY(tank.getDestinationCoordinates().y)
                .orientation(createOrientation(tank.getCoordinates(), tank.getDestinationCoordinates()))
                .build();
    }
    public Orientation createOrientation(GridPoint2 coordinates, GridPoint2 destinationCoordinates) {
        int deltaX = destinationCoordinates.x - coordinates.x;
        int deltaY = destinationCoordinates.y - coordinates.y;
        if (deltaX > 0) {
            return Orientation.E;
        } else if (deltaX < 0) {
            return Orientation.W;
        } else if (deltaY > 0) {
            return Orientation.N;
        } else {
            return Orientation.S;
        }
    }
}
