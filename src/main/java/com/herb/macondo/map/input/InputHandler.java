package com.herb.macondo.map.input;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.input.KeyCode;

public class InputHandler {
    private Set<KeyCode> activeKeys = new HashSet<>();

    public void keyPressed(KeyCode code) {
        activeKeys.add(code);
    }

    public void keyReleased(KeyCode code) {
        activeKeys.remove(code);
    }

    public boolean isMovingUp() {
        return activeKeys.contains(KeyCode.W);
    }

    public boolean isMovingDown() {
        return activeKeys.contains(KeyCode.S);
    }

    public boolean isMovingLeft() {
        return activeKeys.contains(KeyCode.A);
    }

    public boolean isMovingRight() {
        return activeKeys.contains(KeyCode.D);
    }

    public boolean hasOpposingHorizontal() {
        return activeKeys.contains(KeyCode.A) && activeKeys.contains(KeyCode.D);
    }

    public boolean hasOpposingVertical() {
        return activeKeys.contains(KeyCode.W) && activeKeys.contains(KeyCode.S);
    }
}

