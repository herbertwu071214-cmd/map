package com.herb.macondo.map.view;

import com.herb.macondo.map.model.GameModel;
import com.herb.macondo.map.model.Obstacle;
import com.herb.macondo.map.model.Enemy;
import com.herb.macondo.map.model.Projectile;
import com.herb.macondo.map.model.ExpOrb;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameView {
    private Canvas canvas;
    private double cameraX;
    private double cameraY;

    public GameView(Canvas canvas) {
        this.canvas = canvas;
    }

    public double getCameraX() { return cameraX; }
    public double getCameraY() { return cameraY; }

    public void render(GameModel model, Text modeText, boolean paused) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        cameraX = model.getPlayerX() - canvas.getWidth() / 2;
        cameraY = model.getPlayerY() - canvas.getHeight() / 2;

        double playerScreenX = model.getPlayerX() - cameraX;
        double playerScreenY = model.getPlayerY() - cameraY;

        for (Obstacle obs : model.getObstacles()) {
            double screenX = obs.getX() - cameraX;
            double screenY = obs.getY() - cameraY;
            gc.setFill(Color.GRAY);
            gc.fillRect(screenX, screenY, obs.getWidth(), obs.getHeight());
        }

        for (Enemy enemy : model.getEnemies()) {
            double screenX = enemy.getX() - cameraX;
            double screenY = enemy.getY() - cameraY;
            gc.setFill(Color.RED);
            gc.fillRect(screenX - enemy.getWidth()/2, screenY - enemy.getHeight()/2, enemy.getWidth(), enemy.getHeight());

            double healthPercent = enemy.getHealth() / enemy.getMaxHealth();
            gc.setFill(Color.GREEN);
            gc.fillRect(screenX - enemy.getWidth()/2, screenY - enemy.getHeight()/2 - 8, enemy.getWidth() * healthPercent, 4);
        }

        for (Projectile p : model.getProjectiles()) {
            double screenX = p.getX() - cameraX;
            double screenY = p.getY() - cameraY;
            gc.setFill(Color.YELLOW);
            gc.fillOval(screenX - p.getSize()/2, screenY - p.getSize()/2, p.getSize(), p.getSize());
        }

        for (ExpOrb orb : model.getExpOrbs()) {
            double screenX = orb.getX() - cameraX;
            double screenY = orb.getY() - cameraY;
            gc.setFill(Color.LIMEGREEN);
            gc.fillOval(screenX - 6, screenY - 6, 12, 12);
        }

        gc.setFill(Color.BLUE);
        gc.fillRect(playerScreenX - 15, playerScreenY - 15, 30, 30);

        double healthPercent = model.getPlayerHealth() / model.getPlayerMaxHealth();
        gc.setFill(Color.RED);
        gc.fillRect(playerScreenX - 20, playerScreenY - 25, 40, 6);
        gc.setFill(Color.GREEN);
        gc.fillRect(playerScreenX - 20, playerScreenY - 25, 40 * healthPercent, 6);

        gc.setFill(Color.WHITE);
        gc.fillText("HP: " + (int)model.getPlayerHealth() + " / " + (int)model.getPlayerMaxHealth(), 10, 50);
        gc.fillText("Level: " + model.getPlayerLevel() + "  Exp: " + (int)model.getCurrentExp() + " / " + (int)model.getExpToNextLevel(), 10, 80);
        gc.fillText(modeText.getText(), 10, 20);

        if (paused) {
            gc.setFill(Color.rgb(0, 0, 0, 0.7));
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.setFill(Color.WHITE);
            gc.setFont(javafx.scene.text.Font.font(24));
            gc.fillText("PAUSED", canvas.getWidth()/2 - 40, canvas.getHeight()/2);
            if (model.isUpgradePending()) {
                gc.setFont(javafx.scene.text.Font.font(18));
                gc.fillText("Choose an upgrade", canvas.getWidth()/2 - 70, canvas.getHeight()/2 + 40);
            }
        }
    }
}
