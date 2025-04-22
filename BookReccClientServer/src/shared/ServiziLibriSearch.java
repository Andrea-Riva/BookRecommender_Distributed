package shared;

import java.util.List;

public interface ServiziLibriSearch {
    public List<Libro> searchByTitle(String titolo);

    public List<Libro> searchByAuth(String autore);

    public List<Libro> searchByAuthYear(String autore, int anno);
}
