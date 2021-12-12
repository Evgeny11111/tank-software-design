package ru.mipt.bit.platformer.driver;

import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.objects.Event;
import ru.mipt.bit.platformer.Issuer;
import ru.mipt.bit.platformer.Observer;

import java.util.ArrayList;

/**
 * Adapter
 */
public class Level implements Issuer {
    private final int width = 10;
    private final int height = 8;

    private final Tank playerTank;
    private final ArrayList<Tank> tanks;
    private final ArrayList<Tree> trees;
    private final ArrayList<Bullet> bullets;

    private final ArrayList<Observer> observers;

    public Level(Tank playerTank, ArrayList<Tree> trees, ArrayList<Tank> tanks) {
        this.playerTank = playerTank;
        this.trees = trees;
        this.tanks = tanks;
        this.bullets = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public void updateObjects(float deltaTime) {
        checkObjects();
        livePlayer(deltaTime);
        liveTanks(deltaTime);
        liveBullets(deltaTime);
    }

    private void liveBullets(float deltaTime) {
        for (Bullet bullet : bullets) {
            bullet.live(deltaTime);
        }
    }

    private void liveTanks(float deltaTime) {
        for (Tank tank : tanks) {
            tank.live(deltaTime);
        }
    }

    public void livePlayer(float deltaTime) {
        playerTank.live(deltaTime);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
        notifySubs(Event.AddBullet, bullet);
    }

    public void checkObjects() {
        checkTanks();
        checkBullets();
    }


    public void checkTanks() {
        ArrayList <Tank> tanksCopy = new ArrayList<>(tanks);
        for (Tank tank : tanksCopy) {
            if (!tank.isAlive()) {
                System.out.println("remove tank");
                notifySubs(Event.RemoveTank, tank);
                tanks.remove(tank);
            }
        }
    }

    public void checkBullets() {
        ArrayList<Bullet> bulletsCopy = new ArrayList<>(bullets);
        for (Bullet bullet : bulletsCopy) {
            if (!bullet.isExistent()) {
                notifySubs(Event.RemoveBullet, bullet);
                bullets.remove(bullet);
            }
        }
    }

    public Tank getPlayerTank() {
        return playerTank;
    }

    public ArrayList<Tank> getTanks() {
        return tanks;
    }

    public ArrayList<Tree> getTreeObstacles() {
        return trees;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifySubs(Event event, Object objectByGame) {
        for (Observer sub : observers)
            sub.update(event, objectByGame);
    }
}
