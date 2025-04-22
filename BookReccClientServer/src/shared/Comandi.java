package shared;

/**
 * La classe mette a disposizione delle variabili con stringhe prefissate utilizzate mediante la comunicazione
 * tra client e clienthandler (per evitare errori di inconsistenza).
 */
public class Comandi {
    public static final String LOGIN = "LOGIN";
    public static final String REGISTER = "REGISTER";
    public static final String CHECK_CF = "CHECK_CF";
    public static final String CHECK_MAIL = "CHECK_MAIL";

    public static final String  SEARCH_BY_TITLE = "SEARCH_BY_TITLE";
    public static final String SEARCH_BY_AUTH = "SEARCH_BY_AUTH";
    public static final String SEARCH_BY_AUTH_YEAR  = "SEARH_BY_AUTH_YEAR";

    public static final String INSERISCI_CONSIGLI = "INSERISCI_CONSIGLI";
    public static final String RETURN_CONIGLI = "RETURN_CONSIGLI";
    public static final String GET_IDLIBRO = "GET_IDLIBRO";

    public static final String AGGIUNGI_LIBRO_LIB = "AGGIUNGI_LIBRO_IB";
    public static final String VISUALIZZA_LIBRERIA_UTENTE = "VISUALIZZA_LIBRERIA_UTENTE";
    public static final String CREA_IBRERIA = "CREA_IBRERIA";

    public static final String AGGIUNGI_VALUTAZIONE = "AGGIUNGI_VALUTAZIONE";



}
