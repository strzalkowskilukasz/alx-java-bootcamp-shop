package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

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
    }
}
}
