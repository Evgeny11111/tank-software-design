package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Level {
    public TiledMap level;
    public MapRenderer levelRenderer;
    public TileMovement tileMovement;
    public TiledMapTileLayer groundLayer;

    Level(TiledMap load, Batch batch){
        level = load;
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public void updatePlayerPlacement(Tank tank) {
        tileMovement.moveRectangleBetweenTileCenters(tank.getTexture().getRectangle(),
                tank.getCoordinates(),
                tank.getDestinationCoordinates(),
                tank.getMovementProgress());
    }

    public void placeObstacles(List<Tree> trees) {
        for (Tree tree : trees) {
            moveRectangleAtTileCenter(groundLayer, tree.getGraphicsTree().getRectangle(), tree.getCoordinates());
        }

    }

    public void render(){
        levelRenderer.render();
    }

    public void dispose(){
        level.dispose();
    }
}
