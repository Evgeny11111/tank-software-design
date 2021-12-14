package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.driver.CollisionChecker;
import ru.mipt.bit.platformer.driver.Level;
import ru.mipt.bit.platformer.objects.GridPoint2Comparator;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

import java.util.concurrent.ThreadLocalRandom;

import java.util.*;

/**
 * Use case
 */
public class ObjectsGenerator implements LevelGenerator {
    private final List<Tree> trees;
    private final List<Tank> tanks;
    private final int width = 10;
    private final int height = 8;
    private final TreeSet<GridPoint2> takenPoints;

    private final CollisionChecker collisionChecker;

    private final int tanksNumber;
    private final int treesNumber;

    public ObjectsGenerator(int tanksNumber, int obstaclesNumber) {
        trees = new ArrayList<>();
        tanks = new ArrayList<>();
        takenPoints = new TreeSet<>(new GridPoint2Comparator());

        this.collisionChecker = new CollisionChecker();

        this.tanksNumber = tanksNumber;
        this.treesNumber = obstaclesNumber;
    }

    private Tank generatePlayer() {
        int randomWidth = generateNumber(0, width);
        int randomHeight = generateNumber(0, height);
        GridPoint2 coords = new GridPoint2(randomWidth, randomHeight);
        while (takenPoints.contains(coords)) {
            randomWidth = generateNumber(0, width);
            randomHeight = generateNumber(0, height);
            coords = new GridPoint2(randomWidth, randomHeight);
        }
        takenPoints.add(coords);
        Tank playerTank = new Tank(coords, collisionChecker);
        collisionChecker.addTank(playerTank);
        return playerTank;
    }

    private List<Tree> generateTrees(int treesNumber) {
        int randomWidth = generateNumber(0, width);
        int randomHeight = generateNumber(0, height);
        GridPoint2 coords = new GridPoint2(randomWidth, randomHeight);
        for (int i = 0; i < treesNumber; ++i) {
            while (takenPoints.contains(coords)) {
                randomWidth = generateNumber(0, width);
                randomHeight = generateNumber(0, height);
                coords = new GridPoint2(randomWidth, randomHeight);
            }
            takenPoints.add(coords);
            Tree tree = new Tree(coords);
            collisionChecker.addTree(tree);
            trees.add(tree);
        }
        return trees;
    }

    private List<Tank> generateTanks(int tanksNumber) {
        int randomWidth = generateNumber(0, width);
        int randomHeight = generateNumber(0, height);
        GridPoint2 coords = new GridPoint2(randomWidth, randomHeight);
        for (int i = 0; i < tanksNumber; ++i) {
            while (takenPoints.contains(coords)) {
                randomWidth = generateNumber(0, width);
                randomHeight = generateNumber(0, height);
                coords = new GridPoint2(randomWidth, randomHeight);
            }
            takenPoints.add(coords);
            Tank tank = new Tank(coords, collisionChecker);
            collisionChecker.addTank(tank);
            tanks.add(tank);
        }
        return tanks;
    }

    private int generateNumber(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to);
    }

    @Override
    public Level generateLevel() {
        return new Level(generatePlayer(), generateTrees(treesNumber), generateTanks(tanksNumber));
    }
}