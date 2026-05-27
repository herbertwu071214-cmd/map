package com.herb.macondo.map.view;

import com.herb.macondo.map.model.GameModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameView {
    private Canvas canvas;

    public GameView(Canvas canvas) {
        this.canvas = canvas;
    }

    public void render(GameModel model) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.BLUE);
        double playerScreenX = model.getPlayerX();
        double playerScreenY = model.getPlayerY();
        gc.fillRect(playerScreenX - 15, playerScreenY - 15, 30, 30);
    }
}
