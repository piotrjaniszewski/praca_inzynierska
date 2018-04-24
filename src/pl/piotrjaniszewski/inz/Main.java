package pl.piotrjaniszewski.inz;

import pl.piotrjaniszewski.inz.Algorithm.EquipmentOptimizationAlgorithm;
import pl.piotrjaniszewski.inz.Head.Equipment;
import pl.piotrjaniszewski.inz.Workpiece.Workpiece;

public class Main {
    public static void main(String[] args) {
        Workpiece workpiece = new Workpiece("p9.txt");
        int populationSize = 30;
        long duration = 5*60*60*1000;
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