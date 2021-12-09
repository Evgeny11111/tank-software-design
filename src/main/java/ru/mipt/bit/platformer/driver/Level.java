package ru.mipt.bit.platformer.driver;

import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.ObjectByGame;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.objects.Event;

import java.util.ArrayList;

public class Level {
    private final int width = 10;
    private final int height = 8;

    private final Tank playerTank;
    private final ArrayList<Tank> tanks;
    private final ArrayList<Tree> trees;
    private final ArrayList<Bullet> bullets;

    private final ArrayList<Subscriber> subscribers;

    public Level(Tank playerTank, ArrayList<Tree> trees, ArrayList<Tank> tanks) {
        this.playerTank = playerTank;
        this.trees = trees;
        this.tanks = tanks;
        this.bullets = new ArrayList<>();
        subscribers = new ArrayList<>();
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
        checkPlayer();
        checkTanks();
        checkBullets();
    }

    public void checkPlayer() {
        //if (!playerTank.isAlive())
        //System.out.println("Player died!");
    }

    public void checkTanks() {
        ArrayList <Tank> tanksCopy = new ArrayList<>(tanks);
        for (Tank tank : tanksCopy) {
            if (!tank.isAlive()) {
                System.out.println("removing tank");
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


    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }


    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }


    public void notifySubs(Event event, ObjectByGame objectByGame) {
        for (Subscriber sub : subscribers)
            sub.update(event, objectByGame);
    }
}
