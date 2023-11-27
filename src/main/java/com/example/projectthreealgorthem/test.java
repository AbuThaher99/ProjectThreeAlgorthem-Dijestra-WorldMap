package com.example.projectthreealgorthem;

import javafx.scene.effect.Light;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
//import java.util.ArrayList;
//import java.util.List;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.imgcodecs.Imgcodecs;

public class test {
    public static void main(String[] args) throws IOException {

        String[] countries = {  "Argentina", "Brazil", "Chile", "China", "Algeria",
                "Egypt", "Greenland",  "Iran",   "South Korea",
                "Kazakhstan", "Canada",  "Mongolia",  "Niger", "Russia", "Saudi Arabia",
                 "South Africa", "India",  "Libya", "Japan"  ,"Peru"  , "Jordan"
       ,"France"  ,"Sweden","Mali","Sudan","Ethiopia","Finland","Spain","Portugal"
                ,"Morocco","Pakistan" ,"Norway","North Sweden","Ukraine", "Palestine", "Yemen", "Oman", "Iraq",
                "Turkey","Indonesia","Malaysia","Italy","UK","Mauritania","Syrian Arab Republic","Tunisia","Gana","Genia","Somalia","USA"};
        System.out.println("countries.length = " + countries.length);
        generateRandomEdges(countries);



        // write a code that take a multiple cordination and put marker on it using webView









    }

    public static void generateRandomEdges(String[] countries) {
        Random random = new Random();
        int n = countries.length;
        HashSet<String> uniqueEdges = new HashSet<>();

        while (uniqueEdges.size() < n * 2) {
            int i = random.nextInt(n);
            int j = random.nextInt(n);
            if (i != j) {
                uniqueEdges.add(countries[i] + "," + countries[j]);
            }
        }
        System.out.println("uniqueEdges.size() = " + uniqueEdges.size());

        for (String edge : uniqueEdges) {
            System.out.println(edge);
        }
    }

}
