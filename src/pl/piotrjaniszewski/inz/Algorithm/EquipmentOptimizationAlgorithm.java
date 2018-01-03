package pl.piotrjaniszewski.inz.Algorithm;

import pl.piotrjaniszewski.inz.Head.EquipmentSingleArray;
import pl.piotrjaniszewski.inz.Head.HeadPosition;
import pl.piotrjaniszewski.inz.Workpiece.Hole;
import pl.piotrjaniszewski.inz.Workpiece.Workpiece;
import pl.piotrjaniszewski.inz.inverover.InverOver;

import java.util.Arrays;
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

    private List<EquipmentSingleArray> bestList;
    private int smallestNumberofSteps;
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
            List<EquipmentSingleArray> newPopulation = new LinkedList<>();
            for (int i = 0; i < populationSize; i++) {
                EquipmentSingleArray equipmentSingleArray1= getEquipment();
                EquipmentSingleArray equipmentSingleArray2 = getEquipment(equipmentSingleArray1);
                newPopulation.add(new EquipmentSingleArray( equipmentSingleArray1,equipmentSingleArray2));
//                newPopulation.add(new EquipmentSingleArray(headWidth,headHeight,numberOfDrills,anyHeadPositions,holes));
            }

            EquipmentSingleArray populationBest = findBest(newPopulation);
            if(populationBest.getNumberOfSteps() < best.getNumberOfSteps()){
                best=populationBest;
                smallestNumberofSteps=best.getNumberOfSteps();
                bestList=new LinkedList<>();
                bestList.add(best);
                System.out.println("Nowy minimalny: "+best.getNumberOfSteps());
            } else if(populationBest.getNumberOfSteps()==best.getNumberOfSteps()){
                if(!bestList.contains(populationBest)){
                    bestList.add(populationBest);
                }
            }

            this.population=newPopulation;
        }
        System.out.println("ilosc najlepszych: "+bestList.size());
        int shortestPathIndex=-1;
        double shortestPathLength =Double.MAX_VALUE;
        int[] bestPath = null;
        for (int i = 0; i < bestList.size(); i++) {
            HeadPosition[] points = new HeadPosition[bestList.get(i).getHeadPositions().size()];
            for (int j = 0; j < points.length; j++) {
                points[j]=bestList.get(i).getHeadPositions().get(j);
            }

            InverOver inverOver = new InverOver(25,120*1000,0.02,points);
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

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();




        InverOver inverOver = new InverOver(10,1000,0.03, best.getHeadPositions().toArray(new HeadPosition[best.getHeadPositions().size()]));

        int[] tmpp = new int[best.getHeadPositions().size()];
        for (int i = 0; i < best.getHeadPositions().size(); i++) {
            tmpp[i]=i;
        }

        inverOver.start();
        System.out.println(inverOver.calculateFitness(inverOver.getBest().getPath()));
        System.out.print("Path{path=[");
        for (int i = 0; i < bestPath.length; i++) {
            System.out.print(bestPath[i]+", ");
        }
        System.out.println();
        System.out.println(inverOver.getBest().toString());








        //sortowanie
        List<HeadPosition> bestHeadPositions = best.getHeadPositions();
        HeadPosition[] headPositions = new HeadPosition[bestHeadPositions.size()];
        for (int i = 0; i < bestPath.length; i++) {
            headPositions[i]=bestHeadPositions.get(bestPath[i]);
        }
        List<HeadPosition> headPositionList = new LinkedList(Arrays.asList(headPositions));
        best.setHeadPositions(headPositionList);















        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        inverOver = new InverOver(10,1000,0.03, best.getHeadPositions().toArray(new HeadPosition[best.getHeadPositions().size()]));

        tmpp = new int[best.getHeadPositions().size()];
        for (int i = 0; i < best.getHeadPositions().size(); i++) {
            tmpp[i]=i;
        }

        inverOver.start();
        System.out.println(inverOver.calculateFitness(inverOver.getBest().getPath()));
        System.out.print("Path{path=[");
        for (int i = 0; i < bestPath.length; i++) {
            System.out.print(bestPath[i]+", ");
        }
        System.out.println();
        System.out.println(inverOver.getBest().toString());




        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();







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