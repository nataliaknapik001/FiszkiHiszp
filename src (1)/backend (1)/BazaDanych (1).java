
package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class BazaDanych {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=FiszkiDB;private static final String DB_URL = \"jdbc:sqlserver://localhost:1433;databaseName=FiszkiDB;encrypt=true;trustServerCertificate=true;loginTimeout=30";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = System.getenv("FISZKI_DB_PASSWORD");
    
    public static Map<String, String> pobierzSlowka(int kategoriaId) {
        Map<String, String> slowkaMapa = new LinkedHashMap<>();
        Connection polaczenie = null;
        PreparedStatement skladnia = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            polaczenie = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            skladnia = polaczenie.prepareStatement("SELECT polski, hiszpanski FROM slowka WHERE kategoria_id = ?");
            skladnia.setInt(1, kategoriaId);
            rs = skladnia.executeQuery();
            
            while (rs.next()) {
                String polskie = rs.getString("polski").trim();
                String hiszpanskie = rs.getString("hiszpanski").trim();
                slowkaMapa.put(polskie, hiszpanskie);
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd bazy danych: " + e.getMessage(), "Błąd - aplikacja FiszkiDB", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Brak sterownika JDBC: " + e.getMessage(), "Błąd - aplikacja FiszkiDB", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (skladnia != null) skladnia.close();
                if (polaczenie != null) polaczenie.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Błąd przy zamykaniu zasobów: " + e.getMessage(), "Błąd - aplikacja FiszkiDB", JOptionPane.ERROR_MESSAGE);
            }
        }
        return slowkaMapa;
    }
    
    public static Map<String, Integer> pobierzKategorie() {
        Map<String, Integer> kategorie = new LinkedHashMap<>();
        Connection polaczenie = null;
        PreparedStatement skladnia = null;
        ResultSet rs = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            polaczenie = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            skladnia = polaczenie.prepareStatement("SELECT id, nazwa FROM kategorie");
            rs = skladnia.executeQuery();

            while (rs.next()) {
                kategorie.put(rs.getString("nazwa"), rs.getInt("id"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd bazy danych: " + e.getMessage(), "Błąd - aplikacja FiszkiDB", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Brak sterownika JDBC: " + e.getMessage(), "Błąd - aplikacja FiszkiDB", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (skladnia != null) skladnia.close();
                if (polaczenie != null) polaczenie.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Błąd przy zamykaniu zasobów: " + e.getMessage(), "Błąd - aplikacja FiszkiDB", JOptionPane.ERROR_MESSAGE);
            }
        }
        return kategorie;
    }
}