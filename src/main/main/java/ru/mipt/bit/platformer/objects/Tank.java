package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.move.ControlPanel;
import ru.mipt.bit.platformer.objects.graphics.GraphicsTank;
import ru.mipt.bit.platformer.objects.move.Movement;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;
public class Tank {
    private static final float MOVEMENT_SPEED = 0.4f;
    private final GraphicsTank texture;
    // player current position coordinates on level 10x8 grid (e.g. x=0, y=1)
    private final GridPoint2 coordinates;
    // which tile the player want to go next
    private final GridPoint2 destinationCoordinates;
    private float movementProgress;
    private float rotation;

    private Movement nextMove;

    public Tank(Texture tankTexture, GridPoint2 destinationCoordinates) {
        texture = new GraphicsTank(tankTexture);
        this.destinationCoordinates = destinationCoordinates;
        coordinates = new GridPoint2(destinationCoordinates);
        rotation = 0f;
        movementProgress = 1f;
        nextMove = new Movement();
    }

    public boolean hasFinishedMovement() {
        return isEqual(movementProgress, 1f);
    }

    public void makeRotation() {
        rotation = nextMove.rotation;
    }

    public void makeMovement() {
        destinationCoordinates.add(nextMove.directionVector);
    }

    public GridPoint2 tryMovement() {
        if (nextMove.isNull())
            return destinationCoordinates;

        GridPoint2 newCoordinates = new GridPoint2();
        newCoordinates.x = destinationCoordinates.x + nextMove.directionVector.x;
        newCoordinates.y = destinationCoordinates.y + nextMove.directionVector.y;
        return newCoordinates;
    }

    public void finishMovement() {
        nextMove.directionVector.x = 0;
        nextMove.directionVector.y = 0;
        movementProgress = 0f;
    }

    public boolean notObstacles(List<Tree> trees) {
        GridPoint2 thisCoordinates = tryMovement();
        for (Tree tree : trees) {
            if (tree.getCoordinates().equals(thisCoordinates)){
                return false;
            }
        }
        return true;
    }

    private boolean notTanks(List<Tank> tanks) {
        GridPoint2 thisCoordinates = tryMovement();
        GridPoint2 tankCoordinates;
        for (Tank tank : tanks) {
            if (this.equals(tank)) {
                continue;
            }
            tankCoordinates = tank.tryMovement();
            if (tank.getCoordinates().equals(thisCoordinates) ||
                    tankCoordinates.equals(thisCoordinates)){
                return false;
            }
        }
        return true;
    }

    public void updateCoordinates(){
        if (hasFinishedMovement()) {
            // record that the player has reached his/her destination
            coordinates.set(destinationCoordinates);
        }
    }

    public void move() {
        if (!nextMove.isNull() && hasFinishedMovement()) {
            makeRotation();
            makeMovement();
            finishMovement();
        }

        float deltaTime = Gdx.graphics.getDeltaTime();
        updateMovementProgress(deltaTime, MOVEMENT_SPEED);
    }

    private boolean notWall(int width, int hight) {
        GridPoint2 thisCoordinates = tryMovement();
        return thisCoordinates.x >= 0 && thisCoordinates.x < width &&
                thisCoordinates.y >= 0 && thisCoordinates.y < hight;
    }

    public void updateMovementProgress(float deltaTime, float movementSpeed) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
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

    public GraphicsTank getTexture() {
        return texture;
    }

    public void dispose() {
        texture.getBlueTank().dispose();
    }

    public void setNextMove(Movement nextMove) {
        this.nextMove = nextMove;
    }

    public Movement getNextMove() {
        return nextMove;
    }

    public void updateNextMove(boolean isPlayer){
        if (isPlayer){
            updateNextMovePlayer();
        }
        else {
            updateNextMoveRandomly();
        }
    }

    private void updateNextMovePlayer() {
        nextMove = ControlPanel.chooseKeyToDirection(Gdx.input);
    }

    private void updateNextMoveRandomly() {
        nextMove = ControlPanel.chooseRandomlyDirection(Gdx.input);
    }

    public Boolean checkAllCollisions(List<Tree> trees, List<Tank> tanks, int width, int hight) {
        return notWall(width, hight) && notObstacles(trees) && notTanks(tanks);
    }

}
