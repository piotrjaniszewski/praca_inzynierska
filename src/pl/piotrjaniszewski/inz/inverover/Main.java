package pl.piotrjaniszewski.inz.inverover;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        System.out.println("Poczatek algorytmu");
        for (int i = 0; i < 5; i++) {
            InverOver inverOver = new InverOver(100,5*1000,0.05,"qa194.tsp");
            inverOver.StartClassic();
            inverOver.obliczFenotyp(inverOver.getNajlepszy().getPath());
            System.out.println( inverOver.obliczFenotyp(inverOver.getNajlepszy().getPath()));
            PrintWriter zapis = null;
            try {
                zapis = new PrintWriter("powtorzenie"+(i+1)+".txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            zapis.println(inverOver.getNajlepszy());
            zapis.println(inverOver.obliczFenotyp(inverOver.getNajlepszy().getPath()));
            zapis.close();
            System.out.println("Ilosc pokolen "+ inverOver.ilosc);
            System.out.println();
        }
    }
}