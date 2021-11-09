package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.move.Movement;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;
public class Tank {
    private static final float MOVEMENT_SPEED = 0.4f;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private final GridPoint2 coordinates;
    // which tile the player want to go next
    private final GridPoint2 destinationCoordinates;
    private float movementProgress;
    private float rotation;

    private Movement nextMove;

    public Tank(GridPoint2 destinationCoordinates) {
        this.destinationCoordinates = destinationCoordinates;
        coordinates = new GridPoint2(destinationCoordinates);
        rotation = 0f;
        movementProgress = 1f;
        nextMove = new Movement();
    }

    private boolean hasFinishedMovement() {
        return isEqual(movementProgress, 1f);
    }

    private void makeRotation() {
        rotation = nextMove.rotation;
    }

    private void makeMovement() {
        destinationCoordinates.add(nextMove.directionVector);
    }

    private GridPoint2 tryMovement() {
        if (nextMove.vectorIsNull())
            return destinationCoordinates;

        GridPoint2 newCoordinates = new GridPoint2();
        newCoordinates.x = destinationCoordinates.x + nextMove.directionVector.x;
        newCoordinates.y = destinationCoordinates.y + nextMove.directionVector.y;
        return newCoordinates;
    }

    private void finishMovement() {
        nextMove.directionVector.x = 0;
        nextMove.directionVector.y = 0;
        movementProgress = 0f;
    }

    private boolean notObstacles(List<Tree> trees) {
        GridPoint2 thisCoordinates = tryMovement();
        for (Tree tree : trees) {
            if (tree.getCoordinates().equals(thisCoordinates)){
                return false;
            }
        }
        return true;
    }

    private boolean notTanks(Tank playerTank,List<Tank> tanks) {
        GridPoint2 thisCoordinates = tryMovement();
        GridPoint2 tankCoordinates;
        for (Tank tank : tanks) {
            if (this.equals(tank)) {
                continue;
            }
            tankCoordinates = tank.tryMovement();
            if (tank.getCoordinates().equals(thisCoordinates) ||
                    tankCoordinates.equals(thisCoordinates) ||
                    playerTank.getCoordinates().equals(thisCoordinates) ||
                    playerTank.getCoordinates().equals(tankCoordinates)){
                return false;
            }
        }
        return true;
    }

    private boolean notWall(int width, int hight) {
        GridPoint2 thisCoordinates = tryMovement();
        return thisCoordinates.x >= 0 && thisCoordinates.x < width &&
                thisCoordinates.y >= 0 && thisCoordinates.y < hight;
    }

    private void updateMovementProgress(float deltaTime, float movementSpeed) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
    }

    public void updateCoordinates(){
        if (hasFinishedMovement()) {
            // record that the player has reached his/her destination
            coordinates.set(destinationCoordinates);
        }
    }

    public void move() {
        if (!nextMove.vectorIsNull() && hasFinishedMovement()) {
            makeRotation();
            makeMovement();
            finishMovement();
        }

        float deltaTime = Gdx.graphics.getDeltaTime();
        updateMovementProgress(deltaTime, MOVEMENT_SPEED);
    }

    public float getRotation() {
        return rotation;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }



    public void setNextMove(Movement nextMove) {
        this.nextMove = nextMove;
    }

    public Movement getNextMove() {
        return nextMove;
    }





    public Boolean checkAllCollisions(List<Tree> trees,Tank playerTank,List<Tank> tanks, int width, int hight) {
        return notWall(width, hight) && notObstacles(trees) && notTanks(playerTank,tanks);
    }

}
