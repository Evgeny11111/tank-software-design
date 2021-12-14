package ru.mipt.bit.platformer.objects.state;

import ru.mipt.bit.platformer.objects.Bullet;

/**
 * Entity
 */
public interface State {
    boolean canShoot();

    void takeDamage(Bullet bullet);
}
