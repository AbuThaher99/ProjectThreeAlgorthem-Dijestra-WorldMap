package com.example.projectthreealgorthem;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class MapViewer {

    private ImageView imageView;
    private Canvas canvas;
    private GraphicsContext gc;
    Dijkstra dijkstra = new Dijkstra();


    public MapViewer(String imageFile, String[] shortestPathNodes) {
        Image image = new Image(imageFile);
        imageView = new ImageView(image);
        imageView.setFitWidth(1200);
        imageView.setFitHeight(715);

        canvas = new Canvas(image.getWidth(), image.getHeight());
        gc = canvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0);

        drawShortestPath(shortestPathNodes);

        imageView.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            System.out.println("Clicked on x: " + x + " y: " + y);
        });
    }

    private void drawShortestPath(String[] shortestPathNodes) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(5);

        double[] xPoints = new double[shortestPathNodes.length];
        double[] yPoints = new double[shortestPathNodes.length];

        // assuming that `getX(node)` and `getY(node)` methods exist and return the coordinates of the node
        for (int i = 0; i < shortestPathNodes.length; i++) {
            xPoints[i] = dijkstra.getNodes().get(shortestPathNodes[i]).x;
            yPoints[i] = dijkstra.getNodes().get(shortestPathNodes[i]).y;
        }

        gc.strokePolyline(xPoints, yPoints, shortestPathNodes.length);
    }

    public ImageView getView() {
        return imageView;
    }
}