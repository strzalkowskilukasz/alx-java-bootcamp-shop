package model;

import java.math.BigDecimal;

public class Komputer extends Produkt {

    private String marka;
    private int ram;

    public Komputer(){}
    public Komputer(String nazwa, BigDecimal cena, String marka, int ram) {
       super(nazwa, cena);
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
        return "Komputer{" +
                "nazwa='" + nazwa + '\'' +
                ", cena=" + cena +
                ", marka='" + marka + '\'' +
                ", ram=" + ram +
                ", id=" + id +
                '}';
    }
}
