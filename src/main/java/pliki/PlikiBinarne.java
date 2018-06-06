package pliki;

import model.Sklep;
import model.WysylkaZamowien;

import java.io.*;

public class PlikiBinarne implements OperacjePlikowe {

    //InputStream, OutputStrem- pliki binarne
    //Reader (Scanner), Writer- pliki tekstowe

    public void zapisz(Sklep sklep) throws IOException {
        FileOutputStream fos = new FileOutputStream("sklep.bin");

        ObjectOutputStream ous = new ObjectOutputStream(fos); // OUS obudowuje drugi (wzorzec projewktowy- dekorator) daje mu więcej funkcji.
        ous.writeObject(sklep); //zapisuje object sklep w postaci bajtów do liku binarnego.

        ous.close();
        fos.close(); //zamykamy w odwrotnej kolejnosci niz otwieralismy
    }

    public Sklep wczytaj() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("sklep.bin");

        ObjectInputStream ois = new ObjectInputStream(fis);
        Sklep sklep = (Sklep) ois.readObject();

        ois.close();
        fis.close();

        return sklep;
    }

    @Override
    public WysylkaZamowien.Lista wczytajZamowieniaKuriera() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("kurier.bin");

        ObjectInputStream ois = new ObjectInputStream(fis);
        WysylkaZamowien.Lista wysylka = (WysylkaZamowien.Lista) ois.readObject();

        ois.close();
        fis.close();

        return wysylka;
    }

    @Override
    public void wyslijZamowienieKurierowi(WysylkaZamowien.Lista wysylka) throws Exception {
        FileOutputStream fos = new FileOutputStream("kurier.bin");

        ObjectOutputStream ous = new ObjectOutputStream(fos);
        ous.writeObject(wysylka);

        ous.close();
        fos.close();

    }
}


