package pl.piotrjaniszewski.inz;

import pl.piotrjaniszewski.inz.Head.EquipmentSingleArray;
import pl.piotrjaniszewski.inz.Head.HeadPosition;
import pl.piotrjaniszewski.inz.Workpiece.Workpiece;

import java.util.List;

public class SandBox {
    public static void main(String[] args) {
        Workpiece workpiece = new Workpiece("test1.txt");
        workpiece.getAnyHeadPositions(4,5);
        EquipmentSingleArray equipment   = new EquipmentSingleArray(new int[]{1, 1, 4, 3,4, 3, 2, 4, 2},4,4);
        EquipmentSingleArray equipment2  = new EquipmentSingleArray(new int[]{1 ,4 ,1 ,3, 2,1,2,3, 4},4,4);
        System.out.println(equipment);
        List<HeadPosition> headPositions = workpiece.getHeadPositionsWithMinimal(4,5,2);
        int i = equipment.getNumberOfSteps(headPositions,workpiece.getHolesList());
        int ii = equipment2.getNumberOfSteps(headPositions,workpiece.getHolesList());
        System.out.println(i);
        System.out.println(ii);

    }
}
