package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.ObjectsCoordinates;
import ru.mipt.bit.platformer.objects.graphics.Drawer;
import ru.mipt.bit.platformer.objects.move.Movement;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

import java.util.ArrayList;
import java.util.List;


public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;

    private Level levelLayer;
    private ArrayList<Tank> tanks;
    private ArrayList<Tree> trees;

    private int width;
    private int hight;

    @Override
    public void create() {
        batch = new SpriteBatch();

        levelLayer = new Level(new TmxMapLoader().load("level.tmx"), batch);

        ObjectsCoordinates levelGenerator = new ObjectsCoordinates();
        levelGenerator.generateLevelFromFile("src/main/resources/startingSettings/level.txt");
//        levelGenerator.generateRandomCoordinates(5);

        width = levelGenerator.getWidth();
        hight = levelGenerator.getHight();

        tanks = new ArrayList<>();
        for (int i = 0; i < levelGenerator.getTankCoordinates().size(); i++){
            tanks.add(new Tank(new Texture("images/tank_blue.png"), levelGenerator.getTankCoordinates().get(i)));
        }
        trees = new ArrayList<>();
        for (int i = 0; i < levelGenerator.getTreeCoordinates().size(); i++){
            trees.add(new Tree(new Texture("images/greenTree.png"), levelGenerator.getTreeCoordinates().get(i)));
        }

        levelLayer.placeObstacles(trees);
    }

    @Override
    public void render() {
        // clear the screen
        Drawer.clearScreen();

        tanks.get(0).updateNextMove(true);
        for (int i = 1; i < tanks.size(); i++) {
            tanks.get(i).updateNextMove(false);
        }

        List<Boolean> checks = new ArrayList<>();
        for (Tank tank : tanks) {
            checks.add(tank.checkAllCollisions(trees, tanks, width, hight));
        }
        for (int i = 0; i < checks.size(); i++) {
            if (!checks.get(i)){
                tanks.get(i).setNextMove(new Movement(new GridPoint2(0, 0), tanks.get(i).getRotation()));
            }
            tanks.get(i).move();
        }
        for (Tank tank : tanks) {
            tank.updateCoordinates();
        }

        // calculate interpolated player screen coordinates
        for (Tank tank : tanks) {
            levelLayer.updatePlayerPlacement(tank);
        }

        // render each tile of the level
        levelLayer.render();

        Drawer.draw(batch, tanks, trees);
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        for (Tree tree : trees) {
            tree.getGraphicsTree().dispose();
        }
        for (Tank tank : tanks) {
            tank.dispose();
        }

        levelLayer.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
