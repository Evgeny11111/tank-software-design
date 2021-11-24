package ru.mipt.bit.platformer.driver;

import com.badlogic.gdx.Gdx;
import org.awesome.ai.AI;
import ru.mipt.bit.platformer.AI.TankAIController;
import ru.mipt.bit.platformer.objects.move.ControlForPlayer;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

import java.util.ArrayList;

public class Driver {

    private final Tank playerTank;
    private final ArrayList<Tank> tanks;

    private final ControlForPlayer controlForPlayer;

    private final TankAIController tankAIController;

    public Driver(Tank playerTank, ArrayList<Tree> trees, ArrayList<Tank> tanks, AI ai) {
        this.playerTank = playerTank;
        this.tanks = tanks;

        this.controlForPlayer = new ControlForPlayer();
        //ControlForBots controlForBots = new ControlForBots();

        int width = 10;
        int height = 8;
        this.tankAIController = new TankAIController(ai, playerTank, trees, tanks, width, height);
    }

    public void moveAll() {
        movePlayer();
        moveTanks();
    }

    public void movePlayer() {
        float deltaTime = getDeltaTime();
        controlForPlayer.processKey(playerTank).make();
        playerTank.changeMovementProgress(deltaTime);
        playerTank.reachDestination();
    }

    public void moveTanks() {
        float deltaTime = getDeltaTime();
        for (Tank tank : tanks) {
            tankAIController.makeCommands();
        }
        for (Tank tank : tanks) {
            tank.changeMovementProgress(deltaTime);
            tank.reachDestination();
        }
    }

    float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }
}
