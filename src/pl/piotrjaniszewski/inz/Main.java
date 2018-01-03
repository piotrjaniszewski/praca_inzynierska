package pl.piotrjaniszewski.inz;

import pl.piotrjaniszewski.inz.Algorithm.EquipmentOptimizationAlgorithm;
import pl.piotrjaniszewski.inz.Head.EquipmentSingleArray;
import pl.piotrjaniszewski.inz.Workpiece.Workpiece;

public class Main {
    public static void main(String[] args) {
        Workpiece workpiece = new Workpiece("test1.txt");
        int populationSize = 20;
        double mutationProbability = 0;
        long duration = 180*1000;
        int headWidth = 8;
        int headHeight = 10;
        int numberOfDrills = 4;

        EquipmentOptimizationAlgorithm eoa = new EquipmentOptimizationAlgorithm(workpiece,populationSize,mutationProbability,duration,headWidth,headHeight,numberOfDrills);
        eoa.start();

        EquipmentSingleArray best = eoa.getBest();

        System.out.println(workpiece);
        System.out.println();
        System.out.println(best);
        System.out.println();
    }
}