package ru.mipt.bit.platformer.objects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tree {
    private final Rectangle obstacleRectangle;
    private final GridPoint2 coordinates;

    public Tree(TextureRegion graphics, GridPoint2 coordinates) {
        this.coordinates = coordinates;
        obstacleRectangle = createBoundingRectangle(graphics);
    }


    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public Rectangle getObstacleRectangle() {
        return obstacleRectangle;
    }


}
