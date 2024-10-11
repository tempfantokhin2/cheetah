package com.example.cheetah.animals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Gazelle> gazelles = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.println("To stop creating gazelles enter 'q' (quit)");
        while (true){
            System.out.print("Creating gazelle " + (gazelles.size()+1) + ". name speed: ");
            String inputLine = scanner.nextLine();

            if ("q".equalsIgnoreCase(inputLine)) {
                break;
            }
            String[] inputData = inputLine.split(" ");
            gazelles.add(new Gazelle(inputData[0], Double.parseDouble(inputData[1])));
        }
        System.out.println("gazelles state:\n"+gazelles);


        Cheetah testCheetah1 = new Cheetah("riversideslayer14", 6, 98);

        System.out.println("Hunt 1:");
        System.out.println(testCheetah1.doHunt(gazelles));
        System.out.println("gazelles state:\n"+gazelles);

        System.out.println("Hunt 2:");
        System.out.println(testCheetah1.doHunt(gazelles));
        System.out.println("gazelles state:\n"+gazelles);
    }
}