package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.graphics.GraphicsTree;


public class Tree {
    private final GraphicsTree graphicsTree;
    private final GridPoint2 coordinates;

    public Tree(Texture texture, GridPoint2 gridPoint2){
        graphicsTree = new GraphicsTree(texture);
        coordinates = gridPoint2;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }


    public GraphicsTree getGraphicsTree() {
        return graphicsTree;
    }

}
