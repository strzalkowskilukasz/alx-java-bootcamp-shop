package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pliki.ProduktDeserializer;
import pliki.ProduktSerializer;

import java.io.Serializable;
import java.util.*;


public class Sklep implements Serializable {
    //    skladowe

@JsonProperty("produkty")
    private Collection<Produkt> produkty;
@JsonProperty("magazyn")
@JsonSerialize( keyUsing =
        ProduktSerializer.class )
@JsonDeserialize(keyUsing = ProduktDeserializer.class)
    private Map<Produkt, Integer> magazyn;
    @JsonProperty("koszyk")
    @JsonSerialize( keyUsing =
            ProduktSerializer.class )
    @JsonDeserialize(keyUsing = ProduktDeserializer.class)
    private Map<Produkt, Integer> koszyk;
    @JsonProperty("zamowienie")
    private Map<Long, Object> zamowienie;
    private String nazwa;
    public static long generatorIdZamowienia = 0L;
    private long idZamowienia;

    public Sklep(){}
@JsonCreator
    public Sklep(String nazwa) {
        this.produkty = new ArrayList<>();
        this.magazyn = new LinkedHashMap<>();
        this.koszyk = new LinkedHashMap<>();
        this.zamowienie = new LinkedHashMap<>();
        this.nazwa = nazwa;
        generatorIdZamowienia++;
        this.idZamowienia = generatorIdZamowienia;
    }
// gettery

    public Collection<Produkt> getProdukty() {
        return produkty;
    }

    public Map<Produkt, Integer> getMagazyn() {
        return magazyn;
    }

    public Map<Produkt, Integer> getKoszyk() {
        return koszyk;
    }

    public Map<Long, Object> getZamowienie() {
        return zamowienie;
    }
@JsonIgnore
    public long getIdZamowienia() {
        generatorIdZamowienia++;
       return generatorIdZamowienia;
    }
@JsonIgnore
    public String getNazwa() {
        return nazwa;
    }

    //    metody


    public void setGeneratorId() {
        long IdMax = 0L;
        for ( Produkt temp : produkty){
            if (IdMax < temp.getId()){
                IdMax = temp.getId();
            }
        }
        Produkt.generatorId = IdMax;
    }
    public void setGeneratorIdZamowienia() {
        long IdMax = 0L;
        for ( Long temp : zamowienie.keySet()){
             if (IdMax < temp){
                 IdMax = temp;
            }
        }
            generatorIdZamowienia= IdMax;
    }
    public void dodaj(Produkt produkt) {
        produkty.add(produkt);
    }
    public void dodajdoKoszyka(Produkt produkt, int ilosc) {
        koszyk.put(produkt, ilosc);
    }

    public void utworzZamowienie(Object koszyk){
        zamowienie.put(getIdZamowienia(), koszyk);
    }

    public void wydajZamowienieZMagazynu(){
        for ( Produkt temp : getKoszyk().keySet()){
            wydajZMagazynu(temp, getKoszyk().get(temp));
        }
    }

    public void usun(long id) {

        Iterator<Produkt> iterator = produkty.iterator();
        while (iterator.hasNext()) {
            Produkt produkt = iterator.next();
            if (produkt.getId() == id) {
                iterator.remove();
            }
        }
    }

    public void wyswietl() {

        for (Produkt temp : produkty) {
            System.out.println(temp);
        }
    }

    public void przyjmijNaMagazyn(Produkt produkt, int ilosc){
        if (!magazyn.containsKey(produkt)) {
            magazyn.put(produkt, ilosc);
        } else {
           ilosc = magazyn.get(produkt) + ilosc;
            magazyn.replace(produkt, ilosc);
        }
    }
    public boolean wydajZMagazynu(Produkt produkt, int ilosc) {
        if (magazyn.get(produkt) < ilosc) {
            System.out.println("W magazynie nie ma wystarczającej ilości tego produktu. Obecny stan to: " + magazyn.get(produkt));
            return false;
        } else {
            int temp = magazyn.get(produkt) - ilosc;
            magazyn.replace(produkt, temp);
            System.out.println("Pobrano: " + ilosc + " szt. produktu o nazwie: " + produkt.getNazwa());
            System.out.println("Obecny stan tego produktu to: " + magazyn.get(produkt));
            return true;
        }
    }

    public void zmienStanMagazynowy(Produkt produkt) {
        magazyn.replace(produkt, 0);
    }
    public void wyswietlStanMagazynu(){
        System.out.println("Stan magazynu: ");
        for (Produkt p : magazyn.keySet()){
            System.out.println("ID produktu: " + p.getId() + "\nNazwa produktu: " + p.getNazwa() + "\nLiczba sztuk na magazynie: " + magazyn.get(p));
        }
        }

    public void wyswietlPosortowaneCenowo() {
        List<Produkt> kopiaProdukty = new ArrayList<>(produkty);
        Collections.sort(kopiaProdukty);
        Iterator<Produkt> it = kopiaProdukty.iterator();
        while (it.hasNext()){
            Produkt temp = it.next();
            System.out.println(temp);
        }
    }

    public void wyswietlPosortowanePoNazwie() {
        Comparator<Produkt> nazwaKomparator = new Comparator<Produkt>() {
            @Override
            public int compare(Produkt o1, Produkt o2) {
//                return o1.nazwa.compareTo(o2.nazwa);
                if(o1.nazwa.compareTo(o2.nazwa) > 0){
                    return 1;
                } else if(o1.nazwa.compareTo(o2.nazwa) < 0) {
                    return -1;
                } else
                    return 0;
            }
        };
        List<Produkt> kopiaProdukty = new ArrayList<>(produkty);
        Collections.sort(kopiaProdukty, nazwaKomparator);
        for (Produkt temp : kopiaProdukty) {
            System.out.println(temp);
        }
    }

    @Override
    public String toString() {
        return "Sklep{" +
                "produkty=" + produkty +
                ", magazyn=" + magazyn +
                ", nazwa='" + nazwa + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sklep sklep = (Sklep) o;
        return Objects.equals(produkty, sklep.produkty) &&
                Objects.equals(magazyn, sklep.magazyn) &&
                Objects.equals(nazwa, sklep.nazwa);
    }

    @Override
    public int hashCode() {

        return Objects.hash(produkty, magazyn, nazwa);
    }

}


