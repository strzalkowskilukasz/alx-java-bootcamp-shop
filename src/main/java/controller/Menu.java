package controller;

import model.*;
import pliki.OperacjePlikowe;
import pliki.PlikiBinarne;
import pliki.PlikiJson;

import java.math.BigDecimal;
import java.util.*;

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
        System.out.println("0- wydrukuj rejestr sklepu");
        System.out.println("1- dodaj produkt");
        System.out.println("2- usuń produkt");
        System.out.println("3- wyświetl listę produktow");
        System.out.println("4- wyświetl posortowaną listę produktów po cenie");
        System.out.println("5- wyświetl posortowaną listę produktów po nazwie");
        System.out.println("6- przyjmij na magazyn");
        System.out.println("7- wydaj z magazynu");
        System.out.println("8- pokaż stan magazynu");
        System.out.println("9- przyjmij zamówienie");
        System.out.println("10- usuń zamówienie");
        System.out.println("11- pokaz listę zamowień sklepu");
        System.out.println("12- pokaż listę przyjętych wysyłek kuriera");
        System.out.println("q- wyjdź z programu i zapisz zmiany");

        String wybor;
        String wyborDodanie;
        WysylkaZamowien.Lista lista;
        Produkt produkt;

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
                        scanner.nextLine();
                    try {
                        switch (wyborDodanie) {
                            case "1":
                                System.out.print("Podaj nazwe komputera: ");
                                String nazwa = scanner.nextLine();
                                System.out.print("Podaj markę komputera: ");
                                String marka = scanner.nextLine();
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
                                nazwa = scanner.nextLine();
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
                        } catch (InputMismatchException e) {
                            System.out.println("Podałeś błędny format właściwości produktu.");
                            scanner.nextLine();
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
                    case "6":
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

                    break;
                        case "7":
                        System.out.print("Podaj id produktu, który chcesz pobrać z magazynu:");
                        id = scanner.nextLong();

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

                            try {
                                sklep.wydajZMagazynu(produkt, ilosc);
                            } catch (Sklep.ZaMaloTowaru ex) {
                                System.out.println(ex.getMessage());

                                boolean czyWystarczy = false;
                                do {
                                    System.out.println("Wybierz jedną z opcji: ");
                                    System.out.println("1: Pobierz wszystko");
                                    System.out.println("2: Pobierz inną ilość");
                                    System.out.println("3: Dodaj do magazynu (odbierz z hurtowni)");
                                    System.out.println("4: Wyjdź do głównego menu");

                                    wyborDodanie = scanner.next();
                                    switch (wyborDodanie) {
                                        case "1":
                                            sklep.zmienStanMagazynowy(produkt);

                                            break;
                                        case "2":
                                            System.out.print("Podaj iloś produktu do pobrania z magazynu: ");
                                            ilosc = scanner.nextInt();
                                            try {
                                                sklep.wydajZMagazynu(produkt, ilosc);
                                                czyWystarczy = true;
                                                break;
                                            } catch (Sklep.ZaMaloTowaru e) {
                                                System.out.println(e.getMessage());
                                            }
                                                break;

                                        case "3":
                                            System.out.print("Podaj iloś produktu: ");
                                            ilosc = scanner.nextInt();
                                            sklep.przyjmijNaMagazyn(produkt, ilosc);

                                            break;
                                        case "4":

                                            break;
                                        default:
                                            System.out.println("Podałeś błedną opcję. Powracam do głównego menu.");
                                            break;
                                    }
                                } while (wyborDodanie.equalsIgnoreCase("2") && !czyWystarczy);
                            }
                        }
                    break;
                case "8":
                    sklep.wyswietlStanMagazynu();

                    break;
                case "9":
                    sklep.getKoszyk().clear(); //czyszczę koszyk

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
                            System.out.print("Podaj ilość zamówionego produktu: ");
                            int ilosc = scanner.nextInt();
                            try {
                                sklep.wydajZMagazynu(produkt, ilosc);
                                sklep.dodajdoKoszyka(produkt, ilosc);
                            } catch (Sklep.ZaMaloTowaru zaMaloTowaru) {
                                System.out.println(zaMaloTowaru.getMessage());
                                boolean czyWystarczy = false;
                                do {
                                    System.out.println("Wybierz jedną z opcji: ");
                                    System.out.println("1: Anuluj tę pozycję zamówienia");
                                    System.out.println("2: Zamów inną ilość");

                                    wyborDodanie = scanner.next();
                                    switch (wyborDodanie) {
                                        case "1":
                                            System.out.println("Ta pozycja zamówienia została anulowana.");

                                            break;
                                        case "2":
                                            System.out.print("Podaj ilość zamówionego produktu: ");
                                            ilosc = scanner.nextInt();
                                            try {
                                                sklep.wydajZMagazynu(produkt, ilosc);
                                                czyWystarczy = true;
                                                sklep.dodajdoKoszyka(produkt, ilosc);

                                            } catch (Sklep.ZaMaloTowaru e) {
                                                System.out.println(e.getMessage());
                                            }

                                            break;
                                        default:
                                            System.out.println("Podałeś błedną opcję. Ta pozycja zamówienia została anulowana.");
                                            break;
                                    }
                                } while (!czyWystarczy && wyborDodanie.equalsIgnoreCase("2"));
                            }
                        }
                        System.out.println("Chcesz dodać do zamówienia kolejny produkt? T/N");
                        wyborDodanie = scanner.next();
                    } while (wyborDodanie.equalsIgnoreCase("T")) ;

                    if (sklep.getKoszyk().isEmpty()){
                        break;
                    }
                    System.out.println("Podaj dane kupującego.");
                    System.out.println();
                    scanner.nextLine();
                    System.out.println("Imię kupującego: ");
                    String imie = scanner.nextLine();
                    System.out.println("Nazwisko kupującego: ");
                    String nazwisko = scanner.nextLine();
                    System.out.println("Podaj adres dostawy. ");
                    System.out.println();
                    System.out.println("Podaj ulicę: ");
                    String ulica = scanner.nextLine();
                    System.out.println("Podaj kod pocztowy: ");
                    String kodPocztowy = scanner.nextLine();
                    System.out.println("Podaj miasto: ");
                    String miasto = scanner.nextLine();
                    System.out.println("Podaj numer telefonu ");
                    String telefon = scanner.nextLine();

                    sklep.utworzZamowienie(sklep.getKoszyk());
                    do {
                        System.out.println("1: Sprawdź szczegóły zamówienia");
                        System.out.println("2: Zatwierdź zamówienie (dodaj do listy kuriera)");
                        System.out.println("3: Cofnij zamówienie");
                        System.out.println("4: Wyjdź do głównego menu");

                        wyborDodanie = scanner.next();

                        switch (wyborDodanie) {

                            case "1":
                                sklep.pokazSzczegolyZamowienia();

                                break;
                            case "2":
                    try {
                        lista = pliki.wczytajZamowieniaKuriera();
                    } catch (Exception e) {
                        System.out.println("Blad odczytu pliku");
                        e.printStackTrace();
                        lista = new WysylkaZamowien.Lista();
                    }
                                WysylkaZamowien.Adres adres = new WysylkaZamowien.Adres(ulica, miasto, kodPocztowy);
                                WysylkaZamowien zamowienie = new WysylkaZamowien(imie, nazwisko, telefon, adres, sklep.wartoscZamowienia(), sklep.getIdZamowienia());

                                lista.dodajDoListyKuriera(zamowienie);

                                try {
                                    pliki.wyslijZamowienieKurierowi(lista);
                                } catch (Exception e) {
                                    System.out.println("Bląd zapisu do pliku");
                                    e.printStackTrace();
                                }
                                System.out.println();

                                break;
                            case "3":
                                sklep.usunZamowienie(sklep.getIdZamowienia());
                                sklep.oddajZamowienieDoMagazynu();

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
                    sklep.pokazListeZamowien();

                    break;
                case "12":
                    try {
                        lista = pliki.wczytajZamowieniaKuriera();
                    } catch (Exception e) {
                        System.out.println("Bląd odczytu pliku");
                        e.printStackTrace();
                        lista = new WysylkaZamowien.Lista();
                    }
                    lista.pokazListeKuriera();

                    break;
                case "q":
                case "Q":
                    System.out.println("Koniec");
                    // zapis do pliku
                    try {
                        pliki.zapisz(sklep);
                    } catch (Exception e) {
                        System.out.println("Bląd zapisu do pliku");
                        e.printStackTrace();
                    }
                    break;
                case "l":
                case "L":
                    System.out.println("0- wydrukuj rejestr sklepu");
                    System.out.println("1- dodaj produkt");
                    System.out.println("2- usuń produkt");
                    System.out.println("3- wyświetl listę produktow");
                    System.out.println("4- wyświetl posortowaną listę produktów po cenie");
                    System.out.println("5- wyświetl posortowaną listę produktów po nazwie");
                    System.out.println("6- przyjmij na magazyn");
                    System.out.println("7- wydaj z magazynu");
                    System.out.println("8- pokaż stan magazynu");
                    System.out.println("9- przyjmij zamówienie");
                    System.out.println("10- usuń zamówienie");
                    System.out.println("11- pokaz listę zamowień sklepu");
                    System.out.println("12- pokaż listę przyjętych wysyłek kuriera");
                    System.out.println("q- wyjdź z programu i zapisz zmiany");

                    break;
                default:
                    System.out.println("Podałeś błedną opcję.");
            }
        } while (!wybor.equalsIgnoreCase("q"));
    }
}
