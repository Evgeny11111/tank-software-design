package ru.mipt.bit.platformer.objects.state;

import ru.mipt.bit.platformer.objects.Bullet;

/**
 * Entity
 */
public interface TankState {
    boolean canShoot();

    void takeDamage(Bullet bullet);
}
