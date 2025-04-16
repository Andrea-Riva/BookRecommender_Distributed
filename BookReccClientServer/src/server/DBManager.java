package server;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {
    public DBManager() {
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(DBUtils.getUrl(), DBUtils.getUser(), DBUtils.getPassword());
            System.out.println("Connessione al DataBase effettuata con successo...");
            return conn;


        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Errore: impossibile connettersi al DataBase...");
    }
}
