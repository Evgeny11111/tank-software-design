package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface GraphicsObject {
    void render(Batch batch, float rotation);
}
