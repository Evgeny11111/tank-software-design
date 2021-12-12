package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Objects;

/**
 * Entity
 */
public class Tree {
    private final GridPoint2 treeObstacleCoordinates;

    public Tree(GridPoint2 coordinates) {
        this.treeObstacleCoordinates = new GridPoint2(coordinates.x, coordinates.y);
    }

    public GridPoint2 getCoordinates() {
        return this.treeObstacleCoordinates;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Tree)) return false;
        if (obj == this) return true;
        return (((Tree) obj).getCoordinates() == this.getCoordinates());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(treeObstacleCoordinates);
    }
}