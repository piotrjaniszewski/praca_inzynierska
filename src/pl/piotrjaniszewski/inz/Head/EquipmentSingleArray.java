package pl.piotrjaniszewski.inz.Head;

import pl.piotrjaniszewski.inz.Workpiece.Hole;
import java.util.*;

public class EquipmentSingleArray {
    private int width;
    private int height;
    private int numberOfDrills;
    private int[] headEquipmnet;
    private int minimalNumberOfHoles = 1;

    private boolean stepsCalculated=false;
    private int numberOfSteps;
    private List<HeadPosition> headPositions  = new LinkedList<>();

    private Random rnd = new Random();

    public EquipmentSingleArray(int[] headEquipmnet, int width, int numberOfDrills) {
        this.headEquipmnet=headEquipmnet;
        this.width = width;
        this.height = headEquipmnet.length-width;
    }

    public EquipmentSingleArray(int width, int height, int numberOfDrills) {
        this.width = width;
        this.height = height;
        this.numberOfDrills = numberOfDrills;
        headEquipmnet = new int[width+height];
        randomGenerate();
    }

    public EquipmentSingleArray(Equipment equipment1, Equipment equipment2){
        this.width = equipment1.width;
        this.height = equipment1.height;
        this.numberOfDrills = equipment1.numberOfDrills;
    }

    private void randomGenerate(){
        headEquipmnet=new int[width+height];
        for (int i = 0; i < numberOfDrills; i++) {
            headEquipmnet[i]=i+1;
        }
        for (int i = numberOfDrills; i < headEquipmnet.length; i++) {
            headEquipmnet[i]=rnd.nextInt(numberOfDrills)+1;
        }
        int index;
        int temp;
        for (int i = headEquipmnet.length - 1; i > 0; i--)
        {
            index = rnd.nextInt(i + 1);
            temp = headEquipmnet[index];
            headEquipmnet[index] = headEquipmnet[i];
            headEquipmnet[i] = temp;
        }
    }

    public int getNumberOfSteps(List<HeadPosition> possibleHeadPositions){
        if (stepsCalculated){
            return numberOfSteps;
        } else {
            calculateSteps(possibleHeadPositions);
            return numberOfSteps;
        }
    }

    public List<HeadPosition> getHeadPositions(List<HeadPosition> possibleHeadPositions) {
        if(stepsCalculated){
            return headPositions;
        } else {
            calculateSteps(possibleHeadPositions);
            return headPositions;
        }
    }

    private void calculateSteps(List<HeadPosition> possibleHeadPositions){
        //dla listy possibleHeadPositions tworze nową listę i bangladesz;
        for (int i = 0; i < possibleHeadPositions.size(); i++) {
            int x = possibleHeadPositions.get(i).getX();//pozycja głowicy
            int y = possibleHeadPositions.get(i).getY();//pozycja głowicy
            List<Hole> possibleHoles = new LinkedList<>();
            for (int j = 0; j < possibleHeadPositions.get(i).getPossibleHoles().size(); j++) {
                Hole testedHole = possibleHeadPositions.get(i).getPossibleHoles().get(j);
                int testedX = testedHole.getX()-x;
                int testedY = testedHole.getY()-y;
                if(testedY==0){
                    //poziomo
                    if(headEquipmnet[testedX]==testedHole.getType()){
                        possibleHoles.add(new Hole(testedHole.getX(),testedHole.getY(),testedHole.getType()));
                    }
                } else {
                    //pionowo
                    if(headEquipmnet[width+testedY-1]==testedHole.getType()){
                        possibleHoles.add(new Hole(testedHole.getX(),testedHole.getY(),testedHole.getType()));
                    }
                }
            }

            if(possibleHoles.size()>=minimalNumberOfHoles){
                headPositions.add(new HeadPosition(x,y,possibleHoles));
            }
        }

//        headPositions.sort(Comparator.comparingInt(new ToIntFunction<HeadPosition>() {
//            @Override
//            public int applyAsInt(HeadPosition value) {
//                return -value.getPossibleHoles().size();
//            }
//        }));
        minimalizeNumberOfSteps();
        //znalezc niepokryte otwory

        System.out.println();
        System.out.println();

        for (int i = 0; i < headPositions.size(); i++) {
            System.out.println(i+": "+headPositions.get(i).getPossibleHoles().size());
        }
        System.out.println();
        System.out.println();

        numberOfSteps=headPositions.size();
    }

    private void minimalizeNumberOfSteps() {
        mnosV1();
        mnosV2();
    }
    private HeadPosition max(){
        HeadPosition max = headPositions.get(0);
        for (int i = 1; i < headPositions.size(); i++) {
            if(headPositions.get(i).getPossibleHoles().size()>max.getPossibleHoles().size()){
                max = headPositions.get(i);
            }
        }
        return max;
    }

    private void mnosV1() {
        List<HeadPosition> minimalHeadPositions = new LinkedList<>();
        while(headPositions.size()!=0){
            HeadPosition headPosition = max();
            if(headPosition.getPossibleHoles().size()!=0){
                minimalHeadPositions.add(new HeadPosition(headPosition));
            }
            headPositions.remove(headPosition);
            for (int i = 0; i < headPosition.getPossibleHoles().size(); i++) {
                Hole hole = headPosition.getPossibleHoles().get(i);
                for (int j = 0; j < headPositions.size(); j++) {
                    headPositions.get(j).getPossibleHoles().remove(hole);
                }
            } //usunięto wszytkie otwory aktualnego ustawienia głowicy
            for (int i = 0; i < headPositions.size(); i++) {
                if(headPositions.get(i).getPossibleHoles().size()<2){
                    headPositions.remove(headPositions.get(i));
                }
            } //usuwam puste ustawienia
        }

        headPositions=minimalHeadPositions; //wszystkie ustawienia głowicy wiercące więcej niż jeden
        //dodać do listy pozycji pozycje które mają jeden otwór
        System.out.println("Po minimalizacj: "+ headPositions.size());
    }

    // porównywanie - kto wywiercił więcej otworów bez sensu
    private void mnosV2() {

    }

    @Override
    public String toString() {
        String tmp ="";
        for (int i = 0; i < width; i++) {
            tmp+= headEquipmnet[i]+" ";
        }
        tmp+="\n";
        for (int i = width; i < headEquipmnet.length; i++) {
            tmp+= headEquipmnet[i]+"\n";
        }

        return tmp;
    }
}