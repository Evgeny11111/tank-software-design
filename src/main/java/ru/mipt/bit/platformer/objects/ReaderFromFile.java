package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderFromFile {
    private final ArrayList<GridPoint2> tankCoordinates;
    private final ArrayList<GridPoint2> treeCoordinates;
    private final int width;
    private final int hight;

    public ReaderFromFile(){
        tankCoordinates = new ArrayList<>();
        treeCoordinates = new ArrayList<>();
        width = 10;
        hight = 8;
    }

    public void generateLevelFromFile(String pathToFile){
        try {
            File file = new File(pathToFile);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            List<String> lines = new ArrayList<>();
            String oneLine = reader.readLine();

            while (oneLine != null) {
                lines.add(0, oneLine);
                oneLine = reader.readLine();
            }

            tankCoordinates.addAll(Parser.calculateTankCoordinates(lines));
            treeCoordinates.addAll(Parser.calculateTreeCoordinates(lines));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHight() {
        return hight;
    }

    public ArrayList<GridPoint2> getTankCoordinates() {
        return tankCoordinates;
    }

    public ArrayList<GridPoint2> getTreeCoordinates() {
        return treeCoordinates;
    }
}
