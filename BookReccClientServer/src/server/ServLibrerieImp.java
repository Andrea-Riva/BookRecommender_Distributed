package server;
import shared.ServiziLibrerie;
import shared.Libreria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServLibrerieImp implements ServiziLibrerie{
    private  Connection connection;

    public ServLibrerieImp() {
        connection = DBManager.getConnection();
    }

    /**
     * Metodo per la creazione di una DefaultJavaClasses.Libreria, la tabella libreria è composta da una colonna
     * nome e id_utente a cui appartiene, l'id_utente viene recuperato automaticamente dalla
     * sessione corrente a cui ha effettuato il login.
     *
     * @param nome
     * @throws SQLException e
     */
    public  boolean creaLibreria(String nome, int userId) {
        String query = "INSERT INTO Librerie (nome, id_utente) VALUES (?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nome);
            stmt.setInt(2, userId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("DefaultJavaClasses.Libreria creata con successo");
                return true;
            } else {
                System.out.println("Errore durante la creazione");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Errore durante la creazione della libreria");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Metodo per l'aggiunta di un libro nella tabella Libri_Librerie.
     *
     * @param idLibro
     * @param idLibreria
     * @throws SQLException e
     */
    public  boolean aggiungiLibro(int idLibro, int idLibreria) {
        String query = "INSERT INTO Libri_Librerie VALUES ?,?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idLibro);
            stmt.setInt(2, idLibreria);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("DefaultJavaClasses.Libro aggiunto alla libreria con id " + idLibreria);
                return true;
            } else {
                System.out.println("Errore durante l'aggiunta");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'aggiunta del libro");
            e.printStackTrace();
        }
        return false;

    }

    /**
     * Permette la visualizzazione delle librerie creati dagli utenti, restitituisce
     * una lista di oggetti di tipo DefaultJavaClasses.Libreria.
     *
     * @param mailUtente
     * @exception SQLException e
     */
    public  List<Libreria> visuaizzaLibrerieUtente(String mailUtente) {
        List<Libreria> librerieToReturn = new ArrayList<>();
        String query = "SELECT l.nome, l.id_libreria, l.id_utente FROM Librerie l " +
                "JOIN UtentiRegistrati r ON l.id_utente = r.id_utente WHERE r.mail = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, mailUtente);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idLibreria = rs.getInt("id_libreria");
                int idUtente = rs.getInt("id_utente");
                String nomeLibreria = rs.getString("nome");

                Libreria libreria = new Libreria(nomeLibreria, idLibreria, idUtente);
                librerieToReturn.add(libreria);
            }

        } catch (SQLException e) {
            System.out.println("Errore durante il recupero delle Librerie "+e.getMessage());
            e.printStackTrace();
        }
        return librerieToReturn;

    }
}
