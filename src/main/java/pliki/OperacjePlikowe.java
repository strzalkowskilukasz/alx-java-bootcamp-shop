package pliki;

import model.Sklep;
import model.WysylkaZamowien;

public interface OperacjePlikowe {

    public abstract void zapisz(Sklep sklep) throws Exception; //wszystkie funkcje Interfejsu są z założenia publiczne i abstarkcyje, więc ten zapis jest redundantny
    Sklep wczytaj() throws Exception;
    void wyslijZamowienieKurierowi(WysylkaZamowien.Lista wysylka) throws Exception;
    WysylkaZamowien.Lista wczytajZamowieniaKuriera() throws Exception;
}
