package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.mipt.bit.platformer.objects.GraphicsObjects;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.player.GraphicsPlayer;
import ru.mipt.bit.platformer.player.Player;

import java.util.ArrayList;

public class Creater {

    private static Batch batch;

    private static ArrayList<Player> players;

    private static ArrayList<Tree> trees;

    private static Drawer drawer;



    void doCreate() {
        batch = new SpriteBatch();

        ObjectsCoordinate objectsCoordinate = new ObjectsCoordinate();

        players = new ArrayList<>();

        trees = new ArrayList<>();

        //objectsCoordinate.generateFromFile("C:/Users/Evgeny/Desktop/hometask POO/tank-software-design-hometask-Last/src/main/java/ru/mipt/bit/platformer");
        objectsCoordinate.generatePlayers(10);

        objectsCoordinate.generateTrees(10);

        for (int i = 0; i < objectsCoordinate.getArrayPlayers().size(); i++) {
            players.add(new Player(objectsCoordinate.getArrayPlayers().get(i)));
        }

        Level level = new Level();

        GraphicsPlayer graphicsPlayer = new GraphicsPlayer();

        for (int i = 0; i < objectsCoordinate.getArrayTrees().size(); i++) {
            trees.add(new Tree(GraphicsObjects.getObstacle(),objectsCoordinate.getArrayTrees().get(i)));
        }

        drawer = new Drawer(trees, players.get(0));

        level.placeObstacles(trees);
    }
    public static Player getPlayer() {
        return players.get(0);
    }
    public static Batch getBatch() {
        return batch;
    }
    public static ArrayList<Tree> getTrees() {
        return trees;
    }
    public static Drawer getDrawer() {
        return drawer;
    }
}
