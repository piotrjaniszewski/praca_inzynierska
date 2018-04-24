package pl.piotrjaniszewski.inz;

import pl.piotrjaniszewski.inz.Head.Equipment;
import pl.piotrjaniszewski.inz.Workpiece.Workpiece;

public class SandBox {
    public static void main(String[] args) {
//        Workpiece workpiece = new Workpiece("test1.txt");
//        Equipment eq1 = new Equipment(8,7,4,workpiece.getHeadPositions(8,7,2),workpiece);
//        Equipment eq2 = new Equipment(8,7,4,workpiece.getHeadPositions(8,7,2),workpiece);
//        Equipment eq3 = new Equipment(8,7,4,workpiece.getHeadPositions(8,7,2),workpiece);
//
//        System.out.println("pathbest = " + eq3.getPathLength());
//        System.out.println("stepsbest = " + eq3.getNumberOfSteps());
//        System.out.println();
//        System.out.println("path1 = " + eq1.getPathLength());
//        System.out.println("steps1 = " + eq1.getNumberOfSteps());
//        System.out.println("path2 = " + eq2.getPathLength());
//        System.out.println("steps2 = " + eq2.getNumberOfSteps());
//        System.out.println("improvement1 = " + eq1.improvement(eq3));
//        System.out.println("improvement2 = " + eq2.improvement(eq3));
        for (int i = 1; i <= 12; i++) {
            Workpiece workpiece = new Workpiece("p" + i + ".txt");
            System.out.print(workpiece.calculateAverageDistance()+"\t");
        }
    }
}