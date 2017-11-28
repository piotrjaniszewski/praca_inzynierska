package pl.piotrjaniszewski.inz.Head;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import pl.piotrjaniszewski.inz.Workpiece.Hole;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.ToIntFunction;

public class Equipment {

    int[] horizontalHead;
    int[] verticalHead;
    int width;
    int height;
    int numberOfDrills;

    boolean stepsCalculated=false;
    int numberOfSteps;
    List<HeadPosition> headPositions  = new LinkedList<>();

    Random rnd = new Random();

    public Equipment(int[] horizontalHead, int[] verticalHead,int numberOfDrills) {
        this.width = horizontalHead.length;
        this.height = verticalHead.length;
        this.numberOfDrills=numberOfDrills;

        this.horizontalHead=horizontalHead;
        this.verticalHead=verticalHead;
    }

    public Equipment(int width, int height,int numberOfDrills) {
        this.width = width;
        this.height = height;
        this.numberOfDrills = numberOfDrills;
        randomGenerate();
    }

    public Equipment(Equipment equipment1, Equipment equipment2){
        this.width = equipment1.width;
        this.height = equipment1.height;
        this.numberOfDrills = equipment1.numberOfDrills;

    }

    private void randomGenerate(){

        verticalHead = new int[height];
        horizontalHead = new int[width];

        int[] tmp = new int[width+height];
        for (int i = 0; i < numberOfDrills; i++) {
            tmp[i]=i+1;
        }
        for (int i = numberOfDrills; i < tmp.length; i++) {
            tmp[i]=rnd.nextInt(numberOfDrills)+1;
        }

        int index;
        int temp;
        for (int i = tmp.length - 1; i > 0; i--)
        {
            index = rnd.nextInt(i + 1);
            temp = tmp[index];
            tmp[index] = tmp[i];
            tmp[i] = temp;
        }

        for (int i = 0; i < horizontalHead.length; i++) {
            horizontalHead[i]=tmp[i];
        }

        for (int i = 0; i < verticalHead.length; i++) {
            verticalHead[i]=tmp[horizontalHead.length + i];
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
            int x = possibleHeadPositions.get(i).getX();
            int y = possibleHeadPositions.get(i).getY();
            List<Hole> possibleHoles = new LinkedList<>();

            for (int j = 0; j < possibleHeadPositions.get(i).getPossibleHoles().size(); j++) {
                Hole testedHole = possibleHeadPositions.get(i).getPossibleHoles().get(j);
                int testedX = testedHole.getX()-x;
                int testedY = testedHole.getY()-y;

                if(testedY==0){
                    //poziomo
                    if(verticalHead[testedX]==testedHole.getType()){
                        possibleHoles.add(new Hole(testedHole.getX(),testedHole.getY(),testedHole.getType()));
                    }

                } else {
                    //pionowo
                    if(horizontalHead[testedY-1]==testedHole.getType()){
                        possibleHoles.add(new Hole(testedHole.getX(),testedHole.getY(),testedHole.getType()));
                    }
                }
            }

            if(possibleHoles.size()!=0){
                headPositions.add(new HeadPosition(x,y,possibleHoles));
            }
        }

        headPositions.sort(Comparator.comparingInt(new ToIntFunction<HeadPosition>() {
            @Override
            public int applyAsInt(HeadPosition value) {
                return value.getPossibleHoles().size();
            }
        }));
        System.out.println();
        System.out.println();

        for (int i = 0; i < headPositions.size(); i++) {
            System.out.println(i+": "+headPositions.get(i).getPossibleHoles().size());
        }
        System.out.println();
        System.out.println();

        numberOfSteps=headPositions.size();
    }
}