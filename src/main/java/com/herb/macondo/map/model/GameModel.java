package com.herb.macondo.map.model;

public class GameModel {
    private double playerX = 400;
    private double playerY = 300;
    private double playerSpeed = 300; // pixels per second

    public double getPlayerX() { return playerX; }
    public double getPlayerY() { return playerY; }
    public void setPlayerX(double x) { playerX = x; }
    public void setPlayerY(double y) { playerY = y; }
    public double getPlayerSpeed() { return playerSpeed; }
}
