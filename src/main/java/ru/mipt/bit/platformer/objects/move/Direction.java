package ru.mipt.bit.platformer.objects.move;

import java.util.concurrent.ThreadLocalRandom;

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
}
