package pl.piotrjaniszewski.inz;

import pl.piotrjaniszewski.inz.Algorithm.EquipmentOptimizationAlgorithm;
import pl.piotrjaniszewski.inz.Head.EquipmentSingleArray;
import pl.piotrjaniszewski.inz.Workpiece.Workpiece;

public class Main {
    public static void main(String[] args) {
        Workpiece workpiece = new Workpiece("test1.txt");
        int populationSize = 30;
        double mutationProbability = 0;
        long duration = 60*1000;
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

        for (int i = 0; i < best.getHeadPositions().size(); i++) {
            System.out.println((i+1)+" "+best.getHeadPositions().get(i).getX()+" "+best.getHeadPositions().get(i).getY());
        }
    }
}