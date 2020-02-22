package adatazis;

import java.util.Date;

public class TopLista {
    private final String nev;
    private final int pontSzam;
    private final Date ido;

    public TopLista(String nev, int pontSzam, Date ido) {
        this.nev = nev;
        this.pontSzam = pontSzam;
        this.ido = ido;
    }

    public String getNev() {
        return this.nev;
    }

    public int getPontSzam() {
        return this.pontSzam;
    }

    public Date getIdoPont() {
        return this.ido;
    }

    public String toString() {
        return String.format("%s %d %s", this.nev, this.pontSzam, this.ido);
    }
}
