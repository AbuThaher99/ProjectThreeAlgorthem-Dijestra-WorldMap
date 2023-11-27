package com.example.projectthreealgorthem;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dijkstra {
    private Map<String, Node> nodes = new HashMap<>();
    private int numVertices, numEdges;
    private File file;
    private String ShortPath = "";
    private double ShortPathWeight = 0;
    List<Edge> edges = new ArrayList<>();
    String[] shortestPathNodes;


    public static final double R = 6371; // Earth's radius in kilometers
    public void read(File file) throws FileNotFoundException {

        Scanner scanner = new Scanner(file);
        String d= scanner.nextLine().trim();
        String[] arr= d.split(",");
        numVertices = Integer.parseInt(arr[0].trim());
        numEdges = Integer.parseInt(arr[1].trim());
        System.out.println("numVertices = " + numVertices);
        System.out.println("numEdges = " + numEdges);

        // Read in the details of each vertex
        for (int i = 0; i < numVertices; i++) {
            String line = scanner.nextLine().trim();
            String [] arr1 = line.split(",");
            String name = arr1[0].trim();
            double x = Double.parseDouble(arr1[1].trim());
            double y = Double.parseDouble(arr1[2].trim());

            Node node = new Node(name, x, y);
            nodes.put(name, node);
        }
        System.out.println("nodes = " + nodes.toString());

        for (int i = 0; i < numEdges; i++) {
            String line = scanner.nextLine().trim();
            String [] arr2 = line.split(",");
            String from = arr2[0].trim();
            String to = arr2[1].trim();
            Node fromNode = nodes.get(from);
            Node toNode = nodes.get(to);
//           double weight = Math.sqrt(Math.pow(toNode.x - fromNode.x, 2) + Math.pow(toNode.y - fromNode.y, 2));
            double wht = distance(toNode.x, toNode.y, fromNode.x, fromNode.y);
            fromNode.addEdge(toNode, wht);
            edges.add(new Edge(fromNode, toNode, wht));
        }




    }



    public void RunDijkstra(String source, String destination) {
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>((Node n1, Node n2) -> Double.compare(distances.get(n1), distances.get(n2)));
        Map<Node, Boolean> visited = new HashMap<>();

        // Only re-initialize the distance for nodes that have changed in the previous query
        Set<Node> nodesToReinitialize = new HashSet<>();

        Node sourceNode = nodes.get(source);
        if (sourceNode == null) {
            System.out.println("Error: Source node not found in the graph");
            return;
        }

        distances.put(sourceNode, 0.0);
        queue.add(sourceNode);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            visited.put(current, true);

            if (current.name.equals(destination)) {

                break;
            }

            for (Edge edge : current.edges) {
                Node neighbor = edge.to;
                if (!visited.containsKey(neighbor)) {
                    distances.put(neighbor, Double.POSITIVE_INFINITY);
                    previous.put(neighbor, null);
                    visited.put(neighbor, false);
                    //nodesToReinitialize.add(neighbor);
                }

                double newDistance = distances.get(current) + edge.weight;
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        Node destNode = nodes.get(destination);
        if (destNode == null) {
            System.out.println("Error: Destination node not found in the graph");
            return;
        }

        Node current = destNode;
        Stack<String> stack = new Stack<>();
        while (current != null) {
            if (current != null) {
                stack.push(current.name);
            }
            current = previous.get(current);
        }
         shortestPathNodes = new String[stack.size()];
        for (int i = 0; i < shortestPathNodes.length; i++) {
            shortestPathNodes[i] = stack.pop();
        }



        for(int i =0; i < shortestPathNodes.length; i++){
            if(i == shortestPathNodes.length - 1){
                ShortPath += shortestPathNodes[i];
            }
            else{
                ShortPath += shortestPathNodes[i] + " -> ";
            }

        }



        this.ShortPathWeight = distances.get(destNode);

        distances.clear();
        previous.clear();
        visited.clear();
    }
//public void runDijkstra(String source, String destination) {
//    Map<Node, Double> distances = new HashMap<>();
//    Map<Node, Node> previous = new HashMap<>();
//    PriorityQueue<Node> queue = new PriorityQueue<>((Node n1, Node n2) -> Double.compare(distances.get(n1), distances.get(n2)));
//    Map<Node, Boolean> visited = new HashMap<>();
//
//    Node sourceNode = nodes.get(source);
//    if (sourceNode == null) {
//        System.out.println("Error: Source node not found in the graph");
//        return;
//    }
//
//    // Set the initial distance for the source node to 0
//    distances.put(sourceNode, 0.0);
//    queue.add(sourceNode);
//    while (!queue.isEmpty()) {
//        Node current = queue.poll();
//        visited.put(current, true);
//
//        if (current.name.equals(destination)) {
//            break;
//        }
//
//        for (Edge edge : current.edges) {
//            Node neighbor = edge.to;
//            if (!visited.containsKey(neighbor)) {
//                distances.put(neighbor, Double.POSITIVE_INFINITY);
//                previous.put(neighbor, null);
//                visited.put(neighbor, false);
//            }
//
//            double newDistance = distances.get(current) + edge.weight;
//            if (newDistance < distances.get(neighbor)) {
//                distances.put(neighbor, newDistance);
//                previous.put(neighbor, current);
//                queue.add(neighbor);
//            }
//        }
//    }
//
//    Node destNode = nodes.get(destination);
//    if (destNode == null) {
//        System.out.println("Error: Destination node not found in the graph");
//        return;
//    }
//
//    Node current = destNode;
//    Stack<String> stack = new Stack<>();
//    while (current != null) {
//        stack.push(current.name);
//        current = previous.get(current);
//    }
//
//    shortestPathNodes = new String[stack.size()];
//    for (int i = 0; i < shortestPathNodes.length; i++) {
//        shortestPathNodes[i] = stack.pop();
//    }
//
//    for(int i = 0; i < shortestPathNodes.length; i++){
//        if(i == shortestPathNodes.length - 1){
//            ShortPath += shortestPathNodes[i];
//        }
//        else{
//            ShortPath += shortestPathNodes[i] + " -> ";
//        }
//    }
//    this.ShortPathWeight = distances.get(destNode);
//
//}
//


    private static double calculateEuclideanDistance(double x1, double y1, double x2, double y2) {

        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }




    public Map<String, Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, Node> nodes) {
        this.nodes = nodes;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public void setNumEdges(int numEdges) {
        this.numEdges = numEdges;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getShortPath() {
        return ShortPath;
    }

    public void setShortPath(String shortPath) {
        ShortPath = shortPath;
    }

    public double getShortPathWeight() {
        return ShortPathWeight;
    }

    public void setShortPathWeight(double shortPathWeight) {
        ShortPathWeight = shortPathWeight;
    }


}