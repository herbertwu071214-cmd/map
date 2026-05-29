package com.herb.macondo.map.model;

public class Enemy {
    private double x;
    private double y;
    private double health;
    private double maxHealth;
    private double speed;
    private double damage;
    private double width;
    private double height;

    public Enemy(double x, double y) {
        this.x = x;
        this.y = y;
        this.health = 30;
        this.maxHealth = 30;
        this.speed = 80;
        this.damage = 10;
        this.width = 25;
        this.height = 25;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getHealth() { return health; }
    public double getMaxHealth() { return maxHealth; }
    public double getSpeed() { return speed; }
    public double getDamage() { return damage; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public boolean isAlive() { return health > 0; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void takeDamage(double amount) { health -= amount; }

    public void moveTowards(double targetX, double targetY, double deltaTime) {
        double dx = targetX - x;
        double dy = targetY - y;
        double length = Math.hypot(dx, dy);
        if (length > 0.01) {
            dx /= length;
            dy /= length;
            x += dx * speed * deltaTime;
            y += dy * speed * deltaTime;
        }
    }
}
