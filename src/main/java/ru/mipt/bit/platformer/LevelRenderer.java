package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.objects.*;
import ru.mipt.bit.platformer.objects.graphics.*;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.HashMap;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

/**
 * Adapter
 */
public class LevelRenderer implements Observer {
    private final Batch batch;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final TileMovement tileMovement;
    private final Texture blueTankTexture;
    private final Texture greenTreeTexture;
    private final Texture bulletTexture;
    private final Texture healthTexture;
    private final GraphicsObject tankPlayerGraphics;
    private final HashMap<Tank, GraphicsObject> tanksToGraphics;
    private final HashMap<Tree, GraphicsTree> treesToGraphics;
    private final HashMap<Bullet, GraphicsBullet> bulletsToGraphics;

    private final Tank playerTank;


    public LevelRenderer(TiledMap level, TiledMapTileLayer groundLayer, Tank playerTank, ArrayList<Tree> treeObstacles, ArrayList<Tank> tanks) {
        this.batch = new SpriteBatch();
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.groundLayer = groundLayer;
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        this.blueTankTexture = new Texture("images/tank_blue.png");
        this.greenTreeTexture = new Texture("images/greenTree.png");
        this.bulletTexture = new Texture("images/bullet.png");
        this.healthTexture = new Texture("images/health.png");

        GraphicsTank tankGraphics = new GraphicsTank(blueTankTexture, this.tileMovement);
        this.tankPlayerGraphics = new GraphicsHealth(tankGraphics, playerTank, healthTexture, this.tileMovement);
        this.treesToGraphics = new HashMap<>();
        for (var tree : treeObstacles)
            treesToGraphics.put(tree, new GraphicsTree(greenTreeTexture, this.tileMovement));
        this.tanksToGraphics = new HashMap<>();
        for (var tank : tanks) {
            tankGraphics = new GraphicsTank(blueTankTexture, this.tileMovement);
            tanksToGraphics.put(tank, new GraphicsHealth(tankGraphics, tank, healthTexture, this.tileMovement));
        }
        this.bulletsToGraphics = new HashMap<>();

        this.playerTank = playerTank;
    }

    private TileMovement getTileMovement() {
        return this.tileMovement;
    }

    public void moveRectangleAtTileCenter() {
        for (var entry : treesToGraphics.entrySet()) {
            var tree = entry.getKey();
            var treeGraphics = entry.getValue();
            GdxGameUtils.moveRectangleAtTileCenter(groundLayer, treeGraphics.getRectangle(), tree.getCoordinates());
        }
    }

    public void render() {
        clearScreen();
        movePlayerRectangle();
        moveTanksRectangle();
        moveBulletsRectangle();
        levelRenderer.render();
        batch.begin();
        renderObjects();
        batch.end();
    }

    void movePlayerRectangle() {
        tankPlayerGraphics.moveBetweenTileCenters(playerTank.getCoordinates(), playerTank.getDestinationCoordinates(), playerTank.getMovementProgress());
    }

    void moveTanksRectangle() {
        for (var entry : tanksToGraphics.entrySet()) {
            var tank = entry.getKey();
            var tankGraphics = entry.getValue();
            tankGraphics.moveBetweenTileCenters(tank.getCoordinates(), tank.getDestinationCoordinates(), tank.getMovementProgress());
        }
    }

    void moveBulletsRectangle() {
        for (var entry : bulletsToGraphics.entrySet()) {
            var bullet = entry.getKey();
            var bulletGraphics = entry.getValue();
            bulletGraphics.moveBetweenTileCenters(bullet.getCoordinates(), bullet.getDestinationCoordinates(), bullet.getMovementProgress());
        }
    }

    void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    void renderObjects() {
        renderPlayer();
        renderTanks();
        renderTrees();
        renderBullets();
    }

    void renderPlayer() {
        tankPlayerGraphics.render(batch, playerTank.getRotation());
    }

    void renderTanks() {
        for (var entry : tanksToGraphics.entrySet()) {
            entry.getValue().render(batch, entry.getKey().getRotation());
        }
    }

    void renderTrees() {
        for (var entry : treesToGraphics.entrySet())
            entry.getValue().render(batch, 0f);
    }

    void renderBullets() {
        for (var entry : bulletsToGraphics.entrySet())
            entry.getValue().render(batch, 0f);
    }

    public void dispose() {
        greenTreeTexture.dispose();
        blueTankTexture.dispose();
        bulletTexture.dispose();
        batch.dispose();
    }

    @Override
    public void update(Event event, ObjectByGame objectByGame) {
        switch(event) {
            case AddBullet:
                bulletsToGraphics.put((Bullet) objectByGame, new GraphicsBullet(bulletTexture, tileMovement));
                break;
            case RemoveBullet:
                Bullet removable = null;
                for (var entry : bulletsToGraphics.entrySet()) {
                    if (entry.getKey() == objectByGame) {
                        removable = entry.getKey();
                        break;
                    }
                }
                if (removable != null) {
                    bulletsToGraphics.remove(removable);
                }
                break;
            case RemoveTank:
                for (var entry : tanksToGraphics.entrySet()) {
                    if (entry.getKey() == objectByGame) {
                        tanksToGraphics.remove(entry.getKey(), entry.getValue());
                        break;
                    }
                }
                break;
            case ChangeHealth:
                tankPlayerGraphics.changeHealthBar();
                for (var entry : tanksToGraphics.entrySet()) {
                    entry.getValue().changeHealthBar();
                }
                break;
        }
    }
}
