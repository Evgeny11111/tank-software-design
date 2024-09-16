package ru.mipt.bit.platformer.objects.state;

import ru.mipt.bit.platformer.objects.Tank;

public class CriticalDamagedState implements State {
    private final Tank tank;

    public CriticalDamagedState(Tank tank) {
        this.tank = tank;
        this.tank.setMovementSpeed(tank.getMovementSpeed() * 1.5f);
    }

    @Override
    public boolean canShoot() {
        return false;
    }
}
