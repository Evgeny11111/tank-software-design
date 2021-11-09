package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.ObjectsGenerator;
import ru.mipt.bit.platformer.objects.ReaderFromFile;
import ru.mipt.bit.platformer.objects.move.Movement;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

import java.util.ArrayList;
import java.util.List;


public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;

    private LevelRenderer levelRenderer;
    private Tank playerTank;
    private ArrayList<Tank> botTanks;
    private ArrayList<Tree> trees;

    private int width;
    private int hight;
    private void generateRandomLevel() {
        ObjectsGenerator objectsGenerator = new ObjectsGenerator();
        objectsGenerator.generateRandomCoordinates(3,10);
        width = objectsGenerator.getWidth();
        hight = objectsGenerator.getHight();
        playerTank = new Tank(objectsGenerator.getTankCoordinates().get(0));
        botTanks = new ArrayList<>();
        for (int i = 1; i < objectsGenerator.getTankCoordinates().size(); i++){
            botTanks.add(new Tank(objectsGenerator.getTankCoordinates().get(i)));
        }
        trees = new ArrayList<>();
        for (int i = 0; i < objectsGenerator.getTreeCoordinates().size(); i++){
            trees.add(new Tree(objectsGenerator.getTreeCoordinates().get(i)));
        }
    }

    private void getLevelFromFile() {
        ReaderFromFile readerFromFile = new ReaderFromFile();
        readerFromFile.generateLevelFromFile("src/main/resources/startingSettings/level.txt");
        width = readerFromFile.getWidth();
        hight = readerFromFile.getHight();
        playerTank = new Tank(readerFromFile.getTankCoordinates().get(0));
        botTanks = new ArrayList<>();
        for (int i = 1; i < readerFromFile.getTankCoordinates().size(); i++){
            botTanks.add(new Tank(readerFromFile.getTankCoordinates().get(i)));
        }
        trees = new ArrayList<>();
        for (int i = 0; i < readerFromFile.getTreeCoordinates().size(); i++){
            trees.add(new Tree(readerFromFile.getTreeCoordinates().get(i)));
        }
    }

    @Override
    public void create() {
        getLevelFromFile();

        batch = new SpriteBatch();

        levelRenderer = new LevelRenderer(new TmxMapLoader().load("level.tmx"), batch,playerTank,botTanks,trees);

        levelRenderer.placeObstacles();
    }

    @Override
    public void render() {
        // clear the screen
        levelRenderer.clearScreen();
        List<Boolean> checks = new ArrayList<>();
        //playerTank
        levelRenderer.updateNextMove(playerTank,true);
        checks.add(playerTank.checkAllCollisions(trees, playerTank,botTanks, width, hight));
        if (!checks.get(0)){
            playerTank.setNextMove(new Movement(new GridPoint2(0, 0), playerTank.getRotation()));
        }
        playerTank.move();
        playerTank.updateCoordinates();
        //botTanks
        for (Tank botTank : botTanks) {
            levelRenderer.updateNextMove(botTank, false);
        }

        for (int i = 0; i < botTanks.size(); i++) {
            checks.add(botTanks.get(i).checkAllCollisions(trees, playerTank,botTanks, width, hight));
        }

        for (int i = 1; i < checks.size(); i++) {
            if (!checks.get(i)){
                botTanks.get(i-1).setNextMove(new Movement(new GridPoint2(0, 0), botTanks.get(i-1).getRotation()));
            }
            botTanks.get(i-1).move();
        }
        for (Tank botTank : botTanks) {
            botTank.updateCoordinates();
        }

        // calculate interpolated player screen coordinates
        levelRenderer.updatePlayerPlacement();


        // render each tile of the level
        levelRenderer.render();

        levelRenderer.draw(batch);
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
        levelRenderer.dispose();
        batch.dispose();
    }


    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
