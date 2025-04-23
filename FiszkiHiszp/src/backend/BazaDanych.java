package backend;

import java.sql.*;
import java.util.*;

public class BazaDanych {
    private static final String DB_URL = "jdbc:sqlserver://localhost\\MSSQLSERVER1:1433;databaseName=FiszkiDB;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USER = "app_user";
    private static final String DB_PASSWORD = "SilneHaslo123!";

    public static Connection polaczZBaza() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static Map<String, Integer> pobierzKategorie() {
        Map<String, Integer> kategorie = new LinkedHashMap<>();
        String sql = "SELECT id, nazwa FROM Kategorie ORDER BY nazwa";

        try (Connection conn = polaczZBaza();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                kategorie.put(rs.getString("nazwa"), rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kategorie;
    }

    public static List<String> pobierzSlowka(int kategoriaId, String jezyk) {
        List<String> slowka = new ArrayList<>();
        String sql = "SELECT " + jezyk + " FROM Slowka WHERE kategoria_id = ? ORDER BY " + jezyk;

        try (Connection conn = polaczZBaza();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, kategoriaId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                slowka.add(rs.getString(jezyk));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slowka;
    }

    public static List<Object[]> pobierzSlowkaZId(int kategoriaId) {
        List<Object[]> slowka = new ArrayList<>();
        String sql = "SELECT id, polski, hiszpanski FROM Slowka WHERE kategoria_id = ? ORDER BY id";

        try (Connection conn = polaczZBaza();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, kategoriaId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                slowka.add(new Object[]{
                    rs.getInt("id"),
                    rs.getString("polski"),
                    rs.getString("hiszpanski")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return slowka;
    }

    public static boolean dodajSlowko(int kategoriaId, String polski, String hiszpanski) {
        String sql = "INSERT INTO Slowka (polski, hiszpanski, kategoria_id) VALUES (?, ?, ?)";
        
        try (Connection conn = polaczZBaza();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, polski);
            pstmt.setString(2, hiszpanski);
            pstmt.setInt(3, kategoriaId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean aktualizujSlowko(int id, String polski, String hiszpanski) {
        String sql = "UPDATE Slowka SET polski = ?, hiszpanski = ? WHERE id = ?";
        
        try (Connection conn = polaczZBaza();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, polski);
            pstmt.setString(2, hiszpanski);
            pstmt.setInt(3, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean usunSlowko(int id) {
        String sql = "DELETE FROM Slowka WHERE id = ?";
        
        try (Connection conn = polaczZBaza();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean zapiszWynikiNauki(int kategoriaId, int liczbaSlowek, int poprawnychOdpowiedzi, int czasCwiczenia) {
        String sql = "INSERT INTO HistoriaNauki (kategoria_id, liczba_slowek, poprawnych_odpowiedzi, czas_cwiczenia, data) VALUES (?, ?, ?, ?, GETDATE())";
        
        try (Connection conn = polaczZBaza();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, kategoriaId);
            pstmt.setInt(2, liczbaSlowek);
            pstmt.setInt(3, poprawnychOdpowiedzi);
            pstmt.setInt(4, czasCwiczenia);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Object[]> pobierzStatystyki(int kategoriaId) {
        List<Object[]> statystyki = new ArrayList<>();
        String sql = "SELECT data, liczba_slowek, poprawnych_odpowiedzi, czas_cwiczenia FROM HistoriaNauki WHERE kategoria_id = ? ORDER BY data DESC";
        
        try (Connection conn = polaczZBaza();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, kategoriaId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                statystyki.add(new Object[]{
                    rs.getTimestamp("data"),
                    rs.getInt("liczba_slowek"),
                    rs.getInt("poprawnych_odpowiedzi"),
                    rs.getInt("czas_cwiczenia")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statystyki;
    }
}