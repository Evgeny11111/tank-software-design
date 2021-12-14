package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Use case
 */
public interface GraphicsObject {
    void render(Batch batch, float rotation);

    void moveBetweenTileCenters(GridPoint2 coordinates, GridPoint2 destinationCoordinates, float movementProgress);

    void changeHealthBar();
}
