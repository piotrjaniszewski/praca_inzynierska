package pl.piotrjaniszewski.inz.inverover;

import java.util.Arrays;

public class Path {
    private int[] path;

    public Path(int[] path) {
        this.path = path;
    }

    public int[] getPath() {
        return path;
    }

    public void reverse(int point1, int point2){
        int[] genotype = this.getPath().clone();
        if(point1<point2){
            genotype= reverse(genotype,point1+1,point2,(point2-point1)/2);
        } else {
            if(genotype.length-point1 -1 >= point2+1){
                genotype= reverse(genotype,0,point1+point2+1,point2);
                genotype= reverse(genotype, point1+point2+2,genotype.length-1,(genotype.length-point1-point2-2)/2);
            } else {
                genotype= reverse(genotype,point1+1,point2,genotype.length-point1-1);
                genotype= reverse(genotype,0,point2-(genotype.length-point1-1),(point2-(genotype.length-point1-1) + 1)/2);
            }
        }
        this.path = genotype;
    }

    private int[] reverse(int[] genotype, int start, int end, int length){
        int tmp;
        for (int i = 0; i < length; i++) {
            tmp=genotype[start+i];
            genotype[start+i]=genotype[end-i];
            genotype[end-i]=tmp;
        }
        return genotype;
    }

    @Override
    public String toString() {
        return "Path{" +
                "path=" + Arrays.toString(path) +
                '}';
    }
}