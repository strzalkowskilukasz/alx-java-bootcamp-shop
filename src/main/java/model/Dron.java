package model;

import java.math.BigDecimal;

public class Dron extends Produkt{


    private double zasieg;
    private boolean czyMaKamere;

    public Dron(){}
    public Dron(String nazwa, BigDecimal cena, double waga, double zasieg, boolean czyMaKamere) {
        super(nazwa, cena, waga);
        this.zasieg = zasieg;
        this.czyMaKamere = czyMaKamere;
    }

    public double getZasieg() {
        return zasieg;
    }

    public boolean isCzyMaKamere() {
        return czyMaKamere;
    }
    public String czyPosiadaKamere() {
        if (isCzyMaKamere()) {
            return "TAK";

        } else {
            return "NIE";
        }
    }

    @Override
    public String toString() {
        return
                "|| Dron " + " | " +
                "Nazwa: " + nazwa  + " | " +
                "Zasięg: " + zasieg  + " metrów" + " | " +
                "Czy posiada kamerę? " + czyPosiadaKamere() + " | " +
                "Waga: " + waga + " kg" + " | " +
                "Cena: " + cena + " PLN netto" + " | " +
                "Numer ID: " + id + " || ";
    }
}
