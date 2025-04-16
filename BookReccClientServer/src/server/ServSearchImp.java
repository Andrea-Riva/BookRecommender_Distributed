package server;

import shared.Libro;
import shared.ServiziLibriSearch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServSearchImp implements ServiziLibriSearch {
    private Connection connection;

    public ServSearchImp() {
        connection = DBManager.getConnection();
    }

    /**
     * Metodo per la ricerca di libri tramite titolo.
     *
     * @param titolo
     * @return List<DefaultJavaClasses.Libro> libriReturn
     * @throws SQLException
     */
    @Override
    public List<Libro> searchByTitle(String titolo) {
        List<Libro> libriReturn = new ArrayList<>();
        String query = "SELECT * FROM Libri WHERE LOWER(titolo) LIKE LOWER(?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + titolo + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String titoloLibro = rs.getString("titolo");
                String autore = rs.getString("autore");
                String categoria = rs.getString("categoria");
                String editore = rs.getString("editore");
                int annoPubblicazione = rs.getInt("anno_pubblicazione");

                Libro libro = new Libro(titoloLibro, autore, categoria, editore, annoPubblicazione);
                libriReturn.add(libro);
            }
            return libriReturn;
        } catch (SQLException e) {
            System.err.println("Errore nella ricerca");
            e.printStackTrace();
        }
        return libriReturn;
    }

    /**
     * Metodo per la ricerca di libri tramite autore.
     *
     * @param auth
     * @return List<DefaultJavaClasses.Libro> libriReturn
     * @throws SQLException
     */
    @Override
    public List<Libro> searchByAuth(String auth) {
        List<Libro> libriReturn = new ArrayList<>();
        String query = "SELECT * FROM Libri WHERE LOWER(autore) LIKE LOWER(?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + auth + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String titoloLibro = rs.getString("titolo");
                String autore = rs.getString("autore");
                String categoria = rs.getString("categoria");
                String editore = rs.getString("editore");
                int annoPubblicazione = rs.getInt("anno_pubblicazione");

                Libro libro = new Libro(titoloLibro, autore, categoria, editore, annoPubblicazione);
                libriReturn.add(libro);
            }
            return libriReturn;
        } catch (SQLException e) {
            System.err.println("Errore nella ricerca");
            e.printStackTrace();
        }
        return libriReturn;
    }

    /**
     *
     * Metodo per cercare libri tramite nome autore e anno di pubblicazione
     * @param auth
     * @param anno
     * @return List<DefaultJavaClasses.Libro> libriReturn
     * @throws SQLException
     */
    @Override
    public List<Libro> searchByAuthYear(String auth, int anno) {
        List<Libro> libriReturn = new ArrayList<>();
        String query = "SELECT * FROM Libri WHERE LOWER(autore) LIKE LOWER(?) AND anno_pubblicazione = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + auth + "%");
            stmt.setInt(2,anno);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String titoloLibro = rs.getString("titolo");
                String autore = rs.getString("autore");
                String categoria = rs.getString("categoria");
                String editore = rs.getString("editore");
                int annoPubblicazione = rs.getInt("anno_pubblicazione");

                Libro libro = new Libro(titoloLibro, autore, categoria, editore, annoPubblicazione);
                libriReturn.add(libro);
            }
            return libriReturn;
        } catch (SQLException e) {
            System.err.println("Errore nella ricerca");
            e.printStackTrace();
        }return libriReturn;
    }
}
