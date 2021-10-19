package ru.mipt.bit.platformer.player.move;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.player.Player;


public class NewDestination {


    private static GridPoint2 destinationCoordinates = Player.getCoordinates();
    public static GridPoint2 newCoordinates(){
        GridPoint2 newDestination = new GridPoint2();
        newDestination.x = destinationCoordinates.x + Movement.getCurrMovementVector().x;
        newDestination.y = destinationCoordinates.y + Movement.getCurrMovementVector().y;
        return newDestination;
    }

    public static GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public static void setDestinationCoordinates(GridPoint2 destinationCoordinates) {
        NewDestination.destinationCoordinates = destinationCoordinates;
    }
}
