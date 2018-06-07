package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class WysylkaZamowien implements Serializable {

    private String imie;
    private String nazwisko;
    private String telefon;
    private Adres adres;
    private BigDecimal naleznosc;
    private long idZamowienia;

    public WysylkaZamowien() {
    }

    public WysylkaZamowien(String imie, String nazwisko, String telefon, Adres adres, BigDecimal naleznosc, long idZamowienia) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.telefon = telefon;
        this.adres = adres;
        this.naleznosc = naleznosc;
        this.idZamowienia = idZamowienia;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getTelefon() {
        return telefon;
    }

    public Adres getAdres() {
        return adres;
    }

    public BigDecimal getNaleznosc() {
        return naleznosc;
    }

    public long getIdZamowienia() {
        return idZamowienia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WysylkaZamowien that = (WysylkaZamowien) o;
        return idZamowienia == that.idZamowienia &&
                Objects.equals(imie, that.imie) &&
                Objects.equals(nazwisko, that.nazwisko) &&
                Objects.equals(telefon, that.telefon) &&
                Objects.equals(adres, that.adres) &&
                Objects.equals(naleznosc, that.naleznosc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(imie, nazwisko, telefon, adres, naleznosc, idZamowienia);
    }

    public static class Adres implements Serializable {
        private String ulica;
        private String miasto;
        private String kodPocztowy;

        public Adres() {
        }

        public Adres (String ulica, String miasto, String kodPocztowy){
            this.ulica = ulica;
            this.miasto = miasto;
            this.kodPocztowy = kodPocztowy;
        }

        public String getUlica() {
            return ulica;
        }

        public String getMiasto() {
            return miasto;
        }

        public String getKodPocztowy() {
            return kodPocztowy;
        }

        @Override
        public String toString() {
            return "Adres: " +
                    "\nUlica: " + ulica  +
                    "\nMiasto: " + miasto +
                    "\nKod pocztowy: " + kodPocztowy;
        }
    }
public static class Lista implements Serializable {
        @JsonProperty("lista kuriera")
    private Collection<WysylkaZamowien> lista;

        public Lista (){
            this.lista = new ArrayList<>();

    }
    public Collection<WysylkaZamowien> getLista() {
        return lista;
    }
    public void dodajDoListyKuriera (WysylkaZamowien zamowienie){
            if (lista.contains(zamowienie)){
            System.out.println("To zamówienie już jest na liście kuriera.");
        } else
            lista.add(zamowienie);
        System.out.println("Zamówienie nr " + zamowienie.getIdZamowienia() + " dodano do listy kuriera.");
    }
    public void pokazListeKuriera() {
        System.out.println("Lista zamówień kuriera: ");
        System.out.println();
            for (WysylkaZamowien temp : lista) {
                System.out.println("ID zamówienia: " + temp.getIdZamowienia());
                System.out.println("Dane odbiorcy: ");
                System.out.println("Imię i nazwisko: " + temp.getImie() + " " + temp.getNazwisko());
                System.out.println(temp.getAdres());
                System.out.println("Telefon: " + temp.getTelefon());
                System.out.println("Należność: " + temp.getNaleznosc() + " PLN");
                System.out.println();
            }
    }
}
}
