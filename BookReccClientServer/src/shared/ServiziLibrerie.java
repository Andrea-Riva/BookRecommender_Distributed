package shared;

import java.util.List;

public interface ServiziLibrerie {
    public  boolean creaLibreria(String nome);

    public  boolean aggiungiLibro(int idLibro, int idLibreria);

    public  List<Libreria> visuaizzaLibrerieUtente(String mailUtente);


}
