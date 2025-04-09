package DefaultJavaClasses;

public class Valutazione {
    Libro libro;
    int stile;
    int contenuto;
    int gradevolezza;
    int originalita;
    int edizione;
    int votoFinale;
    static final int MIN = 1;
    static final int MAX = 5;

    public Valutazione(Libro libro, int stile, int contenuto, int gradevolezza, int originalita, int edizione) {
        this.libro = libro;
        setStile(stile);
        setContenuto(contenuto);
        setGradevolezza(gradevolezza);
        setOriginalita(originalita);
        setEdizione(edizione);
        votoFinale = (stile+contenuto+gradevolezza+originalita+edizione) / 5;
    }

    public Libro getLibro() {
        return libro;
    }

    public int getStile() {
        return stile;
    }

    public int getContenuto() {
        return contenuto;
    }

    public int getGradevolezza() {
        return gradevolezza;
    }

    public int getOriginalita() {
        return originalita;
    }

    public int getEdizione() {
        return edizione;
    }

    public int getVotoFinale() {
        return votoFinale;
    }

    public void setStile(int stile){
        if(checkValid(stile)){
            this.stile = stile;
        } else throw new IllegalArgumentException("Valore errato");

    }

    public void setContenuto(int contenuto){
        if(checkValid(contenuto)){
            this.contenuto = contenuto;
        } else throw new IllegalArgumentException("Valore errato");

    }

    public void setGradevolezza(int gradevolezza){
        if(checkValid(gradevolezza)){
            this.gradevolezza = gradevolezza;
        } else throw new IllegalArgumentException("Valore errato");

    }

    public void setOriginalita(int originalita){
        if(checkValid(originalita)){
            this.originalita = originalita;
        } else throw new IllegalArgumentException("Valore errato");

    }

    public void setEdizione(int edizione){
        if(checkValid(edizione)){
            this.edizione = edizione;
        } else throw new IllegalArgumentException("Valore errato");

    }

    public void setVotoFinale(int votoFinale){
        if(checkValid(votoFinale)){
            this.votoFinale = votoFinale;
        } else throw new IllegalArgumentException("Valore errato");

    }

    public boolean checkValid(int value){
        return value > 1 && value <5;
    }

}
