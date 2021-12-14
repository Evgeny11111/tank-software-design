package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.driver.CollisionChecker;
import ru.mipt.bit.platformer.driver.Level;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Use case
 */
public class ReaderFromFile implements LevelGenerator {
    private final int width = 10;
    private final int height = 8;
    private Tank playerTank;
    private final List<Tank> tanks;
    private final List<Tree> trees;
    private final String filePath;

    private final CollisionChecker collisionChecker;

    public ReaderFromFile(String filePath) {
        this.filePath = filePath;
        tanks = new ArrayList<>();
        trees = new ArrayList<>();
        collisionChecker = new CollisionChecker();
    }

    private String readFromFileToString() {
        try {
            Path fileName = Path.of(filePath);
            return Files.readString(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public Tank getPlayer() {
        return playerTank;
    }

    public List<Tank> getTanks() {
        return tanks;
    }

    public List<Tree> getTrees() {
        return trees;
    }

    private void getGameObjectsFromFile() {
        String fileData = readFromFileToString();
        getObjectsFromString(fileData);
    }

    private void getObjectsFromString(String fileContent) {
        // предполагается, что в файле нет лишних клеток
        int i = 0;
        char symbol;
        int n = fileContent.length();
        int x = 0, y = height - 1;
        GridPoint2 coords;
        while (i < n) {
            symbol = fileContent.charAt(i);
            if (symbol == '_') {
                x += 1;
            } else if (symbol == 'T') {
                coords = new GridPoint2(x, y);
                Tree tree = new Tree(coords);
                collisionChecker.addTree(tree);
                trees.add(tree);
                x += 1;
            } else if (symbol == 'X') {
                coords = new GridPoint2(x, y);
                playerTank = new Tank(coords, collisionChecker);
                collisionChecker.addTank(playerTank);
                x += 1;
            } else if (symbol == 'N') {
                coords = new GridPoint2(x, y);
                Tank tank = new Tank(coords, collisionChecker);
                collisionChecker.addTank(tank);
                tanks.add(tank);
                x += 1;
            } else if (symbol == '\n') {
                y -= 1;
                x = 0;
            }
            i += 1;
        }
    }

    @Override
    public Level generateLevel() {
        getGameObjectsFromFile();
        return new Level(playerTank, trees, tanks);
    }
}