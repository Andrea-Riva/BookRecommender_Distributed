package shared;

public class Libreria {
    String nomeLibreria;
    int idLibreria;
    int idUtente;

    public Libreria(String nomeLibreria, int idLibreria, int idUtente){
        this.nomeLibreria = nomeLibreria;
        this.idLibreria = idLibreria;
        this.idUtente = idUtente;
    }

    public String getNomeLibreria() {
        return nomeLibreria;
    }

    public int getIdLibreria() {
        return idLibreria;
    }

    public int getIdUtente() {
        return idUtente;
    }
}
