package pl.piotrjaniszewski.inz.Head;

import pl.piotrjaniszewski.inz.Workpiece.Hole;
import java.util.*;

public class EquipmentSingleArray {
    private int width;
    private int height;
    private int numberOfDrills;
    private int[] headEquipment;
    private int minimalNumberOfHoles = 1;

    private boolean stepsCalculated=false;
    private int numberOfSteps;
    private List<HeadPosition> headPositions  = new LinkedList<>();

    private Random rnd = new Random();

    public EquipmentSingleArray(int[] headEquipment, int width, int numberOfDrills) {
        this.headEquipment =headEquipment;
        this.width = width;
        this.height = headEquipment.length-width;
        this.numberOfDrills=numberOfDrills;
    }

    public EquipmentSingleArray(int width, int height, int numberOfDrills) {
        this.width = width;
        this.height = height;
        this.numberOfDrills = numberOfDrills;
        headEquipment = new int[width+height];
        randomGenerate();
    }

    public EquipmentSingleArray(EquipmentSingleArray equipment1, EquipmentSingleArray equipment2){
        this.width = equipment1.width;
        this.height = equipment1.height;
        this.numberOfDrills = equipment1.numberOfDrills;
        headEquipment = newHeadEquipment(equipment1.headEquipment,equipment2.headEquipment);
    }

    private int[] newHeadEquipment(int[] headEquipment1, int[] headEquipment2){
        //bez sensu
        int[] newHeadEquipment = new int[headEquipment1.length];

        return newHeadEquipment;
    }

    private void randomGenerate(){
        headEquipment =new int[width+height];
        for (int i = 0; i < numberOfDrills; i++) {
            headEquipment[i]=i+1;
        }
        for (int i = numberOfDrills; i < headEquipment.length; i++) {
            headEquipment[i]=rnd.nextInt(numberOfDrills)+1;
        }
        int index;
        int temp;
        for (int i = headEquipment.length - 1; i > 0; i--)
        {
            index = rnd.nextInt(i + 1);
            temp = headEquipment[index];
            headEquipment[index] = headEquipment[i];
            headEquipment[i] = temp;
        }
    }

    public int getNumberOfSteps(List<HeadPosition> possibleHeadPositions, List<Hole> holes){
        if (stepsCalculated){
            return numberOfSteps;
        } else {
            calculateSteps(possibleHeadPositions, holes);
            return numberOfSteps;
        }
    }

    public List<HeadPosition> getHeadPositions(List<HeadPosition> possibleHeadPositions, List<Hole> holes) {
        if(stepsCalculated){
            return headPositions;
        } else {
            calculateSteps(possibleHeadPositions, holes);
            return headPositions;
        }
    }

    private void calculateSteps(List<HeadPosition> possibleHeadPositions, List<Hole> holes){
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
                    if(headEquipment[testedX]==testedHole.getType()){
                        possibleHoles.add(new Hole(testedHole.getX(),testedHole.getY(),testedHole.getType()));
                    }
                } else {
                    //pionowo
                    if(headEquipment[width+testedY-1]==testedHole.getType()){
                        possibleHoles.add(new Hole(testedHole.getX(),testedHole.getY(),testedHole.getType()));
                    }
                }
            }

            if(possibleHoles.size()>=minimalNumberOfHoles){
                headPositions.add(new HeadPosition(x,y,possibleHoles));
            }
        }
        minimizeNumberOfSteps();
        List<Hole>remainingHoles = getUndrilledHoles(holes,headPositions);
        for (int i = 0; i < remainingHoles.size(); i++) {
            for (int j = 0; j < headEquipment.length; j++) {
                if(remainingHoles.get(i).getType()== headEquipment[j]){
                    List<Hole> holeList = new LinkedList<>();
                    holeList.add(remainingHoles.get(i));
                    if(j<width){
                        int x = remainingHoles.get(i).getX()-j;
                        int y = remainingHoles.get(i).getY();
                        headPositions.add(new HeadPosition(x,y,holeList));
                    } else {
                        int x = remainingHoles.get(i).getX();
                        int y = remainingHoles.get(i).getY()-(j+width+1);
                        headPositions.add(new HeadPosition(x,y,holeList));
                    }
                    break;
                }
            }
        }
        numberOfSteps=headPositions.size();
        stepsCalculated=true;
    }

    private void minimizeNumberOfSteps() {
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
        this.headPositions=minimalHeadPositions;
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

    private List<Hole> getUndrilledHoles(List<Hole> allHoles, List<HeadPosition>headPositions){
        List<Hole> holes = new LinkedList<>(allHoles);
        for (int i = 0; i < headPositions.size(); i++) {
            for (int j = 0; j < headPositions.get(i).getPossibleHoles().size(); j++) {
                holes.remove(headPositions.get(i).getPossibleHoles().get(j));
            }
        }
        return holes;
    }

    @Override
    public String toString() {
        String tmp ="";
        for (int i = 0; i < width; i++) {
            tmp+= headEquipment[i]+" ";
        }
        tmp+="\n";
        for (int i = width; i < headEquipment.length; i++) {
            tmp+= headEquipment[i]+"\n";
        }

        return tmp;
    }
}