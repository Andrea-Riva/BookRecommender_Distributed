package shared;

import java.io.Serializable;
import java.util.List;

/**
 * Classe wrapper per poter tornare un errore nel caso in cui un utente non loggato tenti
 * di eseguire un metodo per utente loggato.
 */
public class RispostaLogged implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean esito;
    private String messaggio;

    public RispostaLogged(boolean errore){
        this.esito = esito;
        this.messaggio = null;
    }

    public RispostaLogged(String messaggio){
        this.esito = false;
                this.messaggio = messaggio;
    }

    public boolean getEsito() {
        return esito;
    }

    public String getMessaggio() {
        return messaggio;
    }
}
