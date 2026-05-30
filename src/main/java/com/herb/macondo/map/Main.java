package com.herb.macondo.map;

import com.herb.macondo.map.controller.GameController;
import com.herb.macondo.map.model.GameModel;
import com.herb.macondo.map.view.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(800, 600);
        GameModel model = new GameModel();
        GameView view = new GameView(canvas);

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, 800, 600);

        GameController controller = new GameController(model, view, scene);

        primaryStage.setTitle("Macondo");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

