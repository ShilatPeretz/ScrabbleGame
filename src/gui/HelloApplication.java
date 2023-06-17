package com.example.gui;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.Console;
import java.io.IOException;
import java.util.Comparator;


public class HelloApplication extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    private enum bonus{TW,DW,TL,DL,RG,STAR}
   private static final int BOARD_SIZE = 15;
    private static final int TILE_SIZE = 40;

    private static final int WIDTH = 1920;

    private static final int HEIGHT = 1080;
    private bonus[][] bonuses={
            {bonus.TW, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.TW, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.TW},
            {bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG},
            {bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG},
            {bonus.DL, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.DL},
            {bonus.RG, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.RG},
            {bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG},
            {bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG},
            {bonus.TW, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.STAR, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.TW},
            {bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG},
            {bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG},
            {bonus.RG, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.RG},
            {bonus.DL, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.DL},
            {bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG, bonus.RG},
            {bonus.RG, bonus.DW, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.TL, bonus.RG, bonus.RG, bonus.RG, bonus.DW, bonus.RG},
            {bonus.TW, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.RG, bonus.TW, bonus.RG, bonus.RG, bonus.RG, bonus.DL, bonus.RG, bonus.RG, bonus.TW}
    };

    public Character[] letters_arr = new Character[7];


    //whole screen
    public BorderPane root = new BorderPane();

    //game board
    public GridPane board = createGridPane();

    //letters grid
    public GridPane letters = new GridPane();


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Scrabble Game");

        //restart the letters arr

        for (int i = 0; i < 7; i++) {
            letters_arr[i] = '0';
        }



        // Create the gameboard in the middle
        root.setCenter(board);
        board.setAlignment(Pos.CENTER);
        board.setHgap(0);
        board.setVgap(0);

        // Create player slots above the grid
        HBox playerSlots = createPlayerSlots();
        root.setTop(playerSlots);

        // Set up the scene
        Scene scene = new Scene(root, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setScene(scene);


        // Create a GridPane
        letters.setPadding(new Insets(10));
        letters.setHgap(10);
        letters.setVgap(10);

        // create the submit word button

        Button submitButton = new Button("Submit");

        // Set the button's style
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 100px; -fx-pref-height: 40px; -fx-cursor: hand;");

        // Set the button's action
        submitButton.setOnAction(event -> {
            // Perform submit action here
            System.out.println("Submit button clicked!");
        });

        // Add clicked animation
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), submitButton);
        scaleTransition.setToX(0.9);
        scaleTransition.setToY(0.9);
        scaleTransition.setAutoReverse(true);

        submitButton.setOnMousePressed(event -> {
            scaleTransition.playFromStart();
        });

        submitButton.setOnMouseReleased(event -> {
            scaleTransition.stop();
            submitButton.setScaleX(1.0);
            submitButton.setScaleY(1.0);
        });
        letters.add(submitButton,8,0);


        // Create buttons representing each letter of the English alphabet
        addTile('X');
        addTile('Y');
        addTile('A');
        addTile('X');
        addTile('Y');
        addTile('A');
        addTile('X');
        addTile('Y');
        addTile('A');
        // Add the grid to the bottom of the BorderPane
        root.setBottom(letters);
        letters.setAlignment(Pos.CENTER);




        primaryStage.show();
    }



    //board
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Rectangle tile = createTile(col,row);
                gridPane.add(tile, col, row);
            }
        }

        return gridPane;
    }

    private Rectangle createTile(int col,int row) {
        Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
        tile.setFill(Color.WHITE);
        tile.setStroke(Color.BLACK);
        switch (this.bonuses[row][col]) {
            case STAR:
                tile.setFill(Color.YELLOW);
                break;
            case TW:
                tile.setFill(Color.RED);
                break;
            case DW:
                tile.setFill(Color.YELLOW);
                break;

           case TL:
                tile.setFill(Color.ROYALBLUE);
                break;
            case DL:
                tile.setFill(Color.LIGHTSKYBLUE);
                break;
            case RG:
                tile.setFill(Color.LIMEGREEN);
                break;
       }






        return tile;
}


    private HBox createPlayerSlots() {
        HBox playerSlots = new HBox(20);
        playerSlots.setAlignment(Pos.CENTER);

        // Create player slot 1
        VBox playerSlot1 = createPlayerSlot("Player 1", "icon1.png", 100);
        playerSlots.getChildren().add(playerSlot1);

        // Create player slot 2
        VBox playerSlot2 = createPlayerSlot("Player 2", "icon2.png", 75);
        playerSlots.getChildren().add(playerSlot2);

        // Create player slot 3
        VBox playerSlot3 = createPlayerSlot("Player 3", "icon3.png", 90);
        playerSlots.getChildren().add(playerSlot3);

        // Create player slot 4
        VBox playerSlot4 = createPlayerSlot("Player 4", "icon4.png", 85);
        playerSlots.getChildren().add(playerSlot4);

        return playerSlots;
    }


    private VBox createPlayerSlot(String playerName, String iconFileName, int score) {
        VBox playerSlot = new VBox(10);
        playerSlot.setAlignment(Pos.CENTER);

//        // Create the icon image view
//        ImageView iconImageView = new ImageView(new Image(iconFileName));
//        iconImageView.setFitWidth(50);
//        iconImageView.setFitHeight(50);

        // Create the player name label
        Label nameLabel = new Label(playerName);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size:25px; ");

        // Create the score label
        Label scoreLabel = new Label("Score: " + score);
        scoreLabel.setStyle("-fx-font-size:15px");

        // Add components to the player slot
        playerSlot.getChildren().addAll(nameLabel, scoreLabel);

        return playerSlot;
    }


    private void addTile(Character letter) {

        for (int i = 0; i < 7; i++) {
            if(letters_arr[i].equals('0')){
                TileGUI tl = new TileGUI(TILE_SIZE,TILE_SIZE,letter);
                letters.add(tl,i,0);
                letters_arr[i] = letter;
                break;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

