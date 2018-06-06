package controller;

import model.*;
import pliki.OperacjePlikowe;
import pliki.PlikiBinarne;
import pliki.PlikiJson;

import java.math.BigDecimal;
import java.util.Scanner;

public class Menu {


    public void wyswietlMenu(){

        //odczyt z pliku
        Scanner scanner = new Scanner(System.in);

        OperacjePlikowe pliki;

        System.out.println("Jakie pliki chcesz wczytać?");
        System.out.println("1- pliki binarne");
        System.out.println("2- pliki json");
        String wyborOdczytuPlikow = scanner.next();
        switch (wyborOdczytuPlikow){
            case "1" :
            pliki = new PlikiBinarne();
                break;
            case "2" :
            pliki = new PlikiJson();
                break;
            default:
            pliki = new PlikiJson();
                break;
        }

        Sklep sklep;
        try {
            sklep = pliki.wczytaj();
        } catch (Exception e) {
            System.out.println("Blad odczytu, starujemy z pustym plikiem.");
            e.printStackTrace();
            sklep = new Sklep("komputeryidrony.com");
        }

        String wybor;
        String wyborDodanie;
        Produkt produkt;

        System.out.println("0- wydrukuj rejestr sklepu");
        System.out.println("1- dodaj produkt");
        System.out.println("2- usun produkt");
        System.out.println("3- wyswietl liste produktow");
        System.out.println("4- wyswietl posortowana liste produktow po cenie");
        System.out.println("5- wyswietl posortowana liste produktow po nazwie");
        System.out.println("6- przyjmij na magazyn");
        System.out.println("7- wydaj z magazynu");
        System.out.println("8- pokaz stan magazynu");
        System.out.println("9- przyjmij zamowienie");
        System.out.println("10- usun zamowienie");
        System.out.println("11- pokaz liste zamowien");
        System.out.println("q- wyjdz");

        do {

        System.out.print("Wybierz funkcję lub (L)ista poleceń: ");
        wybor = scanner.next();
            switch (wybor) {

                case "0":
                    System.out.println(sklep);

                    break;
                case "1":
                    sklep.setGeneratorId(); // sprawdzam i ustawiam generator po wczytaniu pliku
                    do {
                        System.out.println("Jaki produkt chcesz dodać? ");
                        System.out.println("1- Komputer ");
                        System.out.println("2- Dron");
                        wyborDodanie = scanner.next();

                        switch (wyborDodanie) {
                            case "1":
                                System.out.print("Podaj nazwe komputera: ");
                                String nazwa = scanner.next();
                                System.out.print("Podaj markę komputera: ");
                                String marka = scanner.next();
                                System.out.print("Podaj ilość ramu: ");
                                int ram = scanner.nextInt();
                                System.out.print("Podaj wagę komputera: ");
                                double waga = scanner.nextDouble();
                                System.out.print("Podaj cenę komputera: ");
                                BigDecimal cena = scanner.nextBigDecimal();
                                scanner.nextLine();

                                Komputer komputer = new Komputer(nazwa, cena, waga, marka, ram);
                                sklep.dodaj(komputer);
                                sklep.przyjmijNaMagazyn(komputer, 0);

                                break;
                            case "2":
                                System.out.print("Podaj nazwe drona: ");
                                nazwa = scanner.next();
                                System.out.print("Podaj zasięg drona: ");
                                double zasieg = scanner.nextDouble();
                                System.out.println("Czy dron ma kamerę? T/N");
                                String czyMa = scanner.next();
                                boolean czyMaKamere;
                                if (czyMa.equalsIgnoreCase("T")) {
                                    czyMaKamere = true;
                                } else {
                                    czyMaKamere = false;
                                }
                                System.out.print("Podaj wagę drona: ");
                                waga = scanner.nextDouble();
                                System.out.print("Podaj cenę drona: ");
                                cena = scanner.nextBigDecimal();
                                scanner.nextLine();

                                Dron dron = new Dron(nazwa, cena, waga, zasieg, czyMaKamere);
                                sklep.dodaj(dron);
                                sklep.przyjmijNaMagazyn(dron, 0);

                                break;
                        }
                        System.out.println("Chcesz dodać kolejny produkt? T/N");
                        wyborDodanie = scanner.next();
                    } while (wyborDodanie.equalsIgnoreCase("T"));

                    break;
                case "2":
                    produkt = null;
                    do {
                        System.out.print("Podaj numer ID produktu, który chcesz usunąć z katalogu sklepu: ");
                        long id = scanner.nextLong();
                        for (Produkt s : sklep.getProdukty()) {
                            if (s.getId() == id) {
                                produkt = s;
                                break;
                            }
                        }
                        if (produkt == null) {
                            System.out.println("Nie ma takiego produktu w sklepie. Dodaj produkt lub podaj poprawny numer ID produktu.");
                        } else {
                            sklep.usun(id);
                            System.out.println("Czy chcesz usunąć ten produkt również z magazynu? T/N");
                            wyborDodanie = scanner.next();
                            if (wyborDodanie.equalsIgnoreCase("T")){
                                sklep.usunZMagazynu(produkt);
                            }
                        }
                            System.out.println("Chcesz usunąć kolejny produkt? T/N");
                            wyborDodanie = scanner.next();
                    } while (wyborDodanie.equalsIgnoreCase("T"));

                    break;
                case "3":
                        sklep.wyswietl();

                    break;
                case "4":
                    sklep.wyswietlPosortowaneCenowo();

                    break;
                    case "5":
                    sklep.wyswietlPosortowanePoNazwie();

                    break;
                    case "6":{
                        System.out.print("Podaj ID produktu, który chcesz dodać do magazynu:");
                        long id = scanner.nextLong();
                        produkt = null;
                        for (Produkt s : sklep.getProdukty()){
                            if (s.getId() == id) {
                                produkt = s;
                                break;
                            }
                        }
                        if (produkt == null) {
                            System.out.println("Nie ma takiego produktu w sklepie. Dodaj produkt lub podaj poprawny numer ID produktu.");
                        } else {
                            System.out.print("Podaj iloś produktu: ");
                            int ilosc = scanner.nextInt();
                            sklep.przyjmijNaMagazyn(produkt, ilosc);
                        }

                    break;}
                        case "7":
                        System.out.print("Podaj id produktu, który chcesz pobrać z magazynu:");
                        long id = scanner.nextLong();

                        produkt = null;
                        for (Produkt s : sklep.getProdukty()){
                            if (s.getId() == id) {
                                produkt = s;
                                break;
                            }
                        }
                        if (produkt == null) {
                            System.out.println("Nie ma takiego produktu w sklepie. Dodaj produkt lub podaj poprawny numer ID produktu.");
                        } else {

                            System.out.print("Podaj iloś produktu do pobrania z magazynu: ");
                            int ilosc = scanner.nextInt();
                            boolean czyWystarczy = (sklep.wydajZMagazynu(produkt, ilosc));
                            do {
                                if (czyWystarczy) {
                                    break;
                                } else {
                                    System.out.println("Wybierz jedną z opcji: ");
                                    System.out.println("1: Pobierz wszystko");
                                    System.out.println("2: Pobierz inną ilość");
                                    System.out.println("3: Zamów towar"); // do napisania
                                    System.out.println("4: Dodaj do magazynu");
                                    System.out.println("5: Wyjdź do głównego menu");


                                    wyborDodanie = scanner.next();
                                    switch (wyborDodanie) {

                                        case "1":
                                            sklep.zmienStanMagazynowy(produkt);
                                            break;
                                        case "2":
                                            System.out.print("Podaj iloś produktu do pobrania z magazynu: ");
                                            ilosc = scanner.nextInt();
                                czyWystarczy = (sklep.wydajZMagazynu(produkt, ilosc));
//                                            sklep.wydajZMagazynu(produkt, ilosc);

                                            break;
                                        case "3":
                                            System.out.println("Funkcja jeszcze niedostępna");

                                            break;
                                        case "4":
                                            System.out.print("Podaj iloś produktu: ");
                                            ilosc = scanner.nextInt();
                                            sklep.przyjmijNaMagazyn(produkt, ilosc);

                                            break;
                                        case "5":

                                            break;
                                        default:
                                            System.out.println("Podałeś błedną opcję.");
                                            break;
                                    }
                                }
                            } while (!czyWystarczy && wyborDodanie.equalsIgnoreCase("2"));
                        }
                    break;
                case "8":
                    sklep.wyswietlStanMagazynu();

                    break;
                case "9":
                    sklep.setIdZamowienia(); // sprawdzam i ustawiam generator zamowienia po wczytaniu pliku
                    sklep.getKoszyk().clear(); //czyszcze koszyk

                    WysylkaZamowien.Lista lista;
                    try {
                        lista = pliki.wczytajZamowieniaKuriera();
                    } catch (Exception e) {
                        System.out.println("Blad odczytu pliku");
                        e.printStackTrace();
                        lista = new WysylkaZamowien.Lista();
                    }

                    do {
                        System.out.print("Podaj ID zakupionego produktu: ");
                        id = scanner.nextLong();
                        produkt = null;
                        for (Produkt s : sklep.getProdukty()) {
                            if (s.getId() == id) {
                                produkt = s;
                                break;
                            }
                        }
                        if (produkt == null) {
                            System.out.println("Nie ma takiego produktu w sklepie. Podaj poprawny numer ID produktu.");
                        } else {

                            System.out.print("Podaj iloś zamówionego produktu: ");
                            int ilosc = scanner.nextInt();

                            sklep.dodajdoKoszyka(produkt, ilosc);
                        }
                        System.out.println("Chcesz dodać do zamówienia kolejny produkt? T/N");
                        wyborDodanie = scanner.next();
                    } while (wyborDodanie.equalsIgnoreCase("T")) ;

                    System.out.println("Podaj dane kupującego.");
                    System.out.println();
                    System.out.println("Imię kupującego: ");
                    String imie = scanner.next();
                    System.out.println("Nazwisko kupującego: ");
                    String nazwisko = scanner.next();
                    System.out.println("Podaj adres dostawy. ");
                    System.out.println();
                    System.out.println("Podaj ulicę: ");
                    String ulica = scanner.next();
                    System.out.println("Podaj kod pocztowy: ");
                    String kodPocztowy = scanner.next();
                    System.out.println("Podaj miasto: ");
                    String miasto = scanner.next();
                    System.out.println("Podaj numer telefonu ");
                    String telefon = scanner.next();

                    sklep.utworzZamowienie(sklep.getKoszyk());
                    do {
                        System.out.println("1: Sprawdź szczegóły zamówienia");
                        System.out.println("2: Zatwierdź zamówienie (dodaj do listy kuriera i pobierz towar z magazynu)");
                        System.out.println("3: Cofnij zamówienie");
                        System.out.println("4: Wyjdź do głównego menu");

                        wyborDodanie = scanner.next();

                        switch (wyborDodanie) {

                            case "1":
                                sklep.pokazSzczegolyZamowienia();

                                break;
                            case "2":

                                WysylkaZamowien.Adres adres = new WysylkaZamowien.Adres(ulica, miasto, kodPocztowy);
                                WysylkaZamowien zamowienie = new WysylkaZamowien(imie, nazwisko, telefon, adres, sklep.wartoscZamowienia(), sklep.getIdZamowienia());

                                lista.dodajDoListyKuriera(zamowienie);

                                try {
                                    pliki.wyslijZamowienieKurierowi(lista);
                                } catch (Exception e) {
                                    System.out.println("Blad zapisu do pliku");
                                    e.printStackTrace();
                                }
                                System.out.println();
                                sklep.wydajZamowienieZMagazynu();

                                break;
                            case "3":
                                sklep.usunZamowienie(sklep.getIdZamowienia());

                                break;
                            case "4":
                                break;
                        }
                    } while(wyborDodanie.equalsIgnoreCase("1"));

                    break;
                case "10":
                    System.out.print("Podaj numer zamówienie do usunięcia: ");
                    id = scanner.nextLong();
                    sklep.usunZamowienie(id);

                    break;
                case "11":
                    System.out.println("Poniżej aktualna lista zamówień: ");
                    sklep.pokazListeZamowien();
                case "q":
                case "Q":                           // Jeśli dwa case po soboie to albo albo
                    System.out.println("Koniec");
                    // zapis do pliku
                    try {
                        pliki.zapisz(sklep);
                    } catch (Exception e) {
                        System.out.println("Blad zapisu do pliku");
                        e.printStackTrace();
                    }
                    break;
                case "l":
                case "L":
                    System.out.println("0- wydrukuj rejestr sklepu");
                    System.out.println("1- dodaj produkt");
                    System.out.println("2- usun produkt");
                    System.out.println("3- wyswietl liste produktow");
                    System.out.println("4- wyswietl posortowana liste produktow po cenie");
                    System.out.println("5- wyswietl posortowana liste produktow po nazwie");
                    System.out.println("6- przyjmij na magazyn");
                    System.out.println("7- wydaj z magazynu");
                    System.out.println("8- pokaz stan magazynu");
                    System.out.println("9- przyjmij zamowienie");
                    System.out.println("10- usun zamowienie");
                    System.out.println("11- pokaz liste zamowien");
                    System.out.println("q- wyjdz");

                    break;
                default:
                    System.out.println("Podałeś błedną opcję.");
            }
        } while (!wybor.equalsIgnoreCase("q"));
    }
}
