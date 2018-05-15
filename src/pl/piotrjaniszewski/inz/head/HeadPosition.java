package pl.piotrjaniszewski.inz.head;

import pl.piotrjaniszewski.inz.workpiece.Hole;

import java.util.LinkedList;
import java.util.List;

public class HeadPosition {
    private int x;
    private int y;
    private List<Hole> possibleHoles;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public List<Hole> getPossibleHoles() {
        return possibleHoles;
    }

    public HeadPosition(int x, int y, List<Hole> possibleHoles) {
        this.x = x;
        this.y = y;
        this.possibleHoles = possibleHoles;
    }

    public HeadPosition(HeadPosition headPosition) {
        this.possibleHoles = new LinkedList<>(headPosition.possibleHoles);
        this.x=headPosition.getX();
        this.y=headPosition.getY();
    }

    @Override
    public String toString() {
        return "HeadPosition{" +
                "x=" + x +
                ", y=" + y +
                ", possibleHoles=" + possibleHoles.size() +
                '}';
    }
}