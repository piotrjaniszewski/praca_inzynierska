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

    public void odwroc(int miasto1,int miasto2){
        int[] genotyp = this.getPath().clone();
        if(miasto1<miasto2){
            genotyp=odwracaj(genotyp,miasto1+1,miasto2,(miasto2-miasto1)/2);
        } else {
            if(genotyp.length-miasto1 -1 >= miasto2+1){
                genotyp=odwracaj(genotyp,0,miasto1+miasto2+1,miasto2);
                genotyp=odwracaj(genotyp, miasto1+miasto2+2,genotyp.length-1,(genotyp.length-miasto1-miasto2-2)/2);
            } else {
                genotyp=odwracaj(genotyp,miasto1+1,miasto2,genotyp.length-miasto1-1);
                genotyp=odwracaj(genotyp,0,miasto2-(genotyp.length-miasto1-1),(miasto2-(genotyp.length-miasto1-1) + 1)/2);
            }
        }
        this.path = genotyp;
    }

    private int[] odwracaj(int[] genotyp, int start, int end, int length){
        int tmp;
        for (int i = 0; i < length; i++) {
            tmp=genotyp[start+i];
            genotyp[start+i]=genotyp[end-i];
            genotyp[end-i]=tmp;
        }
        return genotyp;
    }

    @Override
    public String toString() {
        return "Path{" +
                "path=" + Arrays.toString(path) +
                '}';
    }
}