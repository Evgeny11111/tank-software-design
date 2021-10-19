package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class GraphicsObjects {


    // Texture decodes an image file and loads it into GPU memory, it represents a native resource
    private static final Texture greenTreeTexture = new Texture("images/greenTree.png");
    // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
    private static final TextureRegion obstacle = new TextureRegion(greenTreeTexture);



    public static TextureRegion getObstacle() {
        return obstacle;
    }

}
