package pl.piotrjaniszewski.inz;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import pl.piotrjaniszewski.inz.Head.Equipment;
import pl.piotrjaniszewski.inz.Head.EquipmentSingleArray;
import pl.piotrjaniszewski.inz.Head.HeadPosition;
import pl.piotrjaniszewski.inz.Workpiece.Hole;
import pl.piotrjaniszewski.inz.Workpiece.Workpiece;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Workpiece workpiece = new Workpiece("test1.txt");
        System.out.println(workpiece);
        List<HeadPosition> anyHeadPositions = workpiece.getHeadPositionsWithMinimal(7, 4,2);
//        for (int i = 0; i < anyHeadPositions.size(); i++) {
//            System.out.println(anyHeadPositions.get(i));
//        }
//        System.out.println();
//        System.out.println(anyHeadPositions.size());
//        EquipmentSingleArray equipment = new EquipmentSingleArray(7,4,4);
        EquipmentSingleArray equipment = new EquipmentSingleArray(new int[]{4,1,1,1,1,1,1,1,1,1,1},7,4);
        System.out.println("Liczba kroków dla danego ustawienia: "+equipment.getNumberOfSteps(anyHeadPositions));
        System.out.println();
        System.out.println(equipment.toString());
    }

    public static void zbiory(){
        Set<Hole> holesSet = new HashSet<>();
        holesSet.add(new Hole(1,1,1));
        holesSet.add(new Hole(1,1,1));
        holesSet.add(new Hole(1,1,1));
        holesSet.add(new Hole(1,1,2));
        holesSet.add(new Hole(1,1,3));
        holesSet.add(new Hole(1,1,4));

        holesSet.add(new Hole(2,1,1));
        holesSet.add(new Hole(1,2,1));

        for (Iterator<Hole> i = holesSet.iterator();i.hasNext();){
            System.out.println(i.next());
        }

        System.out.println();

        for (Iterator<Hole> i = holesSet.iterator();i.hasNext();){
            if( i.next().equals(new Hole(1,1,1)) ){
                i.remove();
            }
        }

        List<Hole> holes = new LinkedList<>(holesSet);

        for (int i = 0; i < holes.size(); i++) {
            System.out.println(holes.get(i));
        }
    }
}