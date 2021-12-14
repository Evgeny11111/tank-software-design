package ru.mipt.bit.platformer.control.commands;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.driver.Level;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.Date;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

/**
 * Use case
 */
public class ShootCommand implements Command {

    private final Tank tank;
    private final Level level;

    public ShootCommand(Tank tank,  Level level) {
        this.tank = tank;
        this.level = level;
    }

    @Override
    public void make() {
        if (!tank.canShoot())
            return;

        GridPoint2 bulletCoords = tank.getNextCoords();
        Bullet bullet = new Bullet(bulletCoords, tank.getRotation(), tank.getCollisionChecker(), tank);

        if (bullet.checkCollisions(bulletCoords)) {
            level.addBullet(bullet);
        }
        tank.setLastTimeShooting(new Date().getTime());
    }
}
