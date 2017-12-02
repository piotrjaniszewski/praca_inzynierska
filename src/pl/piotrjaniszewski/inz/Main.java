package pl.piotrjaniszewski.inz;

import pl.piotrjaniszewski.inz.Algorithm.EquipmentOptimizationAlgorithm;
import pl.piotrjaniszewski.inz.Head.EquipmentSingleArray;
import pl.piotrjaniszewski.inz.Workpiece.Hole;
import pl.piotrjaniszewski.inz.Workpiece.Workpiece;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Workpiece workpiece = new Workpiece("test1.txt");
        int populationSize = 10;
        double mutationProbability = 0;
        long duration = 120*1000;
        int headWidth = 4;
        int headHeight = 5;
        int numberOfDrills = 4;

        EquipmentOptimizationAlgorithm eoa = new EquipmentOptimizationAlgorithm(workpiece,populationSize,mutationProbability,duration,headWidth,headHeight,numberOfDrills);
        eoa.start();

        EquipmentSingleArray best = eoa.getBest();
        List<Hole> holes = workpiece.getHolesList();

        System.out.println(workpiece);
        System.out.println();
        System.out.println(best);
        System.out.println();
        System.out.println("Liczba krokow: " + best.getNumberOfSteps(workpiece.getHeadPositionsWithMinimal(headWidth,headHeight,2),holes));
        System.out.println(workpiece.isDrilled(best.getHeadPositions(workpiece.getHeadPositionsWithMinimal(headWidth,headHeight,2),holes)));



    }
}