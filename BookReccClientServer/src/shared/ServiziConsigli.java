package shared;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ServiziConsigli {

      boolean DBinserisciCongili(String referenced, String libro1, String libro2, String libro3);

     int DBGetIDLibro(String titolo);

     List<Libro> DBReturnConsigli(String titolo);

}
