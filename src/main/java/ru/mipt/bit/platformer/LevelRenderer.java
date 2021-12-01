package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.objects.graphics.GraphicsTank;
import ru.mipt.bit.platformer.objects.graphics.GraphicsTree;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class LevelRenderer {
    private final Batch batch;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final Texture blueTankTexture;
    private final Texture greenTreeTexture;
    private final GraphicsTank tankPlayerGraphics;
    private final ArrayList<GraphicsTank> tanksGraphics;
    private final ArrayList<GraphicsTree> graphicTrees;


    private final Tank playerTank;
    private final ArrayList<Tree> trees;
    private final ArrayList<Tank> tanks;

    public LevelRenderer(TiledMap level, TiledMapTileLayer groundLayer, Tank playerTank, ArrayList<Tree> trees, ArrayList<Tank> tanks) {
        this.batch = new SpriteBatch();
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.groundLayer = groundLayer;
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        this.blueTankTexture = new Texture("images/tank_blue.png");
        this.tankPlayerGraphics = new GraphicsTank(blueTankTexture, tileMovement);
        this.greenTreeTexture = new Texture("images/greenTree.png");
        this.graphicTrees = new ArrayList<>();
        for (Tree tree : trees)
            graphicTrees.add(new GraphicsTree(greenTreeTexture, tileMovement));
        this.tanksGraphics = new ArrayList<>();
        for (Tank tank : tanks)
            tanksGraphics.add(new GraphicsTank(blueTankTexture, tileMovement));

        this.playerTank = playerTank;
        this.trees = trees;
        this.tanks = tanks;
    }


    public void moveRectangleAtTileCenter() {
        for (int i = 0; i < trees.size(); ++i) {
            Tree tree = trees.get(i);
            GraphicsTree treeGraphics = this.graphicTrees.get(i);
            GdxGameUtils.moveRectangleAtTileCenter(groundLayer, treeGraphics.getRectangle(), tree.getCoordinates());
        }
    }

    public void render() {
        clearScreen();
        movePlayerRectangle();
        moveTanksRectangle();
        levelRenderer.render();
        batch.begin();
        renderObjects();
        batch.end();
    }

    void movePlayerRectangle() {
        tankPlayerGraphics.moveBetweenTileCenters(playerTank.getCoordinates(), playerTank.getDestinationCoordinates(), playerTank.getMovementProgress());
    }

    void moveTanksRectangle() {
        for (int i = 0; i < tanks.size(); ++i) {
            var tank = tanks.get(i);
            var tankGraphics = tanksGraphics.get(i);
            tankGraphics.moveBetweenTileCenters(tank.getCoordinates(), tank.getDestinationCoordinates(), tank.getMovementProgress());
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
    }

    void renderPlayer() {
        tankPlayerGraphics.render(batch, playerTank.getRotation());
    }

    void renderTanks() {
        for (int i = 0; i < tanksGraphics.size(); ++i) {
            var tank = tanks.get(i);
            var tankGraphics = tanksGraphics.get(i);
            tankGraphics.render(batch, tank.getRotation());
        }
    }

    void renderTrees() {
        for (var treeGraphics : graphicTrees)
            treeGraphics.render(batch, 0f);
    }

    public void dispose() {
        greenTreeTexture.dispose();
        blueTankTexture.dispose();
        batch.dispose();
    }

}
