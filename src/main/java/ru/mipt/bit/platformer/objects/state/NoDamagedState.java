package ru.mipt.bit.platformer.objects.state;


import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.Date;

/**
 * Entity
 */
public class NoDamagedState implements State{
    private final Tank tank;

    public NoDamagedState(Tank tank) {
        this.tank = tank;
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
            tank.setState(new MediumDamagedState(tank));
        }
        else if (life == 33) {
            tank.setState(new CriticalDamagedState(tank));
        }
    }
}
