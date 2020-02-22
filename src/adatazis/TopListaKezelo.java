package adatazis;

import adatazis.TopLista;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TopListaKezelo {
    private final Connection kapcsolat;
    private final PreparedStatement beszurasSQL;

    public TopListaKezelo() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:/sakk";
        this.kapcsolat = DriverManager.getConnection(dbURL, "root", "");
        String beszuroLekerdezes = "INSERT INTO toplista (idopont, nev, pontszam) VALUES (?, ?, ?)";
        this.beszurasSQL = this.kapcsolat.prepareStatement(beszuroLekerdezes);
    }

    public List<TopLista> getTopLista() throws SQLException {
        List<TopLista> topLista = new ArrayList();
        String query = "SELECT * FROM toplista ORDER BY pontszam DESC";
        Statement allapot = this.kapcsolat.createStatement();
        ResultSet eredmenyHalmaz = allapot.executeQuery(query);

        while(eredmenyHalmaz.next()) {
            String nev = eredmenyHalmaz.getString("nev");
            int pontSzam = eredmenyHalmaz.getInt("pontszam");
            Date idoPont = eredmenyHalmaz.getDate("idopont");
            topLista.add(new TopLista(nev, pontSzam, idoPont));
        }

        return topLista;
    }

    public void insertTopLista(String nev, int pontSzam) throws SQLException {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.beszurasSQL.setTimestamp(1, ts);
        this.beszurasSQL.setString(2, nev);
        this.beszurasSQL.setInt(3, pontSzam);
        this.beszurasSQL.executeUpdate();
    }
}