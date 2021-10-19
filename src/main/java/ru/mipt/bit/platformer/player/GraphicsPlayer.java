package ru.mipt.bit.platformer.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.player.move.NewDestination;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GraphicsPlayer {

    private static Batch batch;
    private static Player tank;
    private static TiledMap level = new TmxMapLoader().load("level.tmx");
    private static MapRenderer levelRenderer = createSingleLayerMapRenderer(level, batch);
    private static TiledMapTileLayer groundLayer = getSingleLayer(level);
    private static TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    // Texture decodes an image file and loads it into GPU memory, it represents a native resource
    private static Texture blueTankTexture = new Texture("images/tank_blue.png");
    // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
    private static final TextureRegion player = new TextureRegion(blueTankTexture);

    public GraphicsPlayer(Player tank, Batch batch) {
        this.tank = tank;
        this.batch = batch;
    }

    public static TileMovement getTileMovement() {
        return tileMovement;
    }

    public static Player getTank() {
        return tank;
    }

    public static MapRenderer getLevelRenderer() {
        return levelRenderer;
    }

    public static TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    public static TiledMap getLevel() {
        return level;
    }

    public static TextureRegion getPlayer() {
        return player;
    }

    public static void CalcInterpPlayerCoordinate() {
        tileMovement.moveRectangleBetweenTileCenters(tank.getRectangle(), tank.getCoordinates(), NewDestination.getDestinationCoordinates(), tank.getMovementProgress());
    }

    public static void DrawPlayer() {
        drawTextureRegionUnscaled(batch, player, tank.getRectangle(), tank.getRotation());
    }
}
