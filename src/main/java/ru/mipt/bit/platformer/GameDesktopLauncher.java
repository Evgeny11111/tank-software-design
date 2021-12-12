package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.driver.DriverByGame;
import ru.mipt.bit.platformer.driver.Level;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.generators.ReaderFromFile;
import ru.mipt.bit.platformer.generators.ObjectsGenerator;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

/**
 * Adapter
 */
public class GameDesktopLauncher implements ApplicationListener {

    private TiledMap levelTiledMap;
    private TileMovement tileMovement;
    private LevelRenderer levelRenderer;

    private Tank playerTank;
    private ArrayList<Tank> tanks;
    private ArrayList<Tree> trees;
    private ArrayList<Bullet> bullets;

    private DriverByGame driverByGame;
    private Level level;


    private void generateRandomLevel() {
        ObjectsGenerator obstaclesGenerator = new ObjectsGenerator(3, 10);
        level = obstaclesGenerator.generateLevel();
        playerTank = level.getPlayerTank();
        tanks = level.getTanks();
        trees = level.getTreeObstacles();
        bullets = level.getBullets();
    }

    private void getLevelFromFile() {
        ReaderFromFile readerFromFile = new ReaderFromFile();
        readerFromFile.getGameObjectsFromFile("src\\main\\resources\\startingSettings\\level.txt");
        level = readerFromFile.generateLevel();
        playerTank = level.getPlayerTank();
        tanks = level.getTanks();
        trees = level.getTreeObstacles();
        bullets = level.getBullets();
    }

    @Override
    public void create() {
        getLevelFromFile();

        levelTiledMap = new TmxMapLoader().load("level.tmx");
        TiledMapTileLayer groundLayer = getSingleLayer(levelTiledMap);
        levelRenderer = new LevelRenderer(levelTiledMap, groundLayer, playerTank, trees, tanks);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        driverByGame = new DriverByGame(playerTank, trees, tanks, bullets, level, new NotRecommendingAI(), levelRenderer);
        level.subscribe(driverByGame);
        level.subscribe(levelRenderer);
        level.subscribe(playerTank.getCollisionChecker());

        levelRenderer.moveRectangleAtTileCenter();
    }

    @Override
    public void render() {
        float deltaTime = driverByGame.getDeltaTime();
        driverByGame.generateCommands();
        driverByGame.executeCommands();
        level.updateObjects(deltaTime);
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
        levelTiledMap.dispose();
        levelRenderer.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}