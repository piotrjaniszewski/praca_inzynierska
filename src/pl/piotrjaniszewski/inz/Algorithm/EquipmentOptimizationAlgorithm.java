package pl.piotrjaniszewski.inz.Algorithm;

import pl.piotrjaniszewski.inz.Head.Equipment;
import pl.piotrjaniszewski.inz.Head.HeadPosition;
import pl.piotrjaniszewski.inz.Workpiece.Workpiece;

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
    private Random rnd = new Random();

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
        long startTime = System.currentTimeMillis();
        //populacja startowa
        population = new LinkedList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(new Equipment(headWidth, headHeight, numberOfDrills, anyHeadPositions, workpiece));
        }
        best = population.get(0);
        bestSteps=best.getNumberOfSteps();
        bestLength= best.getPathLength();
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

    double bestLength;
    double bestSteps;

    public Equipment getBest() {
        return population.get(0);
    }
    private Equipment getEquipment(){
        Equipment equipment1 = population.get(rnd.nextInt(populationSize-1));
        Equipment equipment2 = population.get(rnd.nextInt(populationSize-1));

        if(equipment1.getNumberOfSteps()>equipment2.getNumberOfSteps()){
            return equipment1;
        } else {
            return equipment2;
        }
    }
    private Equipment getEquipment(Equipment equipment1){

        Equipment bestEq= equipment1;
        int bestPatternNumber=0;
        for (int i = 0; i < populationSize; i++) {
            Equipment tmp = population.get(i);
            int patternsNumber = 0;
            for (int j = 0; j < tmp.getPatternsList().size(); j++) {
                if(!bestEq.getPatternsList().contains(tmp.getPatternsList().get(j))){
                    patternsNumber++;
                }
            }
            if( patternsNumber>bestPatternNumber ){
                bestEq=tmp;
            }
        }
        return bestEq;
    }
    private Equipment findBest(List<Equipment> population){
//        Equipment best = population.get(0);
//        double min = best.getFitness();
//        for (int i = 0; i < population.size(); i++) {
//            if(population.get(i).getFitness() < min){
//                best=population.get(i);
//                min = population.get(i).getNumberOfSteps();
//            }
//        }
        return population.get(0);
    }
}