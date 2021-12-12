package ru.mipt.bit.platformer.objects.state;


import ru.mipt.bit.platformer.objects.Tank;

import java.util.Date;

/**
 * Entity
 */
public class MediumDamagedState implements State{
    private final Tank tank;

    public MediumDamagedState(Tank tank) {
        this.tank = tank;
        this.tank.setMovementSpeed(tank.getMovementSpeed() * 2f);
    }

    @Override
    public boolean canShoot() {
        long time = new Date().getTime();
        long delta = time - tank.getLastTimeShooting();
        if (delta < 600)
            return false;
        tank.setLastTimeShooting(time);
        return true;
    }

}
