package pl.piotrjaniszewski.inz.workpiece;

public class Hole {
    private int x;
    private int y;
    private int type;

    public Hole(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Hole)obj).x == this.x && ((Hole)obj).y==this.y;
    }

    @Override
    public String toString() {
        return "Hole{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + type +
                '}';
    }

    @Override
    public int hashCode() {
        return x*1000000+y;
    }
}