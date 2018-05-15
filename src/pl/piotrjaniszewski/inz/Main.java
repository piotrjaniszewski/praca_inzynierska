package pl.piotrjaniszewski.inz;

import pl.piotrjaniszewski.inz.algorithm.EquipmentOptimizationAlgorithm;
import pl.piotrjaniszewski.inz.head.Equipment;
import pl.piotrjaniszewski.inz.workpiece.Workpiece;

public class Main {
    public static void main(String[] args) {
        Workpiece workpiece = new Workpiece("examples/p9.txt");
        int populationSize = 30;
        long duration = 2*60*1000;
        int headWidth = 8;
        int headHeight = 7;
        int numberOfDrills = 5;

        EquipmentOptimizationAlgorithm eoa = new EquipmentOptimizationAlgorithm(workpiece,populationSize,duration,headWidth,headHeight,numberOfDrills);
        eoa.start();

        Equipment best = eoa.getBest();

        System.out.println(workpiece);
        System.out.println();
        System.out.println(best);
        System.out.println();

        for (int i = 0; i < best.getHeadPositions().size(); i++) {
            System.out.println((i+1)+" "+best.getHeadPositions().get(i).getX()+" "+best.getHeadPositions().get(i).getY());
        }

        System.out.println("best.getNumberOfSteps() = " + best.getNumberOfSteps());
        System.out.println("best.getPathLength() = " + best.getPathLength());
    }
}