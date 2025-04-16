package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import shared.ServiziValutazioni;
import shared.UserIDError;
import shared.UserSession;

public class ServValutazioniImp  implements ServiziValutazioni {

    private  Connection connection;

    public ServValutazioniImp() {
        connection = DBManager.getConnection();
    }

    /**
     * Metodo per aggiungere una valutazione al libro, verifica che l'id utente sia > 0.
     * La verifica dei valori (1...5) non è stata gestita poiché viene effettuata dal DB.
     * @param idLibro
     * @param votoStile
     * @param votoContenuto
     * @param votoGradevolezza
     * @param votoOriginalita
     * @param votoEdizione
     * @exception SQLException e
     * @exception UserIDError e
     */
    public  boolean aggiungiValutazione(int idLibro, int votoStile, int votoContenuto, int votoGradevolezza,
                                              int votoOriginalita, int votoEdizione, String noteStile,
                                              String noteContenuto, String noteGradevolezza, String noteOriginalita,
                                              String noteEdizione) {
        if(UserSession.getUserID()<0){
            try {
                throw new UserIDError("errore");
            }catch (UserIDError e){
                System.out.println(e.getMessage());
            }
            return false;
        };
        String query = "INSERT INTO ValutazioniLibri (id_utente, id_libro, voto_stile, voto_contenuto, voto_gradevolezza, voto_originalità, voto_edizione, voto_complessivo)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, UserSession.getUserID());
            stmt.setInt(2,idLibro);
            stmt.setInt(3,votoStile);
            stmt.setInt(4,votoContenuto);
            stmt.setInt(5,votoGradevolezza);
            stmt.setInt(6,votoOriginalita);
            stmt.setInt(7,votoEdizione);
            stmt.setString(8, noteStile != null ? noteStile : null);
            stmt.setString(9, noteContenuto != null ? noteContenuto : null);
            stmt.setString(10, noteGradevolezza != null ? noteGradevolezza : null);
            stmt.setString(11, noteOriginalita != null ? noteOriginalita : null);
            stmt.setString(12, noteEdizione != null ? noteEdizione : null);
            stmt.setInt(13,((votoStile+votoContenuto+votoGradevolezza+votoOriginalita+votoEdizione)/5));

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Inserimento effetuato");
                return true;
            } else{
                System.out.println("Inserimento non riuscito");
                return false;
            }

        }catch (SQLException e){
            System.out.println("Inserimento non riuscito "+e.getMessage());
            e.printStackTrace();
        } return  false;
    }
}
