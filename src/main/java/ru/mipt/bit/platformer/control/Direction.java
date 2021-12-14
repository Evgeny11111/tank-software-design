package ru.mipt.bit.platformer.control;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Entity
 */
public enum Direction {
    Up,
    Down,
    Left,
    Right;

    public Direction getRandomDirection() {
        int num = ThreadLocalRandom.current().nextInt(0, 10000) % 4;
        return switch (num) {
            case 0 -> Up;
            case 1 -> Down;
            case 2 -> Left;
            case 3 -> Right;
            default -> Up;
        };
    }

    private Map<Direction, Float> createMapperForDirection() {
        Map<Direction, Float> rotates = new HashMap<>();
        rotates.put(Direction.Up, 90f);
        rotates.put(Direction.Left, -180f);
        rotates.put(Direction.Down, -90f);
        rotates.put(Direction.Right, 0f);
        return rotates;
    }

    private Map<Float, Direction> createMapperForFloat() {
        Map<Float, Direction> rotates = new HashMap<>();
        rotates.put(90f, Direction.Up);
        rotates.put(-180f, Direction.Left);
        rotates.put(-90f, Direction.Down);
        rotates.put(0f, Direction.Right);
        return rotates;
    }

    public Direction mapFromFloat(float rotation) {
        var rotates = createMapperForFloat();
        return rotates.get(rotation);
    }

    public float mapFromDirection(Direction direction) {
        var rotates = createMapperForDirection();
        return rotates.get(direction);
    }
}
