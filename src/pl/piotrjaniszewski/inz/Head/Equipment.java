package pl.piotrjaniszewski.inz.Head;

import pl.piotrjaniszewski.inz.Workpiece.Hole;
import pl.piotrjaniszewski.inz.Workpiece.Workpiece;

import java.util.*;

public class Equipment {
    private int width;
    private int height;
    private int numberOfDrills;
    private int[] headEquipment;
    private int minimalNumberOfHoles = 2;

    private int numberOfSteps;
    private List<HeadPosition> headPositions  = new LinkedList<>();
    private double pathLength;

    private Random rnd = new Random();
    private List<Pattern> patternsList = new LinkedList<>();

    private List<HeadPosition> possibleHeadPositions;
    private List<Hole> holes;
    private Workpiece workpiece;

    //random
    public Equipment(int width, int height, int numberOfDrills, List<HeadPosition> possibleHeadPositions, Workpiece workpiece) {
        this.width = width;
        this.height = height;
        this.numberOfDrills = numberOfDrills;
        this.possibleHeadPositions=possibleHeadPositions;
        this.workpiece =workpiece;
        this.holes=workpiece.getHolesList();
        headEquipment = new int[width+height];
        randomGenerate();
        calculateSteps();
        calculatePath();
    }

    //krzyzowanie
    public Equipment(Equipment equipment1, Equipment equipment2){
        this.width = equipment1.width;
        this.height = equipment1.height;
        this.numberOfDrills = equipment1.numberOfDrills;
        this.possibleHeadPositions=equipment1.possibleHeadPositions;
        this.workpiece = equipment1.workpiece;
        this.holes=equipment1.holes;
        headEquipment = newHeadEquipment(equipment1.getPatternsList(),equipment2.getPatternsList());
        calculateSteps();
        calculatePath();
    }

    //getters setters
    public int getNumberOfSteps() {
        return numberOfSteps;
    }
    public List<HeadPosition> getHeadPositions() {
        return headPositions;
    }
    public void setHeadPositions(List<HeadPosition> headPositions) {
        this.headPositions = headPositions;
    }
    public List<Pattern> getPatternsList() {
        return patternsList;
    }
    public double getPathLength() {
        return pathLength;
    }
    public double improvement(Equipment eq1) {
        double improvement = 0.75 * (1-( ((double)this.getNumberOfSteps() / (double)eq1.getNumberOfSteps())))+0.25*(1-( this.getPathLength() / eq1.getPathLength()));
        return improvement;
    }

    //losowy osobnik
    private void randomGenerate(){
        headEquipment =new int[width+height];
        for (int i = 0; i < numberOfDrills; i++) {
            headEquipment[i]=i+1;
        }
        for (int i = numberOfDrills; i < headEquipment.length; i++) {
            headEquipment[i]=rnd.nextInt(numberOfDrills)+1;
        }
        int index;
        int temp;
        for (int i = headEquipment.length - 1; i > 0; i--)
        {
            index = rnd.nextInt(i + 1);
            temp = headEquipment[index];
            headEquipment[index] = headEquipment[i];
            headEquipment[i] = temp;
        }
    }

    //krzyzowanie
    private int[] newHeadEquipment(List<Pattern> patterns1, List<Pattern> patterns2){
        for (int i = 0; i < patterns2.size(); i++) {
            if(!patterns1.contains(patterns2.get(i))){
                patterns1.add(patterns2.get(i));
            }
        }

        Collections.sort(patterns1, Comparator.comparingInt(Pattern::getImprovement));

        int[] newHeadEquipment = new int[width+height];
        while(!patterns1.isEmpty()){
            //sprawdzam nastepny patern
            Pattern pattern = patterns1.get(rnd.nextInt(patterns1.size()));
            List<Integer> positionList= new LinkedList<>();
            if(pattern.getPatternType()==1){
                //sprawdzam pierwsze umiejscowienie
                for (int i = 0; i <= width-pattern.getLength(); i++) {
                    positionList = new LinkedList<>();

                    boolean flag=true;
                    for (int j = 0; j < pattern.getLength(); j++) {
                        if( !(newHeadEquipment[i+j]==0 || newHeadEquipment[i+j]==pattern.getPattern()[j]) ){
                            flag=false;
                            break;
                        }
                    }
                    if(flag){
                        positionList.add(i);
                    }
                }
                //wylosowac punkt
                if(!positionList.isEmpty()){
                    int index = positionList.get(rnd.nextInt(positionList.size()));
                    for (int i = 0; i < pattern.getLength(); i++) {
                        newHeadEquipment[index+i]=pattern.getPattern()[i];
                    }
                }

            } else if(pattern.getPatternType()==2) {
                //sprawdzam pierwsze umiejscowienie
                for (int i = width; i <= newHeadEquipment.length-pattern.getLength(); i++) {
                    positionList = new LinkedList<>();

                    boolean flag=true;
                    for (int j = width; j < pattern.getLength(); j++) {
                        if( !(newHeadEquipment[i+j]==0 || newHeadEquipment[i+j]==pattern.getPattern()[j]) ){
                            flag=false;
                            break;
                        }
                    }
                    if(flag){
                        positionList.add(i);
                    }
                }
                //wylosowac punkt
                if(!positionList.isEmpty()){
                    int index = positionList.get(rnd.nextInt(positionList.size()));
                    for (int i = 0; i < pattern.getLength(); i++) {
                        newHeadEquipment[index+i]=pattern.getPattern()[i];
                    }
                }

            } else {
                boolean flag=true;
                for (int i = 0; i < newHeadEquipment.length; i++) {
                    if(!(newHeadEquipment[i]==0||newHeadEquipment[i]==pattern.getPattern()[i])){
                        flag=false;
                        break;
                    }
                }
                if(flag){
                    for (int i = 0; i < newHeadEquipment.length; i++) {
                        newHeadEquipment[i]=pattern.getPattern()[i];
                    }
                }
            }
            patterns1.remove(pattern);
        }

        //mamy juz wszystkie patterny i uzupełniamy wiertłami ( i sprawdzenie czego brakuje )
        int[] drills = new int[numberOfDrills+1];
        List<Integer> emptyDrills = new LinkedList<>();
        for (int i = 0; i < newHeadEquipment.length; i++) {
            if(newHeadEquipment[i]==0){
                emptyDrills.add(i);
            } else {
                drills[ newHeadEquipment[i] ]++;
            }
        }

        int size = emptyDrills.size();
        for (int i = 1; i < drills.length; i++) {
            if(drills[i]==0){
                if(size!=0){
                    int index = rnd.nextInt(size);
                    newHeadEquipment[emptyDrills.get(index)]=i;
                    emptyDrills.remove(index);
                    size--;
                } else {
                    newHeadEquipment[rnd.nextInt(newHeadEquipment.length)]=i;
                }
            }
        }

        for (int i = 0; i < emptyDrills.size(); i++) {
            newHeadEquipment[emptyDrills.get(i)]=rnd.nextInt(numberOfDrills)+1;
        }

        return newHeadEquipment;
    }

    private void calculateSteps(){
        //dla listy possibleHeadPositions tworze nową listę i bangladesz;
        for (int i = 0; i < possibleHeadPositions.size(); i++) {
            int x = possibleHeadPositions.get(i).getX(); //pozycja głowicy
            int y = possibleHeadPositions.get(i).getY(); //pozycja głowicy

            //związki otworów
            int[] pattern = new int[headEquipment.length];

            List<Hole> possibleHoles = new LinkedList<>();
            for (int j = 0; j < possibleHeadPositions.get(i).getPossibleHoles().size(); j++) {
                Hole testedHole = possibleHeadPositions.get(i).getPossibleHoles().get(j);
                int testedX = testedHole.getX()-x;
                int testedY = testedHole.getY()-y;
                if(testedY==0){
                    // poziomo
                    if(headEquipment[testedX]==testedHole.getType()){
                        possibleHoles.add(new Hole(testedHole.getX(),testedHole.getY(),testedHole.getType()));
                        pattern[testedX] = headEquipment[testedX];
                        //uzywany otwor poziomo
                    }
                } else {
                    // pionowo
                    if(headEquipment[width+testedY-1]==testedHole.getType()){
                        possibleHoles.add(new Hole(testedHole.getX(),testedHole.getY(),testedHole.getType()));
                        pattern[width+testedY-1]=headEquipment[width+testedY-1];
                        //uzywany otwor pionowo
                    }
                }
            }

            if(possibleHoles.size()>=minimalNumberOfHoles){
                headPositions.add(new HeadPosition(x,y,possibleHoles));
                Pattern pattern1 = new Pattern(pattern,width);
                boolean newPattern = true;
                for (int j = 0; j < patternsList.size(); j++) {
                    if(patternsList.get(j).equals(pattern1)){
                        newPattern =false;
                        patternsList.get(j).use();
                        break;
                    }
                }
                if(newPattern){
                    patternsList.add(pattern1);
                }
            }
        }

        for (int i = 0; i < patternsList.size(); i++) {
            patternsList.get(i).minimisePattern();
        }

        minimizeNumberOfSteps(); // ustawenia gdzie jest ponad 2 otwory

        List<Hole>remainingHoles = getUndrilledHoles(holes,headPositions); // otwory które nie zostały wywiercone
        for (int i = 0; i < remainingHoles.size(); i++) { //wiercenie niewywierconych otworow
            for (int j = 0; j < headEquipment.length; j++) {
                if(remainingHoles.get(i).getType()== headEquipment[j]){
                    List<Hole> holeList = new LinkedList<>();
                    holeList.add(remainingHoles.get(i));
                    if(j<width){
                        int x = remainingHoles.get(i).getX()-j;
                        int y = remainingHoles.get(i).getY();
                        headPositions.add(new HeadPosition(x,y,holeList));
                    } else {
                        int x = remainingHoles.get(i).getX();
                        int y = remainingHoles.get(i).getY()-(j+width+1);
                        headPositions.add(new HeadPosition(x,y,holeList));
                    }
                    break;
                }
            }
        }
        numberOfSteps=headPositions.size();
    }

    private void minimizeNumberOfSteps() {
        List<HeadPosition> minimalHeadPositions = new LinkedList<>();
        while(headPositions.size()!=0){
            HeadPosition headPosition = max();
            if(headPosition.getPossibleHoles().size()!=0){
                minimalHeadPositions.add(new HeadPosition(headPosition));
            }
            headPositions.remove(headPosition);
            for (int i = 0; i < headPosition.getPossibleHoles().size(); i++) {
                Hole hole = headPosition.getPossibleHoles().get(i);
                for (int j = 0; j < headPositions.size(); j++) {
                    headPositions.get(j).getPossibleHoles().remove(hole);
                }
            } //usunięto wszytkie otwory aktualnego ustawienia głowicy
            for (int i = 0; i < headPositions.size(); i++) {
                if(headPositions.get(i).getPossibleHoles().size() < 2){
                    headPositions.remove(headPositions.get(i));
                }
            } //usuwam puste ustawienia
        }
        this.headPositions=minimalHeadPositions;
    }

    //najwięcej otworów przy przy pozycji głowicy
    private HeadPosition max(){
        HeadPosition max = headPositions.get(0);
        for (int i = 1; i < headPositions.size(); i++) {
            if(headPositions.get(i).getPossibleHoles().size()>max.getPossibleHoles().size()){
                max = headPositions.get(i);
            }
        }
        return max;
    }

    private List<Hole> getUndrilledHoles(List<Hole> allHoles, List<HeadPosition>headPositions){
        List<Hole> holes = new LinkedList<>(allHoles);
        for (int i = 0; i < headPositions.size(); i++) {
            for (int j = 0; j < headPositions.get(i).getPossibleHoles().size(); j++) {
                holes.remove(headPositions.get(i).getPossibleHoles().get(j));
            }
        }
        return holes;
    }


    //obliczanie drogi

    private void calculatePath(){
        double minValue = Double.MAX_VALUE;
        int minIndex = -1;
        headPositions.add(new HeadPosition(0,0,null));
        List<HeadPosition> newHeadPositions = new LinkedList<>();
        newHeadPositions.add(this.headPositions.get(rnd.nextInt(this.headPositions.size())));
        for (int i = 0; i < this.headPositions.size() - 1; i++) {
            for (int j = 0; j < this.headPositions.size(); j++) {
                if (calculateDistance(newHeadPositions.get(i), this.headPositions.get(j)) < minValue) {
                    if (!newHeadPositions.contains(this.headPositions.get(j))) {
                        minValue = calculateDistance(newHeadPositions.get(i), this.headPositions.get(j));
                        minIndex = j;
                    }
                }
            }
            newHeadPositions.add(this.headPositions.get(minIndex));
            minIndex = -1;
            minValue = Double.MAX_VALUE;
        }

        boolean improvement = false;
        while(improvement){
            improvement=false;
            double best_distance = calculatePathLength(newHeadPositions);
            for (int i = 0; i < newHeadPositions.size(); i++) {
                for (int j = 0; j < newHeadPositions.size(); j++) {
                    Collections.swap(newHeadPositions,i,j);
                    if(calculatePathLength(newHeadPositions) < best_distance){
                        best_distance=calculatePathLength(newHeadPositions);
                        improvement = true;
                        break;
                    }
                    else{
                        Collections.swap(newHeadPositions,j,i);
                    }
                }
                if(improvement){
                    break;
                }
            }
        }

        this.headPositions = newHeadPositions;
        this.pathLength = calculatePathLength(headPositions);
    }

    public double calculatePathLength(List<HeadPosition> headPositions) {
        double Phenotype = 0;
        for (int i = 0; i < headPositions.size()-1; i++) {
            Phenotype += calculateDistance(headPositions.get(i),headPositions.get(i+1));
        }
        Phenotype += calculateDistance(headPositions.get(headPositions.size()-1), headPositions.get(0));
        return Phenotype;
    }


    public static double calculateDistance(HeadPosition h1, HeadPosition h2){
        return Math.sqrt((h2.getX()-h1.getX())*(h2.getX()-h1.getX())+(h2.getY()-h1.getY())*(h2.getY()-h1.getY()));
    }

    //nadpisane metody
    @Override
    public boolean equals(Object obj) {
        Equipment eq2 = (Equipment)obj;
        for (int i = 0; i < headEquipment.length; i++) {
            if(this.headEquipment[i]!=eq2.headEquipment[i]){
                return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        String tmp ="";
        for (int i = 0; i < width; i++) {
            tmp+= headEquipment[i]+" ";
        }
        tmp+="\n";
        for (int i = width; i < headEquipment.length; i++) {
            tmp+= headEquipment[i]+"\n";
        }

        return tmp;
    }
}