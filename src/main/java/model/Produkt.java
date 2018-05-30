package model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "typ")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Komputer.class, name = "komputer"),
        @JsonSubTypes.Type(value = Dron.class, name = "dron")
})

public abstract class Produkt implements Comparable<Produkt>, Serializable {

    protected String nazwa;
    protected BigDecimal cena;
    protected long id;

    static long generatorId = 0L;

    public Produkt(){}
    public Produkt(String nazwa, BigDecimal cena) {
        this.nazwa = nazwa;
        this.cena = cena;
        generatorId++;
        this.id = generatorId;
    }

//    public void setId(long id) {
//        this.id = id;
//    }

    public String getNazwa() {
        return nazwa;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public long getId() {
        return id;
    }

    @Override
    public int compareTo(Produkt o) {
        if(this.cena.compareTo(o.cena) > 0){
            return 1;
        } else if(this.cena.compareTo(o.cena) < 0) {
            return -1;
        } else
        return 0;
    }

    @Override
    public String toString() {
        return "Produkt{" +
                "nazwa='" + nazwa + '\'' +
                ", cena=" + cena +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produkt produkt = (Produkt) o;
        return id == produkt.id &&
                Objects.equals(nazwa, produkt.nazwa) &&
                Objects.equals(cena, produkt.cena);
    }

    @Override
    public int hashCode() {

        return nazwa.hashCode() + cena.hashCode() + Long.hashCode(id);
//        return Objects.hash(nazwa, cena, id);
    }
}
