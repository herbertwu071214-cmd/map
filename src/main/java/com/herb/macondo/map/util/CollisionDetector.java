package com.herb.macondo.map.util;

import com.herb.macondo.map.model.Obstacle;
import java.util.List;

public class CollisionDetector {

    public static boolean collidesWithAnyObstacle(double playerX, double playerY,
                                                  double playerWidth, double playerHeight,
                                                  List<Obstacle> obstacles) {
        for (Obstacle obs : obstacles) {
            if (obs.intersects(playerX, playerY, playerWidth, playerHeight)) {
                return true;
            }
        }
        return false;
    }

    public static boolean canMoveTo(double newX, double newY,
                                    double playerWidth, double playerHeight,
                                    List<Obstacle> obstacles) {
        return !collidesWithAnyObstacle(newX, newY, playerWidth, playerHeight, obstacles);
    }
}

