package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.ObjectByGame;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.objects.graphics.GraphicsObjectBullet;
import ru.mipt.bit.platformer.objects.graphics.GraphicsTank;
import ru.mipt.bit.platformer.objects.graphics.GraphicsTree;
import ru.mipt.bit.platformer.objects.Event;
import ru.mipt.bit.platformer.driver.Subscriber;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.HashMap;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class LevelRenderer implements Subscriber {
    private final Batch batch;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final TileMovement tileMovement;
    private final Texture blueTankTexture;
    private final Texture greenTreeTexture;
    private final Texture bulletTexture;
    private final GraphicsTank tankPlayerGraphics;
    private final HashMap<Tank, GraphicsTank> tanksToGraphics;
    private final HashMap<Tree, GraphicsTree> treesToGraphics;
    private final HashMap<Bullet, GraphicsObjectBullet> bulletsToGraphics;

    private final Tank playerTank;
    private final ArrayList<Tree> trees;
    private final ArrayList<Tank> tanks;
    private final ArrayList<Bullet> bullets;

    public LevelRenderer(TiledMap level, TiledMapTileLayer groundLayer, Tank playerTank,  ArrayList<Tree> trees, ArrayList<Tank> tanks, ArrayList<Bullet> bullets) {
        this.batch = new SpriteBatch();
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.groundLayer = groundLayer;
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        this.blueTankTexture = new Texture("images/tank_blue.png");
        this.tankPlayerGraphics = new GraphicsTank(blueTankTexture, this.tileMovement);
        this.greenTreeTexture = new Texture("images/greenTree.png");
        this.bulletTexture = new Texture("images/bullet.png");
        this.treesToGraphics = new HashMap<>();
        for (var tree : trees)
            treesToGraphics.put(tree, new GraphicsTree(greenTreeTexture, this.tileMovement));
        this.tanksToGraphics = new HashMap<>();
        for (var tank : tanks)
            tanksToGraphics.put(tank, new GraphicsTank(blueTankTexture, this.tileMovement));
        this.bulletsToGraphics = new HashMap<>();

        this.playerTank = playerTank;
        this.trees = trees;
        this.tanks = tanks;
        this.bullets = bullets;
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
                bulletsToGraphics.put((Bullet) objectByGame, new GraphicsObjectBullet(bulletTexture, tileMovement));
                break;
            case RemoveBullet:
                for (var entry : bulletsToGraphics.entrySet()) {
                    if (entry.getKey() == objectByGame) {
                        bulletsToGraphics.remove(entry.getKey(), entry.getValue());
                        break;
                    }
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
        }
    }
}
