package pl.piotrjaniszewski.inz.head;

import java.util.Arrays;

public class Pattern {

    private int usages;
    private int[] pattern;
    private int patternType;
    private int numberOfDrills=0;
    private boolean minimal=false;
    private int improvement;

    public Pattern(int[] pattern, int width) {
        this.pattern = pattern;
        this.usages=1;
        for (int j = 0; j < width; j++) {
            if(this.pattern[j]!=0){
                this.patternType+=1;
                break;
            }
        }

        for (int j = width; j < this.pattern.length; j++) {
            if(this.pattern[j]!=0){
                this.patternType+=2;
                break;
            }
        }

        if(patternType==3){
            minimal=true;
        } else {
            minimisePattern();
        }

        for (int aPattern : pattern) {
            if (aPattern != 0) {
                numberOfDrills++;
            }
        }
        improvement = (numberOfDrills * usages) - usages;
    }

    public int[] getPattern() {
        return pattern;
    }
    public int getLength(){
        return pattern.length;
    }
    public int getPatternType() {
        return patternType;
    }
    public int getImprovement() {
        return improvement;
    }

    public void use(){
        usages++;
        improvement = ((usages * numberOfDrills) - usages);
    }

    //Minimalizowanie schematu
    public void minimisePattern() {
        if(!minimal) {
            int patternStart = 0;
            int patternEnd = 0;
            for (int i = 0; i < pattern.length; i++) {
                if (pattern[i] != 0) {
                    patternStart = i;
                    break;
                }
            }

            for (int i = pattern.length - 1; i > patternStart; i--) {
                if (pattern[i] != 0) {
                    patternEnd = i;
                    break;
                }
            }
            int[] newPattern = new int[patternEnd - patternStart + 1];
            for (int i = 0; i < newPattern.length; i++) {
                newPattern[i] = pattern[patternStart + i];
            }
            this.pattern = newPattern;
            this.usages = 1;
            minimal = true;
        }
    }

    @Override
    public boolean equals(Object obj) {
        int[] testedPattern = ((Pattern)obj).pattern;
        if(testedPattern.length!=this.pattern.length){
            return false;
        }
        for (int i = 0; i < this.pattern.length; i++) {
            if(this.pattern[i]!=testedPattern[i]){
              return false;
            }
        }
        return  true;
    }
    @Override
    public String toString() {
        return "Pattern{" +
                "usages=" + usages +
                ", pattern=" + Arrays.toString(pattern) +
                ", patternType=" + patternType +
                " improvement="+improvement+
                '}';
    }
}