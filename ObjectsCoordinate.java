package ru.mipt.bit.platformer;


import com.badlogic.gdx.math.GridPoint2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ObjectsCoordinate {
    private final ArrayList<GridPoint2> arrayTrees;
    private final ArrayList<GridPoint2> arrayPlayers;

    public ObjectsCoordinate() {
        this.arrayTrees = new ArrayList();
        this.arrayPlayers = new ArrayList();
    }

    public void generateFromFile(String pathToFile){
        try {
            File file = new File(pathToFile);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            ArrayList<String> lines = new ArrayList<>();
            String oneLine = reader.readLine();

            while (oneLine != null) {
                lines.add(0, oneLine);
                oneLine = reader.readLine();
            }
            arrayPlayers.addAll(Parser.getPlayerCoordinates(lines));
            arrayTrees.addAll(Parser.getTreeCoordinates(lines));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void generatePlayers(int k)
    {
        if(k > 79)
        {
            k = 79;
        }
        for (int i = 0; i < k; i++) {
            GridPoint2 playerCoordinates = new GridPoint2((int)(Math.random() * 10),(int)(Math.random() * 8));
            while (arrayPlayers.contains(playerCoordinates) || arrayTrees.contains(playerCoordinates)){
                playerCoordinates = new GridPoint2((int)(Math.random() * 10),(int)(Math.random() * 8));
            }
            arrayPlayers.add(playerCoordinates);
        }
    }

    public void generateTrees(int k)
    {
        if(k > 79)
        {
            k = 79;
        }
        for (int i = 0; i < k; i++) {
            GridPoint2 treeCoordinates = new GridPoint2((int)(Math.random() * 10),(int)(Math.random() * 8));
            while (arrayPlayers.contains(treeCoordinates) || arrayTrees.contains(treeCoordinates)){
                treeCoordinates = new GridPoint2((int)(Math.random() * 10),(int)(Math.random() * 8));
            }
            arrayTrees.add(treeCoordinates);
        }
    }

    public ArrayList<GridPoint2> getArrayPlayers() {
        return arrayPlayers;
    }

    public ArrayList<GridPoint2> getArrayTrees() {
        return arrayTrees;
    }
}
