package pl.piotrjaniszewski.inz.Head;

import java.util.Arrays;

public class Pattern {

    private int usages;
    private int[] pattern;
    private int patternType;
    private int width;
    private int numberOfDrills=0;
    private boolean minimal=false;
    private int improvement=0;

    public Pattern(int[] pattern, int width) {
        this.pattern = pattern;
        this.width = width;
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

        for (int i = 0; i < pattern.length; i++) {
            if(pattern[i]!=0){
                numberOfDrills++;
            }
        }
        improvement = (numberOfDrills * usages) - usages;
    }

    public void use(){
        usages++;
        improvement = ((usages * numberOfDrills) - usages);
    }

    public int[] getPattern() {
        return pattern;
    }

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

    public int getLength(){
        return pattern.length;
    }
    public int getUsages() {
        return usages;
    }
    public int getPatternType() {
        return patternType;
    }
    public int getNumberOfDrills() {
        return numberOfDrills;
    }
    public int getImprovement() {
        return improvement;
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