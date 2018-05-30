package pliki;

import model.Sklep;

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

    public Sklep wczytaj()throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("sklep.bin");

        ObjectInputStream ois = new ObjectInputStream(fis);
        Sklep sklep = (Sklep) ois.readObject();

        ois.close();
        fis.close();

        return sklep;
    }
}


