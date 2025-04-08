import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBSearchingTools {

    /**
     * Metodo per la ricerca di libri tramite titolo.
     *
     * @param titolo
     * @param conn
     * @return List<Libro> libriReturn
     * @throws SQLException
     */
    public List<Libro> searchByTitolo(String titolo, Connection conn) throws SQLException {
        List<Libro> libriReturn = new ArrayList<>();
        String query = "SELECT * FROM Libri WHERE titolo LIKE ?";

        PreparedStatement stmt = conn.prepareStatement(query);
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
    }

    /**
     * Metodo per la ricerca di libri tramite autore.
     *
     * @param auth
     * @param conn
     * @return List<Libro> libriReturn
     * @throws SQLException
     */
    public List<Libro> searchByAutore(String auth, Connection conn) throws SQLException {
        List<Libro> libriReturn = new ArrayList<>();
        String query = "SELECT * FROM Libri WHERE autore LIKE ?";

        PreparedStatement stmt = conn.prepareStatement(query);
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
    }

    /**
     *
     * Metodo per cercare libri tramite nome autore e anno di pubblicazione
     * @param auth
     * @param anno
     * @param conn
     * @return List<Libro> libriReturn
     * @throws SQLException
     */

    public List<Libro> searchByAutAndAnno(String auth, int anno, Connection conn) throws SQLException {
        List<Libro> libriReturn = new ArrayList<>();
        String query = "SELECT * FROM Libri WHERE autore LIKE ? AND anno_pubblicazione = ?";

        PreparedStatement stmt = conn.prepareStatement(query);
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
    }
}

