package pl.piotrjaniszewski.inz;

import pl.piotrjaniszewski.inz.Algorithm.EqiupmentOptimalisationAlgorithm;
import pl.piotrjaniszewski.inz.Head.EquipmentSingleArray;
import pl.piotrjaniszewski.inz.Workpiece.Hole;
import pl.piotrjaniszewski.inz.Workpiece.Workpiece;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Workpiece workpiece = new Workpiece("test1.txt");
        int populationSize = 100;
        double mutationProbability = 0;
        long duration = 1*1000;
        int headWidth = 6;
        int headHeight = 8;
        int numberOfDrills = 2;

        EqiupmentOptimalisationAlgorithm eoa = new EqiupmentOptimalisationAlgorithm(workpiece,populationSize,mutationProbability,duration,headWidth,headHeight,numberOfDrills);
        eoa.start();

        EquipmentSingleArray best = eoa.getBest();
        List<Hole> holes = workpiece.getHolesList();

        System.out.println(workpiece);
        System.out.println();
        System.out.println(best);
        System.out.println();
        System.out.println("Liczba krokow: "+ best.getNumberOfSteps(workpiece.getAnyHeadPositions(headWidth,headHeight),holes));
        System.out.println();
    }
}