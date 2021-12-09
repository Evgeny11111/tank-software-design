package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Comparator;

public class GridPoint2Comparator implements Comparator<GridPoint2> {
      @Override
    public int compare(GridPoint2 o1, GridPoint2 o2) {
        if (o1.x == o2.x)
            return Integer.compare(o1.y, o2.y);
        else
            return Integer.compare(o1.x, o2.x);
    }
}
