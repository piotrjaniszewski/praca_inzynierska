package pl.piotrjaniszewski.inz.algorithm;

import pl.piotrjaniszewski.inz.head.Equipment;
import pl.piotrjaniszewski.inz.head.HeadPosition;
import pl.piotrjaniszewski.inz.workpiece.Workpiece;

import java.util.*;

public class EquipmentOptimizationAlgorithm {
    private Workpiece workpiece;
    private int populationSize;
    private long duration;
    private int headWidth;
    private int headHeight;
    private int numberOfDrills;

    private List<HeadPosition> anyHeadPositions;

    private Equipment best;
    private List<Equipment> population;

    public EquipmentOptimizationAlgorithm(Workpiece workpiece, int populationSize, long duration, int headWidth, int headHeight, int numberOfDrills) {
        this.workpiece = workpiece;
        this.populationSize = populationSize;
        this.duration = duration;
        this.headWidth = headWidth;
        this.headHeight = headHeight;
        this.numberOfDrills = numberOfDrills;
        this.anyHeadPositions = this.workpiece.getHeadPositions(this.headWidth, this.headHeight, 2);
    }

    public void start() {
        System.out.println("Rozpoczęcie działania algorytmu.");
        System.out.println();
        long startTime = System.currentTimeMillis();
        //populacja startowa
        population = new LinkedList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(new Equipment(headWidth, headHeight, numberOfDrills, anyHeadPositions, workpiece));
        }
        best = population.get(0);
        while (System.currentTimeMillis() - startTime < duration) {
            List<Equipment> newPopulation = new LinkedList<>();
            //krzyzowanie
            for (int i = 0; i < population.size() - 1; i++) {
                for (int j = i + 1; j < population.size() - 1; j++) {
                    newPopulation.add(new Equipment(population.get(i), population.get(j)));
                }
            }
            newPopulation.add(population.get(0)); //dodawanie najlepszego
            //eliminacja
            newPopulation.sort((o1, o2) -> {
                if (o1.improvement(population.get(0)) > o2.improvement(population.get(0))) {
                    return -1;
                } else if (o1.improvement(population.get(0)) < o2.improvement(population.get(0))) {
                    return 1;
                } else {
                    return 0;
                }
            });

            List<Equipment> selectedPopulation = new LinkedList<>();

            for (int i = 0; i < populationSize; i++) {
                selectedPopulation.add(newPopulation.get(i));
            }

            this.population = selectedPopulation;
            if(population.get(0).improvement(best) > 0){
                best=population.get(0);
                System.out.println("population.get(0).getNumberOfSteps() = " + population.get(0).getNumberOfSteps());
                System.out.println("population.get(0).getPathLength() = " + population.get(0).getPathLength());
                System.out.println("duration = " + (System.currentTimeMillis()-startTime));
                System.out.println();
            }
        }
    }
    public Equipment getBest() {
        return population.get(0);
    }
}