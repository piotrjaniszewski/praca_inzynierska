package pl.piotrjaniszewski.inz.Algorithm;

import pl.piotrjaniszewski.inz.Head.EquipmentSingleArray;
import pl.piotrjaniszewski.inz.Head.HeadPosition;
import pl.piotrjaniszewski.inz.Workpiece.Hole;
import pl.piotrjaniszewski.inz.Workpiece.Workpiece;
import pl.piotrjaniszewski.inz.inverover.InverOver;

import java.util.*;

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

    private List<EquipmentSingleArray> bestList;
    private int smallestNumberofSteps;
    private EquipmentSingleArray best;
    private List<EquipmentSingleArray> population;
    private Random rnd = new Random();

    private long populationNumber = 0;

    public EquipmentOptimizationAlgorithm(Workpiece workpiece, int populationSize, double mutationProbability, long duration, int headWidth, int headHeight, int numberOfDrills) {
        this.workpiece = workpiece;
        this.populationSize = populationSize;
        this.mutationProbability = mutationProbability;
        this.duration = duration;
        this.headWidth = headWidth;
        this.headHeight = headHeight;
        this.numberOfDrills = numberOfDrills;
        this.bestList = new LinkedList<>();
        this.smallestNumberofSteps = workpiece.getHolesList().size();
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
        bestList.add(best);

        while (System.currentTimeMillis()-startTime< duration){
            populationNumber++;
            List<EquipmentSingleArray> newPopulation = new LinkedList<>();
            for (int i = 0; i < populationSize-1; i++) {
                //krzyzowanie
                for (int j = i+1; j < populationSize-1; j++) {
                    newPopulation.add(new EquipmentSingleArray(population.get(i),population.get(j)));
                }
            }
            
            //eliminacja
            Collections.sort(newPopulation, Comparator.comparingInt(EquipmentSingleArray::getNumberOfSteps));

            List<EquipmentSingleArray> selectedPopulation = new LinkedList<>();

            for (int i = 0; i < populationSize; i++) {
                selectedPopulation.add(newPopulation.get(i));
            }
            
            EquipmentSingleArray populationBest = findBest(newPopulation);
            if(populationBest.getNumberOfSteps() < best.getNumberOfSteps()){
                best=populationBest;
                smallestNumberofSteps=best.getNumberOfSteps();
                bestList=new LinkedList<>();
                bestList.add(best);
                System.out.println("Nowy minimalny: "+best.getNumberOfSteps()+" Czas: "+(System.currentTimeMillis()-startTime));
            }
            else if(populationBest.getNumberOfSteps()==best.getNumberOfSteps()){
                if(!bestList.contains(populationBest)){
                    bestList.add(populationBest);
                }
            }

            this.population=newPopulation;
        }
        System.out.println("ilosc najlepszych: "+bestList.size());
        int shortestPathIndex = -1;
        double shortestPathLength = Double.MAX_VALUE;
        int[] bestPath = null;

        //obliczanie najkrótrzej drogi
        for (int i = 0; i < bestList.size(); i++) {
            bestList.get(i).getHeadPositions().add(0,new HeadPosition(0,0,new LinkedList<>()));
            HeadPosition[] points = new HeadPosition[bestList.get(i).getHeadPositions().size()];
            for (int j = 0; j < points.length; j++) {
                points[j]=bestList.get(i).getHeadPositions().get(j);
            }

            InverOver inverOver = new InverOver(25,60*1,0.02,points);
            inverOver.start();

            double currentFitnessValue = inverOver.calculateFitness(inverOver.getBest().getPath());
            System.out.println(i+". "+currentFitnessValue);

            if(currentFitnessValue<shortestPathLength){
                shortestPathLength=currentFitnessValue;
                shortestPathIndex=i;
                bestPath=inverOver.getBest().getPath();
            }
        }
        best = bestList.get(shortestPathIndex);

        //sortowanie
        List<HeadPosition> bestHeadPositions = best.getHeadPositions();
        HeadPosition[] headPositions = new HeadPosition[bestHeadPositions.size()];
        for (int i = 0; i < headPositions.length; i++) {
            headPositions[i]=bestHeadPositions.get(bestPath[i]);
        }

        //przesunieci aby punkt 0,0 był pierwszy
        int firstIndex = -1;
        for (int i = 0; i < bestPath.length; i++) {
            if(bestPath[i]==0){
                firstIndex=i;
                break;
            }
        }
        List<HeadPosition> headPositionList = new LinkedList();
        for (int i = firstIndex; i < headPositions.length; i++) {
            headPositionList.add(headPositions[i]);
        }
        for (int i = 0; i < firstIndex; i++) {
            headPositionList.add(headPositions[i]);
        }
        best.setHeadPositions(headPositionList);
        System.out.println("populationNumber = " + populationNumber);
    }


    public EquipmentSingleArray getBest() {
        return best;
    }

    private EquipmentSingleArray getEquipment(){
        EquipmentSingleArray equipment1 = population.get(rnd.nextInt(populationSize-1));
        EquipmentSingleArray equipment2 = population.get(rnd.nextInt(populationSize-1));

        if(equipment1.getNumberOfSteps()>equipment2.getNumberOfSteps()){
            return equipment1;
        } else {
            return equipment2;
        }
    }

    private EquipmentSingleArray getEquipment(EquipmentSingleArray equipment1){

        EquipmentSingleArray bestEq= equipment1;
        int bestPatternNumber=0;
        for (int i = 0; i < populationSize; i++) {
            EquipmentSingleArray tmp = population.get(i);
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