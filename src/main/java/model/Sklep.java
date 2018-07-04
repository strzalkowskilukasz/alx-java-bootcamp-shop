package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import pliki.ProduktDeserializer;
import pliki.ProduktSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;


public class Sklep implements Serializable {
    @JsonProperty("nazwa")
    private String nazwa;

    @JsonProperty("produkty")
    private Collection<Produkt> produkty;

    @JsonProperty("magazyn")
    @JsonSerialize(keyUsing =
            ProduktSerializer.class)
    @JsonDeserialize(keyUsing = ProduktDeserializer.class)
    private Map<Produkt, Integer> magazyn;

    @JsonProperty("koszyk")
    @JsonSerialize(keyUsing =
            ProduktSerializer.class)
    @JsonDeserialize(keyUsing = ProduktDeserializer.class)
    private Map<Produkt, Integer> koszyk;

    @JsonProperty("Aktualne ID zamówienia")
    private long idZamowienia = 0L;

    @JsonProperty("zamowienie")
    private Map<Long, Object> zamowienie;

    static BigDecimal kosztPrzesylkiDo5Kg = BigDecimal.valueOf(0);
    static BigDecimal kosztPrzesylkiDo10Kg = BigDecimal.valueOf(30.0);
    static BigDecimal kosztPrzesylkiPowyzej10Kg = BigDecimal.valueOf(50.0);
    static BigDecimal vatKomputer = BigDecimal.valueOf(0.23);
    static BigDecimal vatDron = BigDecimal.valueOf(0.18);

    public Sklep() {
    }

    @JsonCreator
    public Sklep(String nazwa) {
        this.produkty = new ArrayList<>();
        this.magazyn = new LinkedHashMap<>();
        this.koszyk = new LinkedHashMap<>();
        this.zamowienie = new LinkedHashMap<>();
        this.nazwa = nazwa;
        this.idZamowienia = 0L;
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
        return idZamowienia;
    }

    public String getNazwa() {
        return nazwa;
    }

// metody

/* GENERATORY ID */
    public void setGeneratorId() {
        long IdMax = 0L;
        for (Produkt temp : produkty) {
            if (IdMax < temp.getId()) {
                IdMax = temp.getId();
            }
        }
        Produkt.generatorId = IdMax;
    }

    public long wygenerujIdZamowienia() {
        idZamowienia++;
        return idZamowienia;
    }
/* PRODUKT I JEGO METODY */
    public void dodaj(Produkt produkt) {
        produkty.add(produkt);
        System.out.println("Dodano do katalogu nowy produkt o numerze ID: " + produkt.getId());
    }

    public void usun(long id) {

            Iterator<Produkt> iterator = produkty.iterator();
            while (iterator.hasNext()) {
                Produkt produkt = iterator.next();
                if (produkt.getId() == id) {
                    iterator.remove();
                    System.out.println("Usunięto z katalogu produkt nr: " + id);
                }
            }
        }

        public void wyswietl() {

            for (Produkt temp : produkty) {
            System.out.println(temp);
        }
    }

    public void wyswietlPosortowaneCenowo() {
        List<Produkt> kopiaProdukty = new ArrayList<>(produkty);
        Collections.sort(kopiaProdukty);
        Iterator<Produkt> it = kopiaProdukty.iterator();
        while (it.hasNext()) {
            Produkt temp = it.next();
            System.out.println(temp);
        }
    }

    public void wyswietlPosortowanePoNazwie() {
        List<Produkt> kopiaProdukty = new ArrayList<>(produkty);
//        Collections.sort(kopiaProdukty, new Comparator<Produkt>() {
//            @Override
//            public int compare(Produkt o1, Produkt o2) {
//                if (o1.nazwa.compareTo(o2.nazwa) > 0) {
//                    return 1;
//                } else if (o1.nazwa.compareTo(o2.nazwa) < 0) {
//                    return -1;
//                } else
//                    return 0;
//            }
//        });

//        Collections.sort(kopiaProdukty, (o1, o2) -> o1.nazwa.compareTo(o2.nazwa));
//        kopiaProdukty.sort((o1, o2) -> o1.nazwa.compareTo(o2.nazwa));
//        kopiaProdukty.sort(Comparator.comparing(o -> o.nazwa));
        kopiaProdukty.sort(Comparator.comparing(Produkt::getNazwa));

        for (Produkt temp : kopiaProdukty) {
            System.out.println(temp);
        }
    }
 /* MAGAZYN I JEGO METODY */

    public void przyjmijNaMagazyn(Produkt produkt, int ilosc) {
        if (!magazyn.containsKey(produkt)) {
            magazyn.put(produkt, ilosc);
        } else {
            int ilosc2 = magazyn.get(produkt) + ilosc;
            magazyn.replace(produkt, ilosc2);
        }
        System.out.println("Przyjęto na magazyn " + ilosc + " szt. produktu o numerze ID: " + produkt.getId());
    }

    public void wydajZMagazynu(Produkt produkt, int ilosc) throws ZaMaloTowaru{
        if (magazyn.get(produkt) > ilosc) {
            int temp = magazyn.get(produkt) - ilosc;
            magazyn.replace(produkt, temp);
            System.out.println("Pobrano z magazynu " + ilosc + " szt. produktu o nazwie: " + produkt.getNazwa());
            System.out.println("Obecny stan magazynowy tego produktu to: " + magazyn.get(produkt));

        } else {
            throw new ZaMaloTowaru("W magazynie nie ma wystarczającej ilości tego produktu. " + "Obecny stan to: " + magazyn.get(produkt));
        }
    }
    public void usunZMagazynu(Produkt produkt) {
        getMagazyn().remove(produkt);
        System.out.println("Usunięto z magazynu produkt o numerze ID: " + produkt.getId());
    }

    public void zmienStanMagazynowy(Produkt produkt) {
        magazyn.replace(produkt, 0);
        System.out.println("Wydano wszystkie sztuki produktu o nazwie " + produkt.getNazwa());
    }

    public void wyswietlStanMagazynu() {
        System.out.println("Stan magazynu: ");
        for (Produkt p : magazyn.keySet()) {
            System.out.println("ID produktu: " + p.getId() + "\nNazwa produktu: " + p.getNazwa() + "\nLiczba sztuk na magazynie: " + magazyn.get(p));
        }
    }
/* KOSZYK I JEGO METODY */

    public void dodajdoKoszyka(Produkt produkt, int ilosc) {
        if (koszyk.containsKey(produkt)) {
            koszyk.replace(produkt, koszyk.get(produkt) + ilosc);
        } else {
            koszyk.put(produkt, ilosc);
        }
        System.out.println("Dodano pozycję do zamówienia.");
    }

    public BigDecimal wartoscKoszyka() {
        BigDecimal wartoscProduktu = BigDecimal.ZERO;
        BigDecimal sumaWartosciProduktow = BigDecimal.ZERO;
        for (Produkt temp : koszyk.keySet()) {
            if (temp instanceof Komputer) {
                wartoscProduktu = temp.getCena().add(temp.getCena().multiply(vatKomputer)).multiply(new BigDecimal(koszyk.get(temp)));
            } else if (temp instanceof Dron) {
                wartoscProduktu = temp.getCena().add(temp.getCena().multiply(vatDron)).multiply(new BigDecimal(koszyk.get(temp)));
            }
            sumaWartosciProduktow = sumaWartosciProduktow.add(wartoscProduktu);
        }
        return sumaWartosciProduktow;
    }

    public BigDecimal kosztPrzesylki() {
        double lacznaWagaZamowienia = 0;
        double wagaPojedynczejPozycji = 0;

        for (Produkt temp : koszyk.keySet()) {
            wagaPojedynczejPozycji = temp.getWaga() * koszyk.get(temp);
            lacznaWagaZamowienia += wagaPojedynczejPozycji;
        }

        if (lacznaWagaZamowienia < 5) {
            return kosztPrzesylkiDo5Kg;
        } else if (lacznaWagaZamowienia < 10) {
            return kosztPrzesylkiDo10Kg;
        } else {
            return kosztPrzesylkiPowyzej10Kg;
        }
    }
/* ZAMÓWIENIE I JEGO METODY */

    public void utworzZamowienie(Object koszyk) {
        zamowienie.put(wygenerujIdZamowienia(), koszyk);

        System.out.println("Przyjęto nowe zamówienie nr: " + idZamowienia);
        System.out.println("Aby zapisać dane osobowe zamawiającego, zatwierdź zamówienie.");
    }

    public void usunZamowienie(long id) {
        boolean czyJest = false;
        Iterator<Long> it = zamowienie.keySet().iterator();
        while (it.hasNext()) {
            Long temp = it.next();
            if (temp == id) {
                it.remove();
                System.out.println("Usunięto zamówienie nr: " + id);
                czyJest = true;
            }
        }
                if (!czyJest){
                System.out.println("Nie ma zamówienia o takim numerze. Sprawdź listę zamówień.");
                }
        }

    public BigDecimal wartoscZamowienia() {
        return wartoscKoszyka().add(kosztPrzesylki());
    }

    public void oddajZamowienieDoMagazynu() {
        for (Produkt temp : getKoszyk().keySet()) {
            przyjmijNaMagazyn(temp, getKoszyk().get(temp));
        }
    }

    public void pokazSzczegolyZamowienia() {
        System.out.println("Zamówione produkty: ");
        int liczbaPorzadkowa = 0;
        for (Produkt temp : getKoszyk().keySet()) {
            liczbaPorzadkowa++;
            System.out.println(liczbaPorzadkowa + ":");
            System.out.print("Rodzaj produktu: ");
            if (temp instanceof Komputer) {
                System.out.println("Komputer");
            } else if (temp instanceof Dron){
                System.out.println("Dron");
            }
            System.out.println("Nazwa produktu: " + temp.getNazwa());
            if (temp instanceof Komputer) {
                System.out.println("Marka: " + ((Komputer) temp).getMarka());
                System.out.println("Ilość pamięci RAM: " + ((Komputer) temp).getRam() + " GB");
            } else if (temp instanceof Dron) {
                System.out.println("zasięg drona: " + ((Dron) temp).getZasieg() + " metrów");
                if (((Dron) temp).isCzyMaKamere()) {
                    System.out.println("Dron posiada kamerę.");
                } else {
                    System.out.println("Dron nie ma kamery na wyposażeniu.");
                }
            }
            System.out.println("cena netto za sztukę: " + temp.getCena() + " PLN netto");
            System.out.println("Ilość zamówionych sztuk: " + getKoszyk().get(temp));
            System.out.println();
        }
        System.out.println("Wartość wszystkich produktów z podatkiem: " + wartoscKoszyka() + " PLN netto");
        System.out.println("Koszt przesyłki: " + kosztPrzesylki() + " PLN netto");
        System.out.println("Suma zamówienia wraz z przesyłką: " + wartoscZamowienia() + " PLN netto");
    }

    @SuppressWarnings("unchecked cast- comiler forgets generics -> type of collection")
    public void pokazListeZamowien() {
        System.out.println("Poniżej aktualna lista zamówień: ");
        for (long temp : zamowienie.keySet()) {
            System.out.println("Numer zamówienia: " + temp);
            for (Map.Entry<Object, Integer> entry : ((Map<Object, Integer>) zamowienie.get(temp)).entrySet()) {
                    System.out.println("Produkt: " + entry.getKey());
                    System.out.println("Ilość: " + entry.getValue());
                }
            }
        }

    @Override
    public String toString() {
        return "\nSKLEP " + getNazwa() +
                "\nKATALOG PRODDUKTÓW: " + "\n" + produkty +
                "\nMAGAZYN: " + "\n" + magazyn +
                "\nOSTATNI KOSZYK: " + "\n" +  koszyk +
                "\nZAMÓWIENIA: " + "\n" + zamowienie;
    }
/* Dedykowane wyjątki*/

    public class ZaMaloTowaru extends Exception {
        public ZaMaloTowaru(String string){
            super(string);
        }
    }
}