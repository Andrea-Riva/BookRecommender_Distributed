package server;

import shared.Libro;
import shared.ServiziConsigli;
import shared.UserSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServConsigliImp implements ServiziConsigli {
    private Connection connection;

    public ServConsigliImp() {
        connection = DBManager.getConnection();
    }

    public  boolean DBinserisciCongili(String referenced, String libro1, String libro2, String libro3) {

        String query = "INSERT INTO ConsigliLIbri (id_utente, id_libro_referenced, id_libro_suggested1, " +
                "id_libro_suggested_2, id_libro_suggested3) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, UserSession.getUserID());
            stmt.setInt(2, DBGetIDLibro(referenced));
            stmt.setInt(3, DBGetIDLibro(libro1));
            if (libro2 != null) {
                stmt.setInt(4, DBGetIDLibro(libro2));
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            if (libro3 != null) {
                stmt.setInt(5, DBGetIDLibro(libro3));
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;


        } catch (SQLException e) {
        }
        return false;
    }

    public  int  DBGetIDLibro(String titolo) {
        String query = "SELECT id_libro FROM Libri WHERE LOWER(titolo) LIKE LOWER(?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
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
     * @return
     */
    public List<Libro> DBReturnConsigli(String titolo) {
        List<Libro> toReturn = new ArrayList<>();
        String query = "SELECT l.titolo, l.autore FROM Libri l JOIN ConsigliLibri c ON" +
                "c.id_libro_referenced = l.id_libro WHERE l.titolo LIKE LOWER(?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
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
