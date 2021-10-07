package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import ru.mipt.bit.platformer.objects.GraphicsObjects;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.player.GraphicsPlayer;
import ru.mipt.bit.platformer.player.Player;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class Create {

    public Batch batch;

    private MapRenderer levelRenderer;

    private GraphicsPlayer graphicsPlayer;

    private GraphicsObjects graphicsTree;

    private Player player;

    private Tree tree;



    void doCreate() {
        batch = new SpriteBatch();

        player = new Player();
        graphicsPlayer = new GraphicsPlayer(player, batch);

        tree = new Tree();
        graphicsTree = new GraphicsObjects(tree, batch);

        // load level tiles
        levelRenderer = createSingleLayerMapRenderer(graphicsPlayer.getLevel(), batch);

        moveRectangleAtTileCenter(graphicsPlayer.getGroundLayer(), tree.getObstacleRectangle(), tree.getObstacleCoordinates());

    }

    public Batch getBatch() {
        return batch;
    }

    public GraphicsPlayer getGraphicsPlayer() {
        return graphicsPlayer;
    }

    public GraphicsObjects getGraphicsTree() {
        return graphicsTree;
    }

    public MapRenderer getLevelRenderer() {
        return levelRenderer;
    }
}
