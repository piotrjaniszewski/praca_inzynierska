package pl.piotrjaniszewski.inz;

import pl.piotrjaniszewski.inz.Workpiece.Hole;

import java.util.LinkedList;
import java.util.List;

public class SandBox {
    public static void main(String[] args) {
        List<Hole> holes = new LinkedList<>();
        holes.add(new Hole(1,1,1));
        holes.add(new Hole(2,2,1));

        Hole hole = new Hole(1,1,1);
        holes.remove(hole);
        holes.remove(hole);
        holes.remove(hole);

        for (int i = 0; i < holes.size(); i++) {
            System.out.println(holes.get(i));
        }
    }
}
