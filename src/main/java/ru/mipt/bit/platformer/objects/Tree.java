package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.graphics.GraphicsTree;


public class Tree {
    private final GridPoint2 coordinates;

    public Tree(GridPoint2 gridPoint2){
        coordinates = gridPoint2;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }



}
