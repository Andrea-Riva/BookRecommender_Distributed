package server;

import org.mindrot.jbcrypt.BCrypt;
import shared.ServiziAccount;
import shared.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServAccountImp implements ServiziAccount {

    private Connection connection;

    public ServAccountImp() {
        connection = DBManager.getConnection();
    }

    /**
     * Metodo per inserire un account nel DB, se il codice fiscale o la mail
     * è già stata usata, l'inserimento non viene effettuato.
     *
     * @param nome
     * @param cognome
     * @param codiceFiscale
     * @param mail
     * @param password
     * @throws SQLException
     */
    @Override
    public boolean inserimentoAccountDB(String nome, String cognome,
                                        String codiceFiscale, String mail,
                                        String password) {
        String query = "INSERT INTO UtentiRegistrati(nome, cognome, codice_fiscale, mail, crypted_pass) VALUES (?,?,?,?,?)";
        String hashedPass = BCrypt.hashpw(password, BCrypt.gensalt());
        try {
            if (isCfUsed(codiceFiscale)) {
                System.out.println("Errore, codice fiscale già in uso");
                return false;
            }
            if (isMailUsed(mail)) {
                System.out.println("Errore, mail già in uso");
                return false;
            }

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nome);
            stmt.setString(2, cognome);
            stmt.setString(3, codiceFiscale);
            stmt.setString(4, mail);
            stmt.setString(5, hashedPass);

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                System.out.println("Inserimento effettuato con successo");
                return true;
            } else System.out.println("Errore durante l'inserimento");
            return false;
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento dell'account nel DataBase: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Metodo per effettuare il login, imposta l'id utente, nome e cognome, mail e codice fiscale
     * nella variabile statica della classe DefaultJavaClasses.UserSession.
     *
     * @param mail
     * @param password
     * @throws SQLException
     */
    @Override
    public Utente login(String mail, String password) throws SQLException {
        try {
            if (!isMailUsed(mail)) {
                return null;
            }
            String query = "SELECT password, id_utente, nome, cognome, codice_fiscale, mail " +
                    "FROM UtentiRegistrati WHERE mail = ?";

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, mail);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idUtente = rs.getInt("id_utente");
                String nomeUtente = rs.getString("nome");
                String cognomeUtente = rs.getString("cognome");
                String hashedPassword = rs.getString("password");
                String email = rs.getNString("mail");
                String codiceFiscale = rs.getString("codice_fiscale");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    System.out.println("Login eseguito con successo");
                    return new Utente(idUtente, nomeUtente, cognomeUtente, email, codiceFiscale);
                } else System.out.println("Login non riuscito");

            }
        } catch (SQLException e) {
            System.err.println("Errore durante il login: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sistema per verificare la presenza di un codice fiscale nel DataBase
     *
     * @param cf
     * @return
     * @throws SQLException
     */
    @Override
    public boolean isCfUsed(String cf) throws SQLException {
        String query = "SELECT COUNT(*) FROM UtentiRegistrati WHERE codice_fiscale = ?";

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, cf);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }


    /**
     * Sistema per verificare la presenza di una mail nel DataBase
     *
     * @param mail
     * @return
     * @throws SQLException
     */
    @Override
    public boolean isMailUsed(String mail) throws SQLException {
        String query = "SELECT COUNT(*) FROM UtentiRegistrati WEHERE mail = ?";

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, mail);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

}
