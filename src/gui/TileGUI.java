package com.example.gui;

import javafx.geometry.Bounds;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TileGUI extends GridPane {
    private double mouseX;
    private double mouseY;
    private double initialTranslateX;
    private double initialTranslateY;
    private Text text;

    public TileGUI(double width, double height) {
        setAlignment(Pos.CENTER);
        setTranslateX(0);
        setTranslateY(0);

        Rectangle tile = new Rectangle(width, height);
        tile.setFill(Color.BLACK);
        tile.setStroke(Color.BLACK);

        text = new Text("0");
        text.setFont(Font.font("Arial", 20));
        GridPane.setHalignment(text, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(text, javafx.geometry.VPos.CENTER);

        text.setFill(Color.WHITE);
        getChildren().addAll(tile, text);

        setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
            initialTranslateX = getTranslateX();
            initialTranslateY = getTranslateY();
        });

        setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseX;
            double deltaY = event.getSceneY() - mouseY;
            setTranslateX(initialTranslateX + deltaX);
            setTranslateY(initialTranslateY + deltaY);
        });

        setOnMouseReleased(event -> {
            double snappedX = Math.round(getTranslateX() / (width)) * (width + 1);
            double snappedY = Math.round(getTranslateY() / (width)) * (width + 1);

            setTranslateX(snappedX);
            setTranslateY(snappedY);
        });
    }
}



//if(getTranslateX() > width * 15 || getTranslateY() > width * 15 || getTranslateX() < -width*0.5 ||getTranslateY() < -width*0.5){
//                System.out.println("bug");
//
//                return;
//            }