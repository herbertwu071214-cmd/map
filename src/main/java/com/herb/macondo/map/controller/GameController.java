package com.herb.macondo.map.controller;

import com.herb.macondo.map.input.InputHandler;
import com.herb.macondo.map.model.GameModel;
import com.herb.macondo.map.view.GameView;
import javafx.animation.AnimationTimer;

public class GameController {
    private GameModel model;
    private GameView view;
    private InputHandler inputHandler;
    private AnimationTimer gameLoop;

    public GameController(GameModel model, GameView view, InputHandler inputHandler) {
        this.model = model;
        this.view = view;
        this.inputHandler = inputHandler;
    }

    public void start() {
        gameLoop = new AnimationTimer() {
            long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                update(deltaTime);
                view.render(model);
                lastUpdate = now;
            }
        };
        gameLoop.start();
    }

    private void update(double deltaTime) {
        double speed = model.getPlayerSpeed();
        double dx = 0;
        double dy = 0;

        if (inputHandler.isMovingUp()) dy -= speed;
        if (inputHandler.isMovingDown()) dy += speed;
        if (inputHandler.isMovingLeft()) dx -= speed;
        if (inputHandler.isMovingRight()) dx += speed;

        // Simple normalization if moving diagonally
        if (dx != 0 && dy != 0) {
            double factor = 1.0 / Math.sqrt(2);
            dx *= factor;
            dy *= factor;
        }

        double newX = model.getPlayerX() + dx * deltaTime;
        double newY = model.getPlayerY() + dy * deltaTime;

        // Basic boundary checks
        if (newX < 0) newX = 0;
        if (newX > 800) newX = 800;
        if (newY < 0) newY = 0;
        if (newY > 600) newY = 600;

        model.setPlayerX(newX);
        model.setPlayerY(newY);
    }
}
