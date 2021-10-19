package ru.mipt.bit.platformer;

import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Level {
    private static final TiledMap level = new TmxMapLoader().load("level.tmx");
    private static final MapRenderer levelRenderer = createSingleLayerMapRenderer(level, Creater.getBatch());
    private static final TiledMapTileLayer groundLayer = getSingleLayer(level);
    private static final TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

    public void placeObstacles(ArrayList<Tree> trees) {
        for (Tree tree : trees) {
            moveRectangleAtTileCenter(groundLayer, tree.getObstacleRectangle(), tree.getCoordinates());
        }
    }

    public static MapRenderer getLevelRenderer() {
        return levelRenderer;
    }

    public static TileMovement getTileMovement() {
        return tileMovement;
    }
}
