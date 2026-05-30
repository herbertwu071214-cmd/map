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

    private List<Enemy> enemies = new ArrayList<>();
    private List<Projectile> projectiles = new ArrayList<>();
    private List<ExpOrb> expOrbs = new ArrayList<>();

    private double projectileCooldown = 0;
    private double projectileCooldownMax = 0.3;
    private double damageMultiplier = 1.0;
    private int extraProjectiles = 0;
    private double fireRateMultiplier = 1.0;

    private int playerLevel = 1;
    private double currentExp = 0;
    private double expToNextLevel = 100;

    private boolean upgradePending = false;
    private List<Upgrade> upgradeChoices = new ArrayList<>();

    public GameModel() {
        generateRandomObstacles();
        spawnInitialEnemy();
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

    private void spawnInitialEnemy() {
        enemies.add(new Enemy(600, 500));
    }

    public void shootProjectile(double mouseWorldX, double mouseWorldY) {
        if (projectileCooldown > 0) return;
        double damage = 15 * damageMultiplier;
        Projectile p = new Projectile(playerX, playerY, mouseWorldX, mouseWorldY, damage);
        projectiles.add(p);

        if (extraProjectiles > 0) {
            double angleOffset = Math.PI / 6;
            double baseAngle = Math.atan2(mouseWorldY - playerY, mouseWorldX - playerX);
            for (int i = 0; i < extraProjectiles; i++) {
                double offset = (i - extraProjectiles / 2.0) * angleOffset;
                double angle = baseAngle + offset;
                double dx = Math.cos(angle) * 500;
                double dy = Math.sin(angle) * 500;
                Projectile p2 = new Projectile(playerX, playerY, playerX + dx, playerY + dy, damage);
                projectiles.add(p2);
            }
        }
        projectileCooldown = projectileCooldownMax * fireRateMultiplier;
    }

    public void updateCooldowns(double deltaTime) {
        if (projectileCooldown > 0) projectileCooldown -= deltaTime;
    }

    public void damagePlayer(double amount) {
        playerHealth -= amount;
        if (playerHealth < 0) playerHealth = 0;
    }

    public void addExp(double amount) {
        currentExp += amount;
        while (currentExp >= expToNextLevel) {
            currentExp -= expToNextLevel;
            playerLevel++;
            expToNextLevel = 100 + (playerLevel - 1) * 50;
            prepareUpgradeChoices();
        }
    }

    private void prepareUpgradeChoices() {
        upgradeChoices.clear();
        List<Upgrade.UpgradeType> types = List.of(
                Upgrade.UpgradeType.DAMAGE,
                Upgrade.UpgradeType.PROJECTILE_COUNT,
                Upgrade.UpgradeType.FIRE_RATE,
                Upgrade.UpgradeType.MAX_HEALTH
        );
        java.util.Collections.shuffle(types);
        for (int i = 0; i < Math.min(3, types.size()); i++) {
            upgradeChoices.add(new Upgrade(types.get(i)));
        }
        upgradePending = true;
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
                playerMaxHealth += 20;
                playerHealth += 20;
                break;
            default:
                break;
        }
        upgradePending = false;
    }

    public void spawnExpOrb(double x, double y, double value) {
        expOrbs.add(new ExpOrb(x, y, value));
    }

    public void collectExpOrbs() {
        expOrbs.removeIf(orb -> {
            double dx = orb.getX() - playerX;
            double dy = orb.getY() - playerY;
            if (Math.hypot(dx, dy) < 20) {
                addExp(orb.getValue());
                return true;
            }
            return false;
        });
    }

    public void updateExpOrbs(double deltaTime) {
        for (ExpOrb orb : expOrbs) {
            orb.moveTowards(playerX, playerY, deltaTime);
        }
        collectExpOrbs();
    }

    // Getters
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
    public List<Enemy> getEnemies() { return enemies; }
    public List<Projectile> getProjectiles() { return projectiles; }
    public List<ExpOrb> getExpOrbs() { return expOrbs; }
    public double getDamageMultiplier() { return damageMultiplier; }
    public int getExtraProjectiles() { return extraProjectiles; }
    public double getFireRateMultiplier() { return fireRateMultiplier; }
    public int getPlayerLevel() { return playerLevel; }
    public double getCurrentExp() { return currentExp; }
    public double getExpToNextLevel() { return expToNextLevel; }
    public boolean isUpgradePending() { return upgradePending; }
    public List<Upgrade> getUpgradeChoices() { return upgradeChoices; }

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

