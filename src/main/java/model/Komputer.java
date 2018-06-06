package model;

import java.math.BigDecimal;

public class Komputer extends Produkt {

    private String marka;
    private int ram;

    public Komputer(){}
    public Komputer(String nazwa, BigDecimal cena, double waga, String marka, int ram) {
       super(nazwa, cena, waga);
        this.marka = marka;
        this.ram = ram;
    }

    public String getMarka() {
        return marka;
    }

    public int getRam() {
        return ram;
    }

    @Override
    public String toString() {
        return
                "|| Komputer " + " | " +
                "Nazwa: " + nazwa  + " | " +
                "Marka: " + marka  + " | " +
                "Ilość pamięci RAM: " + ram + " GB" + " | " +
                "Waga: " + waga + " kg" + " | " +
                "Cena: " + cena + " PLN netto" + " | " +
                "Numer ID: " + id + " || ";
    }
}
