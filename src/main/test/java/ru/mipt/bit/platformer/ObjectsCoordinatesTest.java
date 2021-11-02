package ru.mipt.bit.platformer;

import org.junit.Test;
import ru.mipt.bit.platformer.objects.ObjectsCoordinates;

import static org.junit.Assert.*;

public class ObjectsCoordinatesTest {
    @Test
    public void generateRandomCoordinates() {
        ObjectsCoordinates objectsCoordinates = new ObjectsCoordinates();
        objectsCoordinates.generateRandomCoordinates(0, 0);

        assertTrue(objectsCoordinates.getTreeCoordinates().isEmpty());
    }
    @Test
    public void generateRandomCoordinates_shouldInputEqualsIfSumLess80() {
        ObjectsCoordinates objectsCoordinates = new ObjectsCoordinates();
        objectsCoordinates.generateRandomCoordinates(0, 41);

        assertEquals(objectsCoordinates.getTreeCoordinates().size(), 41);
    }
    @Test
    public void generateRandomCoordinates_shouldInputEqualsIfSumLess80_2() {
        ObjectsCoordinates objectsCoordinates = new ObjectsCoordinates();
        objectsCoordinates.generateRandomCoordinates(15, 12);

        assertEquals(objectsCoordinates.getTankCoordinates().size(), 15);
    }
}