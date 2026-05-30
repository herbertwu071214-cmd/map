package com.herb.macondo.map.model;

public class ExpOrb {
    private double x;
    private double y;
    private double value;
    private boolean active;
    private double magnetRadius;

    public ExpOrb(double x, double y, double value) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.active = true;
        this.magnetRadius = 150;
    }

    public void moveTowards(double targetX, double targetY, double deltaTime) {
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.hypot(dx, dy);
        if (distance > 0.01 && distance < magnetRadius) {
            double speed = 300;
            dx /= distance;
            dy /= distance;
            x += dx * speed * deltaTime;
            y += dy * speed * deltaTime;
        }
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getValue() { return value; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public double getMagnetRadius() { return magnetRadius; }
}

