package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectsGenerator {

    private final ArrayList<GridPoint2> tankCoordinates;
    private final ArrayList<GridPoint2> treeCoordinates;
    private final int width;
    private final int hight;

    public ObjectsGenerator(){
        tankCoordinates = new ArrayList<>();
        treeCoordinates = new ArrayList<>();
        width = 10;
        hight = 8;
    }

    public void generateRandomCoordinates(int numberOfTanks, int numberOfObstacles){


        if (numberOfObstacles + numberOfTanks > width * hight){
            numberOfObstacles = width * hight - numberOfTanks;
        }

        GridPoint2 tmpTankCoordinate;
        for (int i = 0; i < numberOfTanks; i++){
            tmpTankCoordinate = new GridPoint2((int) (Math.random() * width), (int) (Math.random() * hight));
            while (tankCoordinates.contains(tmpTankCoordinate)){
                tmpTankCoordinate = new GridPoint2((int) (Math.random() * width), (int) (Math.random() * hight));
            }
            tankCoordinates.add(tmpTankCoordinate);
        }

        GridPoint2 tmpTreeCoordinate;
        for (int i = 0; i < numberOfObstacles; i++){
            tmpTreeCoordinate = new GridPoint2((int) (Math.random() * width), (int) (Math.random() * hight));
            while (tankCoordinates.contains(tmpTreeCoordinate) || treeCoordinates.contains(tmpTreeCoordinate)){
                tmpTreeCoordinate = new GridPoint2((int) (Math.random() * width), (int) (Math.random() * hight));
            }
            treeCoordinates.add(tmpTreeCoordinate);
        }
    }


    public List<GridPoint2> getTankCoordinates() {
        return tankCoordinates;
    }

    public List<GridPoint2> getTreeCoordinates() {
        return treeCoordinates;
    }

    public int getHight() {
        return hight;
    }

    public int getWidth() {
        return width;
    }
}
