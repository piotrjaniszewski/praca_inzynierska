package pl.piotrjaniszewski.inz.inverover;

import pl.piotrjaniszewski.inz.inverover.utils.PobierzPunkty;
import pl.piotrjaniszewski.inz.inverover.utils.Punkt;

import java.util.Random;

public class InverOver {

    private Punkt[] punkty;
    private Random random;
    private int rozmiarPopulacji;
    private long czasTrwania;
    double p;
    private Path[] populacja;
    private Path najlepszy;
    public  int ilosc;

    public InverOver(int rozmiarPopulacji, long czasTrwania, double p, String nazwaPliku) {
        this.rozmiarPopulacji = rozmiarPopulacji;
        this.czasTrwania = czasTrwania;
        this.p = p;
        punkty= PobierzPunkty.ZPlikuTsp(nazwaPliku);
        random = new Random();
    }

    public Path getNajlepszy() {
        return najlepszy;
    }

    public void StartClassic(){
        populacja = populacjaZachlanna(this.rozmiarPopulacji);
//        populacja = populacjaLosowa(this.rozmiarPopulacji);

        najlepszy=populacja[0];

        long start = System.currentTimeMillis();
        while(System.currentTimeMillis()-start<czasTrwania){
            ilosc++;
            for (int i = 0; i < rozmiarPopulacji; i++) {
                Path aktualnyOsobnik = new Path(populacja[i].getPath());
                int losoweMiasto = random.nextInt(punkty.length-1);
                while(true){
                    int miastoPrim;
                    if(random.nextDouble()<=p){
                        do{
                            miastoPrim = random.nextInt(punkty.length-1);
                        }while (miastoPrim==losoweMiasto);
                    }
                    else{
                        Path losowyOsobnik;
                        int index;
                        do{
                            index=random.nextInt(rozmiarPopulacji-1);
                        } while (index==i);
                        losowyOsobnik=populacja[index];
                        miastoPrim = znajdzIndex(aktualnyOsobnik.getPath()[losoweMiasto],losowyOsobnik.getPath())+1;
                        if(miastoPrim==punkty.length){
                            miastoPrim=0;
                        }
                    }
                    if (losoweMiasto == miastoPrim + 1 || losoweMiasto == miastoPrim - 1 || (miastoPrim == 0 && losoweMiasto == punkty.length - 1 || (losoweMiasto == 0 && miastoPrim == punkty.length - 1))) {
                        break;
                    }
                    aktualnyOsobnik.odwroc(losoweMiasto,miastoPrim);
                    losoweMiasto=miastoPrim;
                    if (obliczFenotyp(aktualnyOsobnik.getPath()) <= obliczFenotyp(najlepszy.getPath())) {
                        najlepszy = aktualnyOsobnik;
                        break;
                    }
                }
                if(obliczFenotyp(aktualnyOsobnik.getPath())<=obliczFenotyp(populacja[i].getPath())){
                    populacja[i]=aktualnyOsobnik;
                }
            }
        }

        for (int i = 0; i < populacja.length; i++) {
            if(obliczFenotyp(populacja[i].getPath()) < obliczFenotyp(najlepszy.getPath())){
                najlepszy=populacja[i];
            }
        }
    }

    private static int znajdzIndex(int value, int[] genotyp){
        for (int i = 0; i < genotyp.length; i++) {
            if (genotyp[i]==value){
                return i;
            }
        }
        return -1;
    }

    public double obliczFenotyp(int[] genotyp) {
        double fenotyp = 0;
        for (int i = 0; i < genotyp.length-1; i++) {
            fenotyp += Punkt.odlegloscKartezjanska(punkty[genotyp[i]],punkty[genotyp[i+1]]);
        }
        fenotyp += Punkt.odlegloscKartezjanska(punkty[genotyp[punkty.length-1]],punkty[genotyp[0]]);
        return fenotyp;
    }
    public Path[] populacjaZachlanna(int rozmiar) {
        Path[] populacjaStartowa = new Path[rozmiar];

        for (int i = 0; i < populacjaStartowa.length; i++) {
            populacjaStartowa[i]=zachlannyOsobnik();
        }
        return populacjaStartowa;
    }
    public Path zachlannyOsobnik() {
            double minValue = Double.MAX_VALUE;
            int minIndex = -1;

            int[] genotyp = new int[punkty.length];
            for (int i = 0; i < genotyp.length; i++) {
                genotyp[i] = -1;
            }

            genotyp[0] = random.nextInt(punkty.length);
            for (int i = 0; i < genotyp.length - 1; i++) {
                for (int j = 0; j < genotyp.length; j++) {
                    if (Punkt.odlegloscKartezjanska(punkty[genotyp[i]], punkty[j]) < minValue) {
                        if (!zawiera(genotyp, j, i + 1)) {
                            minValue = Punkt.odlegloscKartezjanska(punkty[genotyp[i]], punkty[j]);
                            minIndex = j;
                        }
                    }
                }
                genotyp[i + 1] = minIndex;
                minIndex = -1;
                minValue = Double.MAX_VALUE;
            }

        return new Path(genotyp);
    }
    private boolean zawiera(int[] genotyp, int value,int length){
        for (int i = 0; i < length; i++) {
            if(genotyp[i] == value) {
                return true;
            }
        }
        return false;
    }
}