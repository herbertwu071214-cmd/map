package com.herb.macondo.map.model;

import com.herb.macondo.map.util.CollisionDetector;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    private double playerX = 400;
    private double playerY = 300;
    private double playerSpeed = 300;
    private double worldWidth = 2000;
    private double worldHeight = 2000;
    private boolean useMouseControl = false;

    private List<Obstacle> obstacles = new ArrayList<>();
    private Random random = new Random();

    private double playerHealth = 100;
    private double playerMaxHealth = 100;
    private int playerLevel = 1;
    private double currentExp = 0;
    private double expToNextLevel = 100;

    private List<Enemy> enemies = new ArrayList<>();
    private List<Projectile> projectiles = new ArrayList<>();
    private List<ExpOrb> expOrbs = new ArrayList<>();
    private List<PowerCube> powerCubes = new ArrayList<>();

    private double rocketCooldown = 0;
    private boolean rocketUnlocked = false;
    private List<Dagger> daggers = new ArrayList<>();

    private double damageMultiplier = 1.0;
    private int extraProjectiles = 0;
    private double fireRateMultiplier = 1.0;

    public GameModel() {
        generateRandomObstacles();
        initializePlayerStats();
    }

    private void initializePlayerStats() {
        playerHealth = 100;
        playerMaxHealth = 100;
        playerLevel = 1;
        currentExp = 0;
        expToNextLevel = 100;
        damageMultiplier = 1.0;
        extraProjectiles = 0;
        fireRateMultiplier = 1.0;
        rocketUnlocked = false;
        rocketCooldown = 0;
        daggers.clear();
    }

    private void generateRandomObstacles() {
        for (int i = 0; i < 30; i++) {
            double x = random.nextDouble() * (worldWidth - 100) + 50;
            double y = random.nextDouble() * (worldHeight - 100) + 50;
            double w = random.nextDouble() * 60 + 30;
            double h = random.nextDouble() * 60 + 30;
            obstacles.add(new Obstacle(x, y, w, h));
        }
    }

    public void addExp(double amount) {
        currentExp += amount;
        while (currentExp >= expToNextLevel) {
            currentExp -= expToNextLevel;
            playerLevel++;
            expToNextLevel = 100 + (playerLevel - 1) * 50;
        }
    }

    public void healPlayer(double amount) {
        playerHealth += amount;
        if (playerHealth > playerMaxHealth) playerHealth = playerMaxHealth;
    }

    public void increaseMaxHealth(double amount) {
        playerMaxHealth += amount;
        playerHealth += amount;
    }

    public void applyUpgrade(Upgrade upgrade) {
        switch (upgrade.getType()) {
            case DAMAGE:
                damageMultiplier += 0.2;
                break;
            case PROJECTILE_COUNT:
                extraProjectiles++;
                break;
            case FIRE_RATE:
                fireRateMultiplier *= 0.9;
                break;
            case MAX_HEALTH:
                increaseMaxHealth(20);
                break;
            case ROCKET:
                rocketUnlocked = true;
                break;
            case DAGGER:
                daggers.add(new Dagger());
                break;
        }
    }

    public void damagePlayer(double amount) {
        playerHealth -= amount;
        if (playerHealth < 0) playerHealth = 0;
    }

    public boolean isPlayerDead() {
        return playerHealth <= 0;
    }

    public void resetGame() {
        playerX = 400;
        playerY = 300;
        initializePlayerStats();
        enemies.clear();
        projectiles.clear();
        expOrbs.clear();
        powerCubes.clear();
        daggers.clear();
        rocketCooldown = 0;
    }

    public double getPlayerX() { return playerX; }
    public double getPlayerY() { return playerY; }
    public void setPlayerX(double x) { playerX = x; }
    public void setPlayerY(double y) { playerY = y; }
    public double getPlayerSpeed() { return playerSpeed; }
    public double getWorldWidth() { return worldWidth; }
    public double getWorldHeight() { return worldHeight; }
    public boolean isUseMouseControl() { return useMouseControl; }
    public void setUseMouseControl(boolean use) { useMouseControl = use; }
    public List<Obstacle> getObstacles() { return obstacles; }
    public double getPlayerHealth() { return playerHealth; }
    public double getPlayerMaxHealth() { return playerMaxHealth; }
    public int getPlayerLevel() { return playerLevel; }
    public double getCurrentExp() { return currentExp; }
    public double getExpToNextLevel() { return expToNextLevel; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<Projectile> getProjectiles() { return projectiles; }
    public List<ExpOrb> getExpOrbs() { return expOrbs; }
    public List<PowerCube> getPowerCubes() { return powerCubes; }
    public double getDamageMultiplier() { return damageMultiplier; }
    public int getExtraProjectiles() { return extraProjectiles; }
    public double getFireRateMultiplier() { return fireRateMultiplier; }
    public boolean isRocketUnlocked() { return rocketUnlocked; }
    public double getRocketCooldown() { return rocketCooldown; }
    public void setRocketCooldown(double cd) { rocketCooldown = cd; }
    public List<Dagger> getDaggers() { return daggers; }

    public void movePlayer(double dx, double dy, double screenWidth, double screenHeight) {
        double newX = playerX + dx;
        double newY = playerY + dy;
        if (newX >= 15 && newX <= worldWidth - 15) playerX = newX;
        if (newY >= 15 && newY <= worldHeight - 15) playerY = newY;
    }

    public void setPlayerPosition(double x, double y) {
        double pw = 30, ph = 30;
        if (!CollisionDetector.collidesWithAnyObstacle(x - 15, y - 15, pw, ph, obstacles)) {
            if (x >= 15 && x <= worldWidth - 15) playerX = x;
            if (y >= 15 && y <= worldHeight - 15) playerY = y;
        }
    }
}
