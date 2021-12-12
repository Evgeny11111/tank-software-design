package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class GraphicsTree implements GraphicsObject{
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final TileMovement tileMovement;

    public GraphicsTree(Texture greenTreeTexture, TileMovement tileMovement) {
        this.graphics = new TextureRegion(greenTreeTexture);
        this.rectangle = createBoundingRectangle(this.graphics);
        this.tileMovement = tileMovement;
    }
    public void render(Batch batch, float rotation) {
        drawTextureRegionUnscaled(batch, this.graphics, this.rectangle, rotation);
    }

    @Override
    public void moveBetweenTileCenters(GridPoint2 coordinates, GridPoint2 destinationCoordinates, float movementProgress) {
    }

    @Override
    public void changeHealthBar() {

    }

    public TextureRegion getGraphics() {
        return this.graphics;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }
}