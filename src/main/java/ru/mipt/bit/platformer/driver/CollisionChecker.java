package ru.mipt.bit.platformer.driver;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.ObjectByGame;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.objects.Event;
import ru.mipt.bit.platformer.Observer;


import java.util.ArrayList;


/**
 * Adapter
 */
public class CollisionChecker implements Observer {

    private final int width = 10;
    private final int height = 8;

    private final ArrayList<Tank> tanks;
    private final ArrayList<Tree> trees;
    private final ArrayList<Bullet> bullets;

    public CollisionChecker() {
        this.tanks = new ArrayList<>();
        this.trees = new ArrayList<>();
        this.bullets = new ArrayList<>();
    }

    public void addTank(Tank tank) {
        tanks.add(tank);
    }

    public void addTreeObstacle(Tree tree) {
        trees.add(tree);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public boolean checkCollisionsWithBullet(GridPoint2 newPosition, Bullet bullet) {
        if (!checkBounds(newPosition)) {
            bullet.setNotExistent();
            return false;
        }

        return checkAllBullets(newPosition, bullet) && checkAllTanksWithBullet(newPosition, bullet) && checkAllTreesWithBullet(newPosition, bullet);
    }

    public boolean checkAllBullets(GridPoint2 newPosition, Bullet bulletToMove) {
        for (var bullet : bullets) {
            if (bullet.equals(bulletToMove)) {
                continue;
            }
            if (!bulletToMove.isMovementPossible(bullet.getCoordinates(), newPosition) || !bulletToMove.isMovementPossible(bullet.getDestinationCoordinates(), newPosition)) {
                bullet.setNotExistent();
                bulletToMove.setNotExistent();
                return false;
            }
        }
        return true;
    }

    public boolean checkAllTanksWithBullet(GridPoint2 newPosition, Bullet bullet) {
        for (var tank : tanks) {
            if (!tank.equals(bullet.getTank()) && (!bullet.isMovementPossible(tank.getCoordinates(), newPosition) || !bullet.isMovementPossible(tank.getDestinationCoordinates(), newPosition)) ) {
                bullet.setNotExistent();
                tank.takeDamage(bullet);
                return false;
            }
        }
        return true;
    }

    public boolean checkAllTreesWithBullet(GridPoint2 newPosition, Bullet bullet) {
        for (Tree tree : trees) {
            if (!bullet.isMovementPossible(tree.getCoordinates(), newPosition)) {
                bullet.setNotExistent();
                return false;
            }
        }
        return true;
    }

    public boolean checkCollisionsWithTank(GridPoint2 newPosition, Tank tankToMove) {
        return checkAllTanksWithTank(newPosition, tankToMove) && checkAllTreesWithTank(newPosition, tankToMove) && checkBounds(newPosition);
    }

    public boolean checkAllTanksWithTank(GridPoint2 newPosition, Tank tankToMove) {
        for (var tank : tanks) {
            if (tank.equals(tankToMove)) {
                continue;
            }
            if (!tankToMove.isMovementPossible(tank.getCoordinates(), newPosition) || !tankToMove.isMovementPossible(tank.getDestinationCoordinates(), newPosition))
                return false;
        }
        return true;
    }

    public boolean checkAllTreesWithTank(GridPoint2 newPosition, Tank tankToMove) {
        for (Tree tree : trees) {
            if (!tankToMove.isMovementPossible(tree.getCoordinates(), newPosition))
                return false;
        }
        return true;
    }

    boolean checkBounds(GridPoint2 newPosition) {
        int x = newPosition.x, y = newPosition.y;
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    @Override
    public void update(Event event, ObjectByGame objectByGame) {
        switch(event) {
            case RemoveTank:
                tanks.remove((Tank) objectByGame);
                break;
            case RemoveBullet:
                bullets.remove((Bullet) objectByGame);
                break;
            case AddBullet:
                bullets.add((Bullet) objectByGame);
                break;
        }
    }
}