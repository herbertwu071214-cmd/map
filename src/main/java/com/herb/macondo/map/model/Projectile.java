package com.herb.macondo.map.model;

public class Projectile {
    private double x;
    private double y;
    private double vx;
    private double vy;
    private double damage;
    private double size;
    private boolean active;
    private double lifetime;
    private double maxLifetime;

    public Projectile(double x, double y, double targetX, double targetY, double damage) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.size = 6;
        this.active = true;
        this.maxLifetime = 3.0;
        this.lifetime = 0;

        double dx = targetX - x;
        double dy = targetY - y;
        double length = Math.hypot(dx, dy);
        if (length > 0.01) {
            double speed = 500;
            this.vx = (dx / length) * speed;
            this.vy = (dy / length) * speed;
        } else {
            this.vx = 0;
            this.vy = 0;
        }
    }

    public void update(double deltaTime) {
        x += vx * deltaTime;
        y += vy * deltaTime;
        lifetime += deltaTime;
        if (lifetime >= maxLifetime) {
            active = false;
        }
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getDamage() { return damage; }
    public double getSize() { return size; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
