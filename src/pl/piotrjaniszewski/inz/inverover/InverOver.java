package pl.piotrjaniszewski.inz.inverover;

import pl.piotrjaniszewski.inz.Head.HeadPosition;

import java.util.Random;

public class InverOver {

    private HeadPosition[] points;
    private Random random;
    private int populationSize;
    private long time;
    private double mutationProbability;
    private Path[] population;
    private Path best;

    public InverOver(int populationSize, long time, double mutationProbability, HeadPosition[] points) {
        this.populationSize = populationSize;
        this.time = time;
        this.mutationProbability = mutationProbability;
        this.points = points;
        random = new Random();
    }

    public Path getBest() {
        return best;
    }

    public void start(){
        population = greedyPopulation(this.populationSize);

        best = population[0];

        long start = System.currentTimeMillis();
        while(System.currentTimeMillis()-start< time){
            for (int i = 0; i < populationSize; i++) {
                Path actualIndividual = new Path(population[i].getPath());
                int randomPoint = random.nextInt(points.length-1);
                while(true){
                    int pointPrim;
                    if(random.nextDouble()<= mutationProbability){
                        do{
                            pointPrim = random.nextInt(points.length-1);
                        }while (pointPrim==randomPoint);
                    }
                    else{
                        Path randomIndividual;
                        int index;
                        do{
                            index=random.nextInt(populationSize -1);
                        } while (index==i);
                        randomIndividual= population[index];
                        pointPrim = findIndex(actualIndividual.getPath()[randomPoint],randomIndividual.getPath())+1;
                        if(pointPrim== points.length){
                            pointPrim=0;
                        }
                    }
                    if (randomPoint == pointPrim + 1 || randomPoint == pointPrim - 1 || (pointPrim == 0 && randomPoint == points.length - 1 || (randomPoint == 0 && pointPrim == points.length - 1))) {
                        break;
                    }
                    actualIndividual.reverse(randomPoint,pointPrim);
                    randomPoint=pointPrim;
                    if (calculateFitness(actualIndividual.getPath()) <= calculateFitness(best.getPath())) {
                        best = actualIndividual;
                        break;
                    }
                }
                if(calculateFitness(actualIndividual.getPath())<= calculateFitness(population[i].getPath())){
                    population[i]=actualIndividual;
                }
            }
        }

        for (int i = 0; i < population.length; i++) {
            if(calculateFitness(population[i].getPath()) < calculateFitness(best.getPath())){
                best = population[i];
            }
        }
    }

    private static int findIndex(int value, int[] genotype){
        for (int i = 0; i < genotype.length; i++) {
            if (genotype[i]==value){
                return i;
            }
        }
        return -1;
    }

    public double calculateFitness(int[] genotype) {
        double Phenotype = 0;
        for (int i = 0; i < genotype.length-1; i++) {
            Phenotype += calculateDistance(points[genotype[i]], points[genotype[i+1]]);
        }
        Phenotype += calculateDistance(points[genotype[points.length-1]], points[genotype[0]]);
        return Phenotype;
    }
    public Path[] greedyPopulation(int size) {
        Path[] initialPopulation = new Path[size];

        for (int i = 0; i < initialPopulation.length; i++) {
            initialPopulation[i]= greedyIndividual();
        }
        return initialPopulation;
    }
    public Path greedyIndividual() {
            double minValue = Double.MAX_VALUE;
            int minIndex = -1;

            int[] genotype = new int[points.length];
            for (int i = 0; i < genotype.length; i++) {
                genotype[i] = -1;
            }

            genotype[0] = random.nextInt(points.length);
            for (int i = 0; i < genotype.length - 1; i++) {
                for (int j = 0; j < genotype.length; j++) {
                    if (calculateDistance(points[genotype[i]], points[j]) < minValue) {
                        if (!contains(genotype, j, i + 1)) {
                            minValue = calculateDistance(points[genotype[i]], points[j]);
                            minIndex = j;
                        }
                    }
                }
                genotype[i + 1] = minIndex;
                minIndex = -1;
                minValue = Double.MAX_VALUE;
            }

        return new Path(genotype);
    }
    private boolean contains(int[] genotype, int value, int length){
        for (int i = 0; i < length; i++) {
            if(genotype[i] == value) {
                return true;
            }
        }
        return false;
    }

    public static double calculateDistance(HeadPosition h1, HeadPosition h2){
        return Math.sqrt((h2.getX()-h1.getX())*(h2.getX()-h1.getX())+(h2.getY()-h1.getY())*(h2.getY()-h1.getY()));
    }
}