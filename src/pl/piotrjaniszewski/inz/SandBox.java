package pl.piotrjaniszewski.inz;

import pl.piotrjaniszewski.inz.Head.EquipmentSingleArray;
import pl.piotrjaniszewski.inz.Head.HeadPosition;
import pl.piotrjaniszewski.inz.Head.Pattern;
import pl.piotrjaniszewski.inz.Workpiece.Workpiece;

import java.util.LinkedList;
import java.util.List;

public class SandBox {
    public static void main(String[] args) {
        Workpiece workpiece = new Workpiece("test1.txt");
        workpiece.getAnyHeadPositions(4,5);
        List<HeadPosition> headPositions = workpiece.getHeadPositionsWithMinimal(4,5,2);

//        System.out.println(headPositions);

        EquipmentSingleArray equipmentSingleArray1 = new EquipmentSingleArray(4,5,4);
        EquipmentSingleArray equipmentSingleArray2 = new EquipmentSingleArray(4,5,4);
        equipmentSingleArray1.getNumberOfSteps(workpiece.getHeadPositionsWithMinimal(4,5,2),workpiece.getHolesList());
        equipmentSingleArray2.getNumberOfSteps(workpiece.getHeadPositionsWithMinimal(4,5,2),workpiece.getHolesList());

        EquipmentSingleArray equipmentSingleArray3 = new EquipmentSingleArray(equipmentSingleArray1,equipmentSingleArray2);

        System.out.println(equipmentSingleArray1 );
        System.out.println(equipmentSingleArray2 );
        System.out.println(equipmentSingleArray3 );

//        int [] arrayPattern = new int[] {0,0,0,0,1,1,1,0,0,0};
//        int [] arrayPattern2 = new int[] {0,0,0,0,1,1,1,0,0,0};
//        Pattern pattern = new Pattern(arrayPattern,6);
//        Pattern pattern2 = new Pattern(arrayPattern2,6);
//
//        List<Pattern> list = new LinkedList<>();
//        list.add(pattern2);
//        System.out.println(list.contains(pattern));
//
//        for (int i = 0; i < pattern.getPattern().length; i++) {
//            System.out.print(pattern.getPattern()[i]+" ");
//        }
//        System.out.println("patternType: "+pattern.getPatternType());
    }
}