package DBClasses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DefaultJavaClasses.UserSession;
import DefaultJavaClasses.Libro;

/**
 * Metodo per inserire i consigli, passa i nomi dei libri come stringhe, il primo libro consigliato
 * è obbligatorio, gli altri 2 sono opzionali e vengono settati a NULL nella tabella.
 */
public class DBConsigliManager {
    public static boolean inserisciCongili(String referenced, String libro1, String libro2, String libro3, Connection conn) {

        String query = "INSERT INTO ConsigliLIbri (id_utente, id_libro_referenced, id_libro_suggested1, " +
                "id_libro_suggested_2, id_libro_suggested3) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, UserSession.getUserID());
            stmt.setInt(2, DBgetIDLibro(referenced, conn));
            stmt.setInt(3, DBgetIDLibro(libro1, conn));
            if (libro2 != null) {
                stmt.setInt(4, DBgetIDLibro(libro2, conn));
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            if (libro3 != null) {
                stmt.setInt(5, DBgetIDLibro(libro3, conn));
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;


        } catch (SQLException e) {
        }
        return false;
    }

    private static int DBgetIDLibro(String titolo, Connection conn) {
        String query = "SELECT id_libro FROM Libri WHERE LOWER(titolo) LIKE LOWER(?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%"+titolo+"%");

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id_libro");
            } else {
                throw new SQLException("DefaultJavaClasses.Libro non trovato nel Dataset");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }


    }

    /**
     * Il metodo ritorna una lista di libri consigliati cercando il libro riferito
     * @param titolo
     * @param conn
     * @return
     */
    public List<Libro> DBReturnConsigli(String titolo, Connection conn) {
        List<Libro> toReturn = new ArrayList<>();
        String query = "SELECT l.titolo, l.autore FROM Libri l JOIN ConsigliLibri c ON" +
                "c.id_libro_referenced = l.id_libro WHERE l.titolo LIKE LOWER(?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + titolo.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String title = rs.getString("titolo");
                String auth = rs.getString("autore");
                Libro l = new Libro(title, auth);
                toReturn.add(l);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return toReturn;
    }
}
