package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;


import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.player.move.Movement;
import ru.mipt.bit.platformer.player.move.NewDestination;


public class Renderer {
    private static final float MOVEMENT_SPEED = 0.4f;

    public void doRender() {

        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);


        // get time passed since the last render
        float deltaTime = Creater.getPlayer().getDeltaTime();


        if (isEqual(Creater.getPlayer().getMovementProgress(), 1f)) {
            Movement.movementKey(Gdx.input);
            if (!DestinationEqualObject.compare()) {
                NewDestination.setDestinationCoordinates(NewDestination.newCoordinates());
                Creater.getPlayer().setMovementProgress(0f);
                Movement.setCurrMovementVector(new GridPoint2(0, 0));
            }
            Creater.getPlayer().setRotation(Movement.getCurrRotation());
        }


        // calculate interpolated player screen coordinates
        Level.getTileMovement().moveRectangleBetweenTileCenters(Creater.getPlayer().getRectangle(), Creater.getPlayer().getCoordinates(), NewDestination.getDestinationCoordinates(), Creater.getPlayer().getMovementProgress());

        Creater.getPlayer().setMovementProgress(continueProgress(Creater.getPlayer().getMovementProgress(), deltaTime, MOVEMENT_SPEED));
        if (isEqual(Creater.getPlayer().getMovementProgress(), 1f)) {
            // record that the player has reached his/her destination
            Creater.getPlayer().setCoordinates(NewDestination.getDestinationCoordinates());
        }

        // render each tile of the level
        Level.getLevelRenderer().render();

        // start recording all drawing commands
        Creater.getBatch().begin();

        // render player
        Creater.getDrawer().DrawPlayer();

        // render tree obstacle
        Creater.getDrawer().DrawTrees();

        // submit all drawing requests
        Creater.getBatch().end();
    }


}
