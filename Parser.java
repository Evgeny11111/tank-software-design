package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;

public class Parser {
    public static ArrayList<GridPoint2> getTreeCoordinates(ArrayList<String> lines){
        ArrayList<GridPoint2> treeCoordinates = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                if (lines.get(i).charAt(j) == 'T'){
                    treeCoordinates.add(new GridPoint2(j, i));
                }
            }
        }

        return treeCoordinates;
    }

    public static ArrayList<GridPoint2> getPlayerCoordinates(ArrayList<String> lines){
        ArrayList<GridPoint2> playerCoordinates = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                if (lines.get(i).charAt(j) == 'X'){
                    playerCoordinates.add(new GridPoint2(j, i));
                }
            }
        }

        return playerCoordinates;
    }
}
