package pl.piotrjaniszewski.inz;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SandBox {
    public static void main(String[] args) {
        Random rnd  = new Random();

        List<Double> list = new LinkedList<>();
        list.add(1.);
        list.add(2.);
        list.add(3.);
        list.add(4.);
        list.add(1,0.);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}