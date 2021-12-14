package ru.mipt.bit.platformer.objects.state;

import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.Tank;


/**
 * Entity
 */
public class CriticalDamagedTankState implements TankState {
    private final Tank tank;

    public CriticalDamagedTankState(Tank tank) {
        this.tank = tank;
        this.tank.setMovementSpeed(tank.getMovementSpeed() * 1.5f);
    }

    @Override
    public boolean canShoot() {
        return false;
    }

    @Override
    public void takeDamage(Bullet bullet) {
        tank.kill();
    }

}
