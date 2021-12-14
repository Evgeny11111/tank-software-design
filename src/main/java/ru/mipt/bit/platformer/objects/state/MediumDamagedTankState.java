package ru.mipt.bit.platformer.objects.state;


import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.Date;

/**
 * Entity
 */
public class MediumDamagedTankState implements TankState {
    private final Tank tank;

    public MediumDamagedTankState(Tank tank) {
        this.tank = tank;
        this.tank.setMovementSpeed(tank.getMovementSpeed() * 2f);
    }

    @Override
    public boolean canShoot() {
        long time = new Date().getTime();
        long delta = time - tank.getLastTimeShooting();
        if (delta < 2000)
            return false;
        tank.setLastTimeShooting(time);
        return true;
    }

    @Override
    public void takeDamage(Bullet bullet) {
        int life = tank.getLife() - bullet.getDamage();
        if (life == 66) {
            tank.setState(new MediumDamagedTankState(tank));
        }
        else if (life == 33) {
            tank.setState(new CriticalDamagedTankState(tank));
        }
    }
}
