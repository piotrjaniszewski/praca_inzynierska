package pl.piotrjaniszewski.inz.workpiece;

import pl.piotrjaniszewski.inz.head.HeadPosition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Workpiece {
    private int holes[][];
    private int height;
    private int width;
    private String filePath;
    private double averagePathLength;
    private int numberOfHoles;

    public Workpiece(String filePath) {
        this.filePath = filePath;
        readFromFile();
        this.numberOfHoles=calculateNumberOfHoles();
        this.averagePathLength=calculateAverageDistance();
    }

    public double getAveragePathLength() {
        return averagePathLength;
    }
    public int getNumberOfHoles() {
        return numberOfHoles;
    }
    private int getHeight() {
        return height;
    }
    private int getWidth() {
        return width;
    }
    private int getHole(int x, int y) {
        return holes[x][y];
    }

    //Lista wszystkich otworów
    public List<Hole> getHolesList(){
        List<Hole> holeList = new LinkedList<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(holes[i][j]!=0){
                    holeList.add(new Hole(i,j,holes[i][j]));
                }
            }
        }
        return holeList;
    }

    //Wczytywanie z pliku
    private void readFromFile() {
        File file = new File(this.filePath);
        try {
            Scanner scanner = new Scanner(file);
            this.width = Integer.valueOf(scanner.nextLine());
            this.height = Integer.valueOf(scanner.nextLine());
            holes = new int[width][height];
            while (scanner.hasNext()) {
                String hole = scanner.nextLine();
                int x = Integer.valueOf(hole.split(",")[0]);
                int y = Integer.valueOf(hole.split(",")[1]);
                int drillType = Integer.valueOf(hole.split(",")[2]);
                holes[x][y] = drillType;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Liczba pozycji głowicy dla których mozna wywiercić minimalNumberOfHoles otworów
    public List<HeadPosition> getHeadPositions(int headWidth, int headHeight, int minimalNumberOfHoles){
        List<HeadPosition> headPositions = new LinkedList<>();

        for (int i = -headWidth ; i < width + headWidth + 1; i++) {
            for ( int j = -headHeight; j < height + headHeight + 1; j++) {
                List<Hole> list = new LinkedList<>();

                for (int k = 0; k < headWidth; k++) {
                    if( j>=0 && i+k>=0 && j<height && i+k<width){
                        if(holes[i+k][j]!=0){
                            list.add(new Hole(i+k,j,holes[i+k][j]));
                        }
                    }
                }

                for (int k = 1; k <= headHeight; k++) {
                    if(i>=0 && j+k>=0 && i<width && j+k<height){
                        if(holes[i][j+k]!=0){
                            list.add(new Hole(i,j+k,holes[i][j+k]));
                        }
                    }
                }

                if(list.size()>=minimalNumberOfHoles){
                    headPositions.add( new HeadPosition(i,j,list) );
                }
            }
        }
        return headPositions;
    }

    //Obliczanie średniej odległości między otworami
    public double calculateAverageDistance(){
        List<Hole> holeList = getHolesList();
        holeList.add(0,new Hole(0,0,0));
        double averageDistanceBetweenHoles=0;
        double averageDistanceToStartPoint=0;
        int tmp=0;
        for (int i = 0; i < holeList.size()-1; i++) {
            for (int j = i+1; j < holeList.size(); j++) {
                averageDistanceBetweenHoles += calculateDistance(holeList.get(i),holeList.get(j));
                tmp++;
            }
            averageDistanceToStartPoint+=calculateDistance(holeList.get(0),holeList.get(i+1));
        }
        double averageDistance=(2*averageDistanceBetweenHoles)/(numberOfHoles*(numberOfHoles-1))+(2*(averageDistanceToStartPoint))/numberOfHoles;
        return (averageDistanceBetweenHoles/tmp)*numberOfHoles  ;
    }

    //Obliczanie liczby otworów
    public int calculateNumberOfHoles(){
        int numberOfHoles=0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(holes[i][j]!=0){
                    numberOfHoles++;
                }
            }
        }
        return numberOfHoles;
    }

    //Obliczanie odległości między dwoma punktami
    public static double calculateDistance(Hole h1, Hole h2){
        return Math.sqrt((h2.getX()-h1.getX())*(h2.getX()-h1.getX())+(h2.getY()-h1.getY())*(h2.getY()-h1.getY()));
    }

    @Override
    public String toString() {
        String tmp = " ";
        for (int i = 0; i < this.getWidth(); i++) {
            tmp += "__";
        }

        tmp += "\n|";
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                if (this.getHole(j, i) != 0) {
                    tmp += this.getHole(j, i) + " ";
                } else {
                    tmp += "  ";
                }
            }
            tmp += "|\n|";
        }
        for (int i = 0; i < this.getWidth(); i++) {
            tmp += "--";
        }
        tmp += "|";
        return tmp;
    }

}