package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.objects.GraphicsObjects;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.player.GraphicsPlayer;
import ru.mipt.bit.platformer.player.Player;

import java.util.ArrayList;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class Drawer {
    private final ArrayList<Tree> trees;
    private final Player player;

    public Drawer(ArrayList<Tree> trees, Player player) {
        this.trees = trees;
        this.player = player;
    }


    public void DrawTrees() {
        for (Tree tree : trees) {
            drawTextureRegionUnscaled(Creater.getBatch(), GraphicsObjects.getObstacle(), tree.getObstacleRectangle(), 0f);
        }
    }

    public void DrawPlayer() {
        drawTextureRegionUnscaled(Creater.getBatch(), GraphicsPlayer.getPlayer(), player.getRectangle(), player.getRotation());
    }
}
