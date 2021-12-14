package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

/**
 * Use case
 */
public class GraphicsHealthAndTank implements GraphicsObject {

    private final GraphicsTank graphicsTank;
    private final Tank tank;
    private final TextureRegion graphics;
    private Rectangle rectangle;
    private final TileMovement tileMovement;

    private boolean healthOn = false;

    public GraphicsHealthAndTank(GraphicsTank graphicsTank, Tank tank, Texture healthTexture, TileMovement tileMovement) {
        this.graphicsTank = graphicsTank;
        this.graphics = new TextureRegion(healthTexture);
        this.rectangle = createBoundingRectangle(this.graphics);
        this.tileMovement = tileMovement;
        this.tank = tank;
    }

    @Override
    public void render(Batch batch, float rotation) {
        graphicsTank.render(batch, rotation);
        if (healthOn) {
            graphics.setRegionWidth(tank.getLife());
            drawTextureRegionUnscaled(batch, graphics, rectangle.setCenter(rectangle.getX() + 50f, rectangle.getY() - 50f), 180f);
        }
    }

    @Override
    public void moveBetweenTileCenters(GridPoint2 coordinates, GridPoint2 destinationCoordinates, float movementProgress) {
        if (healthOn) {
            tileMovement.moveRectangleBetweenTileCenters(rectangle, coordinates, destinationCoordinates, movementProgress);
        }
        graphicsTank.moveBetweenTileCenters(coordinates, destinationCoordinates, movementProgress);
    }

    @Override
    public void changeHealthBar() {
        healthOn = !healthOn;
    }
}
