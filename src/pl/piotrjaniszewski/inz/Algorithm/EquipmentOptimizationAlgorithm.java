package pl.piotrjaniszewski.inz.Algorithm;

import pl.piotrjaniszewski.inz.Head.EquipmentSingleArray;
import pl.piotrjaniszewski.inz.Head.HeadPosition;
import pl.piotrjaniszewski.inz.Workpiece.Hole;
import pl.piotrjaniszewski.inz.Workpiece.Workpiece;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EquipmentOptimizationAlgorithm {
    private Workpiece workpiece;
    private int populationSize;
    private double mutationProbability;
    private long duration;
    private int headWidth;
    private int headHeight;
    private int numberOfDrills;

    private List<HeadPosition> anyHeadPositions;
    private List<Hole> holes;

    private EquipmentSingleArray best;
    private List<EquipmentSingleArray> population;
    private Random rnd = new Random();

    public EquipmentOptimizationAlgorithm(Workpiece workpiece, int populationSize, double mutationProbability, long duration, int headWidth, int headHeight, int numberOfDrills) {
        this.workpiece = workpiece;
        this.populationSize = populationSize;
        this.mutationProbability = mutationProbability;
        this.duration = duration;
        this.headWidth = headWidth;
        this.headHeight = headHeight;
        this.numberOfDrills = numberOfDrills;

        this.anyHeadPositions = this.workpiece.getHeadPositionsWithMinimal(this.headWidth, this.headHeight, 2);
        this.holes = this.workpiece.getHolesList();
    }

    public void start(){
        System.out.println("Rozpoczęcie działania algorytmu.");
        long startTime = System.currentTimeMillis();
        //populacja startowa
        population = new LinkedList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(new EquipmentSingleArray(headWidth,headHeight,numberOfDrills,anyHeadPositions,holes));
        }
        best = findBest(population);

        while (System.currentTimeMillis()-startTime< duration){
            List<EquipmentSingleArray> newPopulation = new LinkedList<>();
            for (int i = 0; i < populationSize; i++) {
                newPopulation.add(new EquipmentSingleArray( getEquipment(),getEquipment()) );
//                newPopulation.add(new EquipmentSingleArray(headWidth,headHeight,numberOfDrills));
            }

            EquipmentSingleArray populationBest = findBest(newPopulation);
            if(populationBest.getNumberOfSteps() < best.getNumberOfSteps()){
                best=populationBest;
                System.out.println("Nowy minimalny: "+best.getNumberOfSteps());
            }

            this.population=newPopulation;
        }
    }

    public EquipmentSingleArray getBest() {
        return best;
    }

    private EquipmentSingleArray getEquipment(){
        // TODO najbardziej różniące się osobniki
        EquipmentSingleArray equipment1 = population.get(rnd.nextInt(populationSize-1));
        EquipmentSingleArray equipment2 = population.get(rnd.nextInt(populationSize-1));

        if(equipment1.getNumberOfSteps()>equipment2.getNumberOfSteps()){
            return equipment1;
        } else {
            return equipment2;
        }
    }

    private EquipmentSingleArray findBest(List<EquipmentSingleArray> population){
        EquipmentSingleArray best = population.get(0);
        int min = best.getNumberOfSteps();
        for (int i = 0; i < population.size(); i++) {
            if(population.get(i).getNumberOfSteps() < min){
                best=population.get(i);
                min = population.get(i).getNumberOfSteps();
            }
        }
        return best;
    }
}