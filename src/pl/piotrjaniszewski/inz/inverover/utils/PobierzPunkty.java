package pl.piotrjaniszewski.inz.inverover.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PobierzPunkty {
    public static Punkt[] ZPlikuTsp(String nazwa){
        File file = new File(nazwa);
        Scanner in = null;
        Punkt[] punkty;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line="";
        while (in.hasNext()&&!line.contains("DIMENSION")){
            line=in.nextLine();
        }
        if (line.contains("DIMENSION")) {
            int rozmiar = Integer.parseInt(line.split(" : ")[1]);
            punkty=new Punkt[rozmiar];
        }
        else {
            throw new Error("W pliku brak informacji o liczbie punktów");
        }
        in.nextLine();
        in.nextLine();

        double x;
        double y;
        String[] liczby;
        int index;
        Punkt p;

        while (in.hasNext()){
            line=in.nextLine();
            liczby = line.split("\\s+");
            if(liczby.length==3){
                x = Double.parseDouble(liczby[1]);
                y = Double.parseDouble(liczby[2]);
                index=Integer.parseInt(liczby[0]);
                p = new Punkt(x,y);
                punkty[index-1]=p;
            }
        }

        in.close();
        //System.out.println("pobrano: "+punkty.length+" punktów");
        return punkty;
    }
}
