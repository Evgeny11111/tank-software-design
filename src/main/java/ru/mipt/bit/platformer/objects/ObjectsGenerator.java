package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.driver.CollisionChecker;
import ru.mipt.bit.platformer.driver.ComparatorGridPoint2;

import java.util.concurrent.ThreadLocalRandom;

import java.util.*;

public class ObjectsGenerator {
    private final ArrayList<Tree> obstacles;
    private final ArrayList<Tank> tanks;
    private final int width = 10;
    private final int height = 8;
    private final TreeSet<GridPoint2> points;

    private final CollisionChecker collisionChecker;

    public ObjectsGenerator() {
        obstacles = new ArrayList<>();
        tanks = new ArrayList<>();
        points = new TreeSet<>(new ComparatorGridPoint2());

        this.collisionChecker = new CollisionChecker();
    }

    public Tank generatePlayer() {
        int randomWidth = generateNumber(0, width);
        int randomHeight = generateNumber(0, height);
        GridPoint2 coords = new GridPoint2(randomWidth, randomHeight);
        while (points.contains(coords)) {
            randomWidth = generateNumber(0, width);
            randomHeight = generateNumber(0, height);
            coords = new GridPoint2(randomWidth, randomHeight);
        }
        points.add(coords);
        Tank tank = new Tank(coords, collisionChecker);
        collisionChecker.addMovable(tank);
        return tank;
    }

    public ArrayList<Tree> generateObstacles(int obstaclesNumber) {
        int randomWidth = generateNumber(0, width);
        int randomHeight = generateNumber(0, height);
        GridPoint2 coords = new GridPoint2(randomWidth, randomHeight);
        for (int i = 0; i < obstaclesNumber; ++i) {
            while (points.contains(coords)) {
                randomWidth = generateNumber(0, width);
                randomHeight = generateNumber(0, height);
                coords = new GridPoint2(randomWidth, randomHeight);
            }
            points.add(coords);
            Tree tree = new Tree(coords);
            collisionChecker.addImmovable(tree);
            obstacles.add(tree);
        }
        return obstacles;
    }

    public ArrayList<Tank> generateTanks(int tanksNumber) {
        int randomWidth = generateNumber(0, width);
        int randomHeight = generateNumber(0, height);
        GridPoint2 coords = new GridPoint2(randomWidth, randomHeight);
        for (int i = 0; i < tanksNumber; ++i) {
            while (points.contains(coords)) {
                randomWidth = generateNumber(0, width);
                randomHeight = generateNumber(0, height);
                coords = new GridPoint2(randomWidth, randomHeight);
            }
            points.add(coords);
            Tank tank = new Tank(coords, collisionChecker);
            collisionChecker.addMovable(tank);
            tanks.add(tank);
        }
        return tanks;
    }

    public int generateNumber(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to);
    }
}