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
import ru.mipt.bit.platformer.generators.LevelGenerator;
import ru.mipt.bit.platformer.generators.ReaderFromFile;
import ru.mipt.bit.platformer.util.TileMovement;


import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

/**
 * Adapter
 */
public class GameDesktopLauncher implements ApplicationListener {

    private TiledMap levelTiledMap;
    private TileMovement tileMovement;
    private LevelRenderer levelRenderer;

    private DriverByGame driverByGame;

    private Level level;

    @Override
    public void create() {
        LevelGenerator readerFromFile = new ReaderFromFile("src\\main\\resources\\startingSettings\\level.txt");
        level = readerFromFile.generateLevel();

        levelTiledMap = new TmxMapLoader().load("level.tmx");
        TiledMapTileLayer groundLayer = getSingleLayer(levelTiledMap);
        levelRenderer = new LevelRenderer(levelTiledMap, groundLayer, level.getPlayerTank(), level.getTreeObstacles(), level.getTanks());
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        driverByGame = new DriverByGame(level, new NotRecommendingAI(), levelRenderer);
        level.observe(driverByGame);
        level.observe(levelRenderer);
        level.observe(level.getPlayerTank().getCollisionChecker());

        levelRenderer.moveRectangleAtTileCenter();
    }

    @Override
    public void render() {
        float deltaTime = driverByGame.getDeltaTime();
        driverByGame.getGeneratorCommands().generate();
        driverByGame.getMakerCommands().make();
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