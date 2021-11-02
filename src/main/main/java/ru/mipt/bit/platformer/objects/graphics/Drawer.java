package ru.mipt.bit.platformer.objects.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;

import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class Drawer {
    public static void clearScreen(){
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    public static void draw(Batch batch, List<Tank> tanks, List<Tree> trees) {
        batch.begin();
        // render player
        for (Tank tank : tanks) {
            drawTextureRegionUnscaled(batch, tank.getTexture().getGraphics(), tank.getTexture().getRectangle(), tank.getRotation());
        }
        // render tree obstacle
        for (Tree tree : trees) {
            drawTextureRegionUnscaled(batch, tree.getGraphicsTree().getGraphics(), tree.getGraphicsTree().getRectangle(), 0f);
        }

        batch.end();
    }
}
