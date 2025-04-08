package GenericJavaClasses;

public class Libro {
    private String titolo;
    private String autore;
    private String categoria;
    private String editore;
    private int annoPubblicazione;

    public Libro(String tit,String aut, String cat, String ed, int annoPubb){
        this.titolo = tit;
        this.autore = aut;
        this.categoria = cat;
        this.editore = ed;
        this.annoPubblicazione = annoPubb;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getAutore() {
        return autore;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getEditore() {
        return editore;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }
}
