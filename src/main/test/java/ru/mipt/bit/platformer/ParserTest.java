package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void calculateTreeCoordinatesReturnIsEmpty() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("___X______");
        lines.add("_____X____");
        lines.add("__________");
        lines.add("X________X");

        ArrayList<GridPoint2> result = new ArrayList<>();

        assertEquals(result, Parser.calculateTreeCoordinates(lines));
    }
    @Test
    public void calculateTreeCoordinatesReturnNumberOfTree() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("___T______");
        lines.add("_____T____");
        lines.add("__________");
        lines.add("___T_____T");

        ArrayList<GridPoint2> result = new ArrayList<>();
        result.add(new GridPoint2(3,0));
        result.add(new GridPoint2(5,1));
        result.add(new GridPoint2(3,3));
        result.add(new GridPoint2(9,3));
        assertEquals(result, Parser.calculateTreeCoordinates(lines));
    }


    @Test
    public void calculateTankCoordinatesReturnIsEmpty() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("___T___TT_");
        lines.add("_____TTT__");
        lines.add("___TTT____");
        lines.add("___T_____T");
        ArrayList<GridPoint2> result = new ArrayList<>();

        assertEquals(result,Parser.calculateTankCoordinates(lines));
    }
    @Test
    public void calculateTankCoordinatesReturnNumberOfTree() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("___X___TT_");
        lines.add("_______X__");
        lines.add("___XXX____");
        lines.add("___T_____T");

        ArrayList<GridPoint2> result = new ArrayList<>();

        result.add(new GridPoint2(3,0));
        result.add(new GridPoint2(7,1));
        result.add(new GridPoint2(3,2));
        result.add(new GridPoint2(4,2));
        result.add(new GridPoint2(5,2));

        assertEquals(result,Parser.calculateTankCoordinates(lines));
    }
}