package ru.mipt.bit.platformer.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class GraphicsPlayer {

    // Texture decodes an image file and loads it into GPU memory, it represents a native resource
    private static final Texture blueTankTexture = new Texture("images/tank_blue.png");
    // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
    private static final TextureRegion player = new TextureRegion(blueTankTexture);


    public static TextureRegion getPlayer() {
        return player;
    }


}
