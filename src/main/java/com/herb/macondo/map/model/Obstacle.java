package com.herb.macondo.map.model;

public class Obstacle {
    private double x;
    private double y;
    private double width;
    private double height;

    public Obstacle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public boolean intersects(double px, double py, double pw, double ph) {
        return px < x + width && px + pw > x &&
                py < y + height && py + ph > y;
    }
}
