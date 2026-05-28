package com.herb.macondo.map.view;

import com.herb.macondo.map.model.GameModel;
import com.herb.macondo.map.model.Obstacle;
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

    public void render(GameModel model, Text modeText) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        cameraX = model.getPlayerX() - canvas.getWidth() / 2;
        cameraY = model.getPlayerY() - canvas.getHeight() / 2;

        double playerScreenX = model.getPlayerX() - cameraX;
        double playerScreenY = model.getPlayerY() - cameraY;

        gc.setFill(Color.BLUE);
        gc.fillRect(playerScreenX - 15, playerScreenY - 15, 30, 30);

        for (Obstacle obs : model.getObstacles()) {
            double screenX = obs.getX() - cameraX;
            double screenY = obs.getY() - cameraY;
            gc.setFill(Color.GRAY);
            gc.fillRect(screenX, screenY, obs.getWidth(), obs.getHeight());
        }

        gc.setFill(Color.WHITE);
        gc.fillText(modeText.getText(), 10, 20);
    }
}
