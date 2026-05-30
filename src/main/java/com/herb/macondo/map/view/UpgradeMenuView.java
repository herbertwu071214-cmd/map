package com.herb.macondo.map.view;

import com.herb.macondo.map.model.Upgrade;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UpgradeMenuView {
    private Stage menuStage;
    private Upgrade selectedUpgrade;

    public Upgrade showAndWait(Upgrade upgrade1, Upgrade upgrade2, Upgrade upgrade3) {
        selectedUpgrade = null;

        menuStage = new Stage();
        menuStage.initModality(Modality.APPLICATION_MODAL);
        menuStage.initStyle(StageStyle.UNDECORATED);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: rgba(0,0,0,0.9); -fx-border-color: gold; -fx-border-width: 3;");
        root.setPrefSize(500, 400);

        Label title = new Label("LEVEL UP!");
        title.setFont(Font.font("Arial", 30));
        title.setTextFill(Color.GOLD);

        Label subtitle = new Label("Choose an upgrade");
        subtitle.setFont(Font.font("Arial", 16));
        subtitle.setTextFill(Color.WHITE);

        VBox buttonsBox = new VBox(15);
        buttonsBox.setAlignment(Pos.CENTER);

        Button btn1 = createUpgradeButton(upgrade1);
        Button btn2 = createUpgradeButton(upgrade2);
        Button btn3 = createUpgradeButton(upgrade3);

        btn1.setOnAction(e -> {
            selectedUpgrade = upgrade1;
            menuStage.close();
        });
        btn2.setOnAction(e -> {
            selectedUpgrade = upgrade2;
            menuStage.close();
        });
        btn3.setOnAction(e -> {
            selectedUpgrade = upgrade3;
            menuStage.close();
        });

        buttonsBox.getChildren().addAll(btn1, btn2, btn3);
        root.getChildren().addAll(title, subtitle, buttonsBox);

        Scene scene = new Scene(root);
        menuStage.setScene(scene);
        menuStage.showAndWait();

        return selectedUpgrade;
    }

    private Button createUpgradeButton(Upgrade upgrade) {
        String text = upgrade.getName() + "\n" + upgrade.getDescription();
        Button btn = new Button(text);
        btn.setWrapText(true);
        btn.setTextAlignment(TextAlignment.CENTER);
        btn.setPrefWidth(300);
        btn.setPrefHeight(70);
        btn.setStyle("-fx-font-size: 14px; -fx-background-color: #2c3e50; -fx-text-fill: white; -fx-border-color: #f1c40f;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-font-size: 14px; -fx-background-color: #34495e; -fx-text-fill: yellow; -fx-border-color: #f1c40f;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-font-size: 14px; -fx-background-color: #2c3e50; -fx-text-fill: white; -fx-border-color: #f1c40f;"));
        return btn;
    }
}

