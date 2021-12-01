package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.driver.Driver;
import ru.mipt.bit.platformer.objects.ReaderFromFile;
import ru.mipt.bit.platformer.objects.ObjectsGenerator;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private TiledMap level;
    private LevelRenderer levelRenderer;

    private Tank playerTank;
    private ArrayList<Tank> tanks;
    private ArrayList<Tree> trees;

    private Driver driver;

    private void generateRandomLevel() {
        ObjectsGenerator objectsGenerator = new ObjectsGenerator();
        playerTank = objectsGenerator.generatePlayer();
        trees = objectsGenerator.generateObstacles(10);
        tanks = objectsGenerator.generateTanks(3);
    }

    private void getLevelFromFile() {
        ReaderFromFile readerFromFile = new ReaderFromFile();
        readerFromFile.getGameObjectsFromFile("src\\main\\resources\\startingSettings\\level.txt");
        playerTank = readerFromFile.getPlayer();
        tanks = readerFromFile.getTanks();
        trees = readerFromFile.getTrees();
    }

    @Override
    public void create() {
        //generateRandomLevel();
        getLevelFromFile();

        level = new TmxMapLoader().load("level.tmx");
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        levelRenderer = new LevelRenderer(level, groundLayer, playerTank, trees, tanks);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        driver = new Driver(playerTank, trees, tanks, new NotRecommendingAI());

        levelRenderer.moveRectangleAtTileCenter();
    }

    @Override
    public void render() {
        driver.moveAll();
        levelRenderer.render();
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
        level.dispose();
        levelRenderer.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}