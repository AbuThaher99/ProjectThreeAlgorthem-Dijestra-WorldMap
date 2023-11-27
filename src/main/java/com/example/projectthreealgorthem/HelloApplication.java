package com.example.projectthreealgorthem;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class HelloApplication extends Application {
    Dijkstra dijkstra = new Dijkstra();
    File file = null;
    int w = 1200;
    int h = 800;

    public void putTheCountry(Pane map , ChoiceBox<String> src_combo , ChoiceBox<String> dest_combo , TextArea path_field ) {


        for (Node node : dijkstra.getNodes().values()) {

//            double x = (node.y + 180) * (w / (360))-36;
//            double y = (90 - node.x) * (h / (180+20));


          double  y = h -(node.x + 90) /180 * h;

            double x= ( (node.y + 180) / 360 * w)-5;





            Button button = new Button(node.name);
            button.setOnMouseClicked(e -> {
                if (src_combo.getValue() == null) {
                    src_combo.setValue(node.name);
                } else if (dest_combo.getValue() == null) {
                    dest_combo.setValue(node.name);
                } else {
                    path_field.setText("Source and Destination are full");
                    Alert alert = new Alert(Alert.AlertType.NONE, "Source and Destination are Full ", ButtonType.OK);
                    if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                    }
                }
            });
            button.setStyle(
                    "-fx-background-radius: 10em; " +
                            "-fx-min-width: 7px; " +
                            "-fx-min-height: 7px; " +
                            "-fx-max-width: 7px; " +
                            "-fx-max-height: 7px;" +
                            "-fx-background-color: red"
            );
            Label label = new Label(node.name);
            label.setTextFill(Color.LIGHTBLUE);


            label.setLayoutX(x);
            label.setLayoutY(y - 15);
                button.setLayoutX(x);
                button.setLayoutY(y);

            map.getChildren().addAll( label,button);


        }



    }


    @Override
    public void start(Stage stage) throws IOException {


        Pane st8 = new Pane();
        Image mh8 = new Image("World-Map.jpg");
        ImageView mah8 = new ImageView(mh8);
        mah8.setFitWidth(1200);
        mah8.setFitHeight(800);




        Group gr = new Group();
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setPrefHeight(25);
        choiceBox.setPrefWidth(150);
        choiceBox.setLayoutX(1236);
        choiceBox.setLayoutY(27);


        ChoiceBox<String> choiceBox2 = new ChoiceBox<>();
        choiceBox2.setPrefHeight(25);
        choiceBox2.setPrefWidth(150);
        choiceBox2.setLayoutX(1236);
        choiceBox2.setLayoutY(92);



        TextArea textArea = new TextArea();
        textArea.setPrefWidth(150);
        textArea.setPrefHeight(157);
        textArea.setLayoutX(1236);
        textArea.setLayoutY(140);
        textArea.setEditable(false);

        TextField textField = new TextField();
        textField.setPrefWidth(150);
        textField.setPrefHeight(25);
        textField.setLayoutX(1236);
        textField.setLayoutY(310);
        textField.setEditable(false);

        Button rest = new Button("Clear");
        rest.setPrefWidth(50);
        rest.setPrefHeight(25);
        rest.setLayoutX(1301);
        rest.setLayoutY(415);
        rest.setOnAction(e->{
            choiceBox.setValue(null);
            choiceBox2.setValue(null);
            textArea.setText("");
            textField.setText("");
            gr.getChildren().clear();
        });


        Button Load = new Button("Load");
        Load.setPrefWidth(50);
        Load.setPrefHeight(25);
        Load.setLayoutX(1334);
        Load.setLayoutY(358);
        Load.setOnAction(e->{
            choiceBox.setValue(null);
            choiceBox2.setValue(null);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    dijkstra.read(file);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.NONE, "You must Chose a File ", ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
            }

            for (Node node : dijkstra.getNodes().values()) {
                choiceBox.getItems().add(node.name);
            }


            for (Node node : dijkstra.getNodes().values()) {
                choiceBox2.getItems().add(node.name);
            }



            ObservableList<String> items = choiceBox.getItems();
            Collections.sort(items);
            choiceBox.setItems(items);

            ObservableList<String> items2 = choiceBox2.getItems();
            Collections.sort(items2);
            choiceBox2.setItems(items2);

            ObservableList<String> items1 = choiceBox.getItems();
            items.sort(Comparator.naturalOrder());
            choiceBox.setItems(items1);

            ObservableList<String> items3 = choiceBox2.getItems();
            items2.sort(Comparator.naturalOrder());
            choiceBox2.setItems(items3);

            putTheCountry(st8,choiceBox,choiceBox2,textArea);

            Load.setDisable(true);


        });






        Button button = new Button("Run");
        button.setPrefWidth(50);
        button.setPrefHeight(25);
        button.setLayoutX(1260);
        button.setLayoutY(358);
        button.setOnAction(event -> {
            gr.getChildren().clear();

            try {


                textField.setText("");
                textArea.setText("");
                dijkstra.RunDijkstra(choiceBox.getValue(), choiceBox2.getValue());
                textArea.setText(dijkstra.getShortPath());
                textField.setText(String.valueOf(dijkstra.getShortPathWeight()));

                for (int i = 0; i < dijkstra.shortestPathNodes.length - 1; i++) {
                    Node temp = dijkstra.getNodes().get(dijkstra.shortestPathNodes[i]);
                    Node parent = dijkstra.getNodes().get(dijkstra.shortestPathNodes[i + 1]);


//                    double  y = h -(node.x + 90) /180 * h;
//                    double x=  (node.y + 180) / 360 * w;



                    double x = ((temp.y + 180) / 360 * w)-5;
                    double y = h -(temp.x + 90) /180 * h ;


                    double x1 = ((parent.y + 180) / 360 * w);
                    double y1 = h -(parent.x + 90) /180 * h;


                    Line line = new Line(x,y, x1, y1);
                    Polyline polyline = new Polyline(x,y, x1, y1);
                    line.setStroke(Color.RED);
                    line.setStrokeWidth(3);
                    polyline.setStroke(Color.RED);
                    polyline.setStrokeWidth(3);
                    gr.getChildren().add(polyline);


                }


                dijkstra.setShortPath("");
                dijkstra.shortestPathNodes = null;
                dijkstra.setShortPathWeight(0);


            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.NONE, "Can not Go from  " + choiceBox.getValue() + " to  " + choiceBox2.getValue(), ButtonType.OK);
                if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
                }
                dijkstra.setShortPath("");
                dijkstra.shortestPathNodes = null;
                dijkstra.setShortPathWeight(0);


            }
        });






        st8.getChildren().addAll(mah8, gr, choiceBox, choiceBox2, rest, button, Load,textArea, textField);
        Scene scene = new Scene(st8, 1400, 800);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setTitle("Dijkstra’s Algorithm!");
        stage.setScene(scene);
       // stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}