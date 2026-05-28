package com.herb.macondo.map.controller;

import com.herb.macondo.map.input.InputHandler;
import com.herb.macondo.map.model.GameModel;
import com.herb.macondo.map.view.GameView;
import com.herb.macondo.map.util.CollisionDetector;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

public class GameController {
    private GameModel model;
    private GameView view;
    private InputHandler input;
    private AnimationTimer gameLoop;
    private Scene scene;
    private Text modeText;

    public GameController(GameModel model, GameView view, Scene scene) {
        this.model = model;
        this.view = view;
        this.scene = scene;
        this.input = new InputHandler();
        this.modeText = new Text();
        modeText.setFill(javafx.scene.paint.Color.WHITE);
        modeText.setX(10);
        modeText.setY(20);
    }

    public void start() {
        scene.setOnKeyPressed(e -> {
            input.keyPressed(e.getCode());
            if (e.getCode() == javafx.scene.input.KeyCode.M) {
                model.setUseMouseControl(!model.isUseMouseControl());
                updateModeText();
            }
        });
        scene.setOnKeyReleased(e -> input.keyReleased(e.getCode()));

        scene.setOnMouseMoved(e -> {
            if (model.isUseMouseControl()) {
                double mouseWorldX = e.getX() + view.getCameraX();
                double mouseWorldY = e.getY() + view.getCameraY();
                model.setPlayerPosition(mouseWorldX, mouseWorldY);
            }
        });

        scene.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                System.out.println("Shoot later");
            }
        });

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
                view.render(model, modeText);
                lastUpdate = now;
            }
        };
        gameLoop.start();
        updateModeText();
    }

    private void updateModeText() {
        if (model.isUseMouseControl()) {
            modeText.setText("Control: MOUSE (press M for WASD)");
        } else {
            modeText.setText("Control: WASD (press M for MOUSE)");
        }
    }

    private void update(double deltaTime) {
        if (model.isUseMouseControl()) {
            return;
        }

        double dx = 0, dy = 0;
        double speed = model.getPlayerSpeed();

        if (!input.hasOpposingHorizontal()) {
            if (input.isMovingLeft()) dx = -1;
            if (input.isMovingRight()) dx = 1;
        }
        if (!input.hasOpposingVertical()) {
            if (input.isMovingUp()) dy = -1;
            if (input.isMovingDown()) dy = 1;
        }

        if (dx != 0 && dy != 0) {
            dx *= 0.7071;
            dy *= 0.7071;
        }

        double moveX = dx * speed * deltaTime;
        double moveY = dy * speed * deltaTime;

        double newX = model.getPlayerX() + moveX;
        double newY = model.getPlayerY() + moveY;

        if (CollisionDetector.canMoveTo(newX - 15, newY - 15, 30, 30, model.getObstacles())) {
            model.movePlayer(moveX, moveY, 800, 600);
        } else {
            double tryX = model.getPlayerX() + moveX;
            if (CollisionDetector.canMoveTo(tryX - 15, model.getPlayerY() - 15, 30, 30, model.getObstacles())) {
                model.movePlayer(moveX, 0, 800, 600);
            }
            double tryY = model.getPlayerY() + moveY;
            if (CollisionDetector.canMoveTo(model.getPlayerX() - 15, tryY - 15, 30, 30, model.getObstacles())) {
                model.movePlayer(0, moveY, 800, 600);
            }
        }
    }
}
