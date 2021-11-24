package ru.mipt.bit.platformer.driver;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

import java.util.ArrayList;

public class CollisionChecker {

    private final ArrayList<Tank> movableGameObjects;
    private final ArrayList<Tree> immovableGameObjects;

    public CollisionChecker() {
        this.movableGameObjects = new ArrayList<>();
        this.immovableGameObjects = new ArrayList<>();
    }

    public void addMovable(Tank tank) {
        movableGameObjects.add(tank);
    }

    public void addImmovable(Tree tree) {
        immovableGameObjects.add(tree);
    }

    public boolean checkCollisions(GridPoint2 newPosition, Tank tankToMove) {
        return checkAllMovables(newPosition, tankToMove) && checkAllImmovables(newPosition, tankToMove) && checkBounds(newPosition);
    }

    public boolean checkAllMovables(GridPoint2 newPosition, Tank tankToMove) {
        for (var tank : movableGameObjects) {
            if (tank.equals(tankToMove)) {
                continue;
            }
            if (!tankToMove.isMovementPossible(tank.getCoordinates(), newPosition) || !tankToMove.isMovementPossible(tank.getDestinationCoordinates(), newPosition))
                return false;
        }
        return true;
    }

    public boolean checkAllImmovables(GridPoint2 newPosition, Tank tankToMove) {
        for (var tree : immovableGameObjects) {
            if (!tankToMove.isMovementPossible(tree.getCoordinates(), newPosition))
                return false;
        }
        return true;
    }

    boolean checkBounds(GridPoint2 newPosition) {
        int x = newPosition.x, y = newPosition.y;
        int width = 10;
        int height = 8;
        return (x >= 0 && x < width && y >= 0 && y < height);
    }
}