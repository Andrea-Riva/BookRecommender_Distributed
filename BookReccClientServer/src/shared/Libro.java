package shared;

import java.io.Serializable;

public class Libro implements Serializable {
    public String titolo;
    public String autore;
    public String categoria;
    public String editore;
    public int annoPubblicazione;

    public Libro(String tit,String aut, String cat, String ed, int annoPubb){
        this.titolo = tit;
        this.autore = aut;
        this.categoria = cat;
        this.editore = ed;
        this.annoPubblicazione = annoPubb;
    }

    /**
     * Costruttore creato per il return dei consigli libro.
     * @param titolo
     * @param autore
     */
    public Libro(String titolo, String autore){
        this.titolo = titolo;
        this.autore = autore;
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


