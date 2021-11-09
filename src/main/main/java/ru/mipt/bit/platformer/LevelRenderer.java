package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.objects.graphics.GraphicsTank;
import ru.mipt.bit.platformer.objects.graphics.GraphicsTree;
import ru.mipt.bit.platformer.objects.move.ControlPanelBots;
import ru.mipt.bit.platformer.objects.move.ControlPanelPlayer;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class LevelRenderer {
    private final Tank playerTank;
    private final ArrayList<Tank> botTanks;
    private final ArrayList<Tree> trees;
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;
    private final GraphicsTank graphicsPlayerTank;
    private final ArrayList<GraphicsTank> graphicsBotTanks = new ArrayList<>();
    private final ArrayList<GraphicsTree> graphicsTrees = new ArrayList<>();

    LevelRenderer(TiledMap load, Batch batch, Tank playerTank, ArrayList<Tank> botTanks, ArrayList<Tree> trees){
        level = load;
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        this.playerTank = playerTank;
        graphicsPlayerTank = new GraphicsTank(new Texture("images/tank_blue.png"));
        this.botTanks = botTanks;
        for (int i = 0; i < botTanks.size(); i++){
            graphicsBotTanks.add(new GraphicsTank(new Texture("images/tank_blue.png")));
        }
        this.trees = trees;
        for (int i = 0; i < trees.size(); i++) {
            graphicsTrees.add(new GraphicsTree(new Texture("images/greenTree.png")));
        }
    }

    public void updatePlayerPlacement() {
        tileMovement.moveRectangleBetweenTileCenters(graphicsPlayerTank.getRectangle(),
                playerTank.getCoordinates(),
                playerTank.getDestinationCoordinates(),
                playerTank.getMovementProgress());
        for (int i = 0; i < botTanks.size(); i++) {
            tileMovement.moveRectangleBetweenTileCenters(graphicsBotTanks.get(i).getRectangle(),
                    botTanks.get(i).getCoordinates(),
                    botTanks.get(i).getDestinationCoordinates(),
                    botTanks.get(i).getMovementProgress());
        }
    }
    public void clearScreen(){
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    public void draw(Batch batch) {
        batch.begin();
        // render tanks
        drawTextureRegionUnscaled(batch, graphicsPlayerTank.getGraphics(), graphicsPlayerTank.getRectangle(), playerTank.getRotation());
        for (int i = 0; i < botTanks.size(); i++) {
            drawTextureRegionUnscaled(batch, graphicsBotTanks.get(i).getGraphics(), graphicsBotTanks.get(i).getRectangle(), botTanks.get(i).getRotation());
        }
        // render tree obstacle
        for (GraphicsTree graphicsTree : graphicsTrees) {
            drawTextureRegionUnscaled(batch, graphicsTree.getGraphics(), graphicsTree.getRectangle(), 0f);
        }

        batch.end();
    }

    public void placeObstacles() {
        for (int i = 0; i < trees.size(); i++) {
            moveRectangleAtTileCenter(groundLayer, graphicsTrees.get(i).getRectangle(), trees.get(i).getCoordinates());
        }

    }
    public void updateNextMove(Tank tank,boolean isPlayer){
        if (isPlayer){
            tank.setNextMove(ControlPanelPlayer.chooseKeyToDirection(Gdx.input));
        }
        else {
            tank.setNextMove(ControlPanelBots.chooseRandomlyDirection(Gdx.input));
        }
    }

    public void render(){
        levelRenderer.render();
    }

    public void dispose(){
        level.dispose();
        graphicsPlayerTank.dispose();
        for (GraphicsTank graphicsBotTank : graphicsBotTanks) {
            graphicsBotTank.dispose();
        }
        for (GraphicsTree graphicsTree : graphicsTrees) {
            graphicsTree.dispose();
        }
    }
}
