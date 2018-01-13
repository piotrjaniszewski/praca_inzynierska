package pl.piotrjaniszewski.inz.Workpiece;

import pl.piotrjaniszewski.inz.Head.HeadPosition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Workpiece {
    private int holes[][];
    private int height;
    private int width;
    private String path;

    public Workpiece(String path) {
        this.path = path;
        readFromFile();
    }

    public Workpiece(int[][] holes) {
        this.holes = holes;
    }

    private void readFromFile() {
        File file = new File(this.path);
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

    public int[][] getHolesTable() {
        return holes;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getHole(int x, int y) {
        return holes[x][y];
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

    public List<HeadPosition> getAnyHeadPositions(int headWidth, int headHeight){
        List<HeadPosition> headPositions = new LinkedList<>();

        for (int i = -headWidth ; i < width + headWidth + 1; i++) {
            for ( int j = -headHeight; j < height + headHeight + 1; j++) {
                List<Hole> list = new LinkedList<>();

                for (int k = 0; k < headWidth; k++) {
                    if( j>=0 && i+k>=0 && j<height&& i+k<width){
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

                if(!list.isEmpty()){
                    headPositions.add( new HeadPosition(i,j,list) );
                }
            }
        }
        return headPositions;
    }

    public boolean isDrilled(List<HeadPosition> headPositions){
        List<Hole> holes = getHolesList();
        for (int i = 0; i < headPositions.size(); i++) {
            for (int j = 0; j < headPositions.get(i).getPossibleHoles().size(); j++) {
                holes.remove(headPositions.get(i).getPossibleHoles().get(j));
            }
        }
        return holes.isEmpty();
    }


    public List<HeadPosition> getHeadPositionsWithMinimal(int headWidth, int headHeight, int minimalNumberOfHoles){
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
}