package shared;

import java.sql.Connection;

public interface ServiziValutazioni {
    public  boolean aggiungiValutazione(int userId, int idLibro, int votoStile, int votoContenuto, int votoGradevolezza,
                                              int votoOriginalita, int votoEdizione, String noteStile,
                                              String noteContenuto, String noteGradevolezza, String noteOriginalita,
                                              String noteEdizione);


}
