package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class GraphicsTree {
    private final TextureRegion graphics;
    private final Rectangle rectangle;

    public GraphicsTree(Texture greenTreeTexture, TileMovement tileMovement) {
        this.graphics = new TextureRegion(greenTreeTexture);
        this.rectangle = createBoundingRectangle(this.graphics);
    }

    public void render(Batch batch, float rotation) {
        drawTextureRegionUnscaled(batch, this.graphics, this.rectangle, rotation);
    }

    public TextureRegion getGraphics() {
        return this.graphics;
    }
    public Rectangle getRectangle() {
        return this.rectangle;
    }
}