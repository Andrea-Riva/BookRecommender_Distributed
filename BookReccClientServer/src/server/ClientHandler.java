package server;

import shared.*;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import shared.ServiziLibriSearch;

public class ClientHandler implements Runnable {
    private int userID;
    private String userName;
    private String userSurname;
    private String userCf;
    private String userMail;
    private boolean isAuthenticated = false;
    /**
     * Inizializzazione del socket e Stream
     */
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    /**
     * creo delle istanze delle classi (che implementano le interfacce per le funzionalità),
     * questo permette l'uso dei metodi (non statici)
     */
    private ServiziAccount accountService;
    private ServiziConsigli consigliService;
    private ServiziLibrerie librerieService;
    private ServiziLibriSearch libriSearchService;
    private ServiziValutazioni valutazioniService;

    static final String ERRORE = "Login necessario per eseguire il metodo.";

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.accountService = new ServAccountImp();
        this.consigliService = new ServConsigliImp();
        this.librerieService = new ServLibrerieImp();
        this.libriSearchService = new ServSearchImp();
        this.valutazioniService = new ServValutazioniImp();

    }

    /**
     * Il metodo run() esegue il thread, in base alla stringa passata esegue un metodo (per le funzionalità).
     * Le stringhe sono salvate nella classe Comandi del package shared (per evitare hardCoding).
     */
    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Object ricevuto = in.readObject();

                if (ricevuto instanceof String comando) {
                    switch (comando) {
                        case Comandi.REGISTER:
                            handleRegister();
                            break;
                        case Comandi.LOGIN:
                            handleLogin();
                            break;
                        case Comandi.SEARCH_BY_TITLE:
                            handleSearchByTitle();
                            break;
                        case Comandi.SEARCH_BY_AUTH:
                            handleSearchByAuth();
                            break;
                        case Comandi.SEARCH_BY_AUTH_YEAR:
                            handleSearchByAuthYear();
                            break;
                        case Comandi.RETURN_CONIGLI:
                            handleReturnConsigli();
                            break;
                        case Comandi.INSERISCI_CONSIGLI:
                            RispostaLogged risposta = null;
                            if (isAuthenticated) {
                                handleInserisciConsigli();
                            } else {
                                risposta = new RispostaLogged(ERRORE);
                                out.writeObject(risposta);
                            }
                            break;
                        case Comandi.GET_IDLIBRO:
                            handleGetIdLibro();
                            break;
                        case Comandi.CREA_IBRERIA:
                            if (isAuthenticated) {
                                handleCreaLib();
                            } else {
                                risposta = new RispostaLogged(ERRORE);
                                out.writeObject(risposta);
                            }
                            break;
                        case Comandi.VISUALIZZA_LIBRERIA_UTENTE:
                            handleVisualizzaLibUser();
                            break;
                        case Comandi.AGGIUNGI_LIBRO_LIB:
                            if (isAuthenticated) {
                                handleAggiungiLibroLib();
                            } else {
                                risposta = new RispostaLogged(ERRORE);
                                out.writeObject(risposta);
                            }
                            break;
                        case Comandi.AGGIUNGI_VALUTAZIONE:
                            if (isAuthenticated) {
                                handleAggiungiValutazione();
                            } else{
                                risposta = new RispostaLogged(ERRORE);
                            out.writeObject(risposta);
                            }
                            break;
                    }

                }

            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Metodi dello switch per le funzionalità del programma.
     * Creo le variabili da passare come argomento dei metodi (le variabili sono castate da Object).
     * Creo la variabile per il return del metodo e si stampo sul canale out.
     */
    private void handleRegister() {
        try {
            String nome = (String) in.readObject();
            String cognome = (String) in.readObject();
            String codFiscale = (String) in.readObject();
            String mail = (String) in.readObject();
            String pass = (String) in.readObject();

            boolean registered = accountService.inserimentoAccountDB(nome, cognome, codFiscale, mail, pass);
            out.writeObject(registered);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.err.println("Errore nel metodo handleRegister: " + e.getMessage());
            e.printStackTrace();
            try {
                out.writeObject(false);
            } catch (IOException ioException) {
                System.err.println("Errore durante l'invio dell'eccezione al client: " + ioException.getMessage());
                ioException.printStackTrace();
            }
        }

    }

    private void handleLogin() {
        try {
            String mail = (String) in.readObject();
            String pass = (String) in.readObject();

            Utente utente = accountService.login(mail, pass);
            this.userID = utente.getId();
            this.userName = utente.getNome();
            this.userSurname = utente.getCognome();
            this.userCf = utente.getCodiceFiscale();
            this.userMail = utente.getEmail();

            out.writeObject(true);
            this.isAuthenticated = true;

        } catch (ClassNotFoundException | IOException | SQLException e) {
            System.err.println("Errore nel metodo handleLogin: " + e.getMessage());
            e.printStackTrace();
            try {
                out.writeObject(false);
            } catch (IOException ioException) {
                System.err.println("Errore durante l'invio dell'eccezione al client: " + ioException.getMessage());
                ioException.printStackTrace();
            }
        }

    }

    private void handleSearchByTitle() {
        try {
            String titolo = (String) in.readObject();

            List<Libro> libri = libriSearchService.searchByTitle(titolo);
            out.writeObject(libri);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore nel metodo handleSearchByTitle: " + e.getMessage());
            e.printStackTrace();
            try {
                out.writeObject(Collections.emptyList());
            } catch (IOException ioException) {
                System.err.println("Errore durante l'invio dell'eccezione al client: " + ioException.getMessage());
                ioException.printStackTrace();
            }
        }
    }

    private void handleSearchByAuth() {
        try {
            String autore = (String) in.readObject();

            List<Libro> libri = libriSearchService.searchByAuth(autore);
            out.writeObject(libri);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            try {
                out.writeObject(Collections.emptyList());
            } catch (IOException ioException) {
                System.err.println("Errore nel medodo handleSearchByAtuh: " + e.getMessage());
                e.printStackTrace();

            }
        }

    }

    private void handleSearchByAuthYear() {
        try {
            String autore = (String) in.readObject();
            int anno = (int) in.readObject();

            List<Libro> risultati = libriSearchService.searchByAuthYear(autore, anno);
            out.writeObject(risultati);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            try {
                out.writeObject(Collections.emptyList());
            } catch (IOException ioException) {
                System.err.println("Errore nel metodo handleSearchByAuthYear: " + e.getMessage());
                e.printStackTrace();
                try {
                    out.writeObject(Collections.emptyList());
                } catch (IOException ioException1) {
                    System.err.println("Errore durante l'invio dell'eccezione al client: " + ioException.getMessage());
                    ioException.printStackTrace();
                }
            }
        }

    }

    private void handleInserisciConsigli() {
        try {
            String referenced = (String) in.readObject();
            String libro1 = (String) in.readObject();
            String libro2 = (String) in.readObject();
            String libro3 = (String) in.readObject();
            int id = this.userID;

            boolean consigliInseriti = consigliService.DBinserisciCongili(id, referenced, libro1, libro2, libro3);
            RispostaLogged esito;
            if(consigliInseriti) {
                esito = new RispostaLogged(true);
            } else {
                esito = new RispostaLogged("Errore durante l'inserimento dei consigli");
            }
            out.writeObject(esito);

        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Errore nel metodo handleInserisciCOnsigli: " + e.getMessage());
            e.printStackTrace();
            try {
                RispostaLogged errore = new RispostaLogged("Errore nel metodo di handling per inserimento consigli");
                out.writeObject(errore);
            } catch (IOException ioException) {
                System.err.println("Errore durante l'invio dell'eccezione al client: " + ioException.getMessage());
                ioException.printStackTrace();
            }
        }
    }

    private void handleReturnConsigli() {
        try {
            String titolo = (String) in.readObject();

            List<Libro> toReturn = consigliService.DBReturnConsigli(titolo);
            out.writeObject(toReturn);
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Errore nel metodo handleReturnConsigli: " + e.getMessage());
            e.printStackTrace();
            try {
                out.writeObject(Collections.emptyList());
            } catch (IOException ioException) {
                System.err.println("Errore durante l'invio dell'eccezione al client: " + ioException.getMessage());
                ioException.printStackTrace();
            }
        }
    }

    private void handleGetIdLibro() {
        try {
            String titolo = (String) in.readObject();
            int idLibro = consigliService.DBGetIDLibro(titolo);
            out.writeObject(idLibro);
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Errore nel metodo handleGetIdLibro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleCreaLib() {
        try {
            String nome = (String) in.readObject();
            int userId = userID;

            boolean libreriaCreata = librerieService.creaLibreria(nome, userId);
            RispostaLogged esito;
            if(libreriaCreata){
                esito = new RispostaLogged(true);
            } else{
                esito = new RispostaLogged("Errore durante la creazione della libreria (handler)");
            }
            out.writeObject(esito);

        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Errore nel metodo handleCreaLib: " + e.getMessage());
            e.printStackTrace();
            try {
                RispostaLogged errore = new RispostaLogged("Errore nel metodo per la creazione di librerie (handler)");
                out.writeObject(errore);
            } catch (IOException ioException) {
                System.err.println("Errore durante l'invio dell'eccezione al client: " + ioException.getMessage());
                ioException.printStackTrace();
            }
        }
    }

    private void handleVisualizzaLibUser() {
        try {
            String mailUtente = (String) in.readObject();

            List<Libreria> librerieUtente = librerieService.visuaizzaLibrerieUtente(mailUtente);
            out.writeObject(librerieUtente);

        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Errore nel metodo handleVisualizzaLibUser: " + e.getMessage());
            e.printStackTrace();
            try {
                out.writeObject(Collections.emptyList());
            } catch (IOException ioException) {
                System.err.println("Errore durante l'invio dell'eccezione al client: " + ioException.getMessage());
                ioException.printStackTrace();
            }
        }
    }

    private void handleAggiungiLibroLib() {
        try {
            int idLibro = (int) in.readObject();
            int idLibreria = (int) in.readObject();

            boolean aggiuntoLibroLibreria = librerieService.aggiungiLibro(idLibro, idLibreria);
            RispostaLogged esito;
            if(aggiuntoLibroLibreria){
                esito = new RispostaLogged(true);
            } else {
                esito = new RispostaLogged("Errore durante l'aggiunta di un libro alla libreria (Handler)");
            }
            out.writeObject(esito);

        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Errore nel metodo handleAggiungiLibroLib: " + e.getMessage());
            e.printStackTrace();
            try {
                RispostaLogged errore = new RispostaLogged("Errore nel metodo di aggiunta alla libreria (Handler)");
                out.writeObject(errore);
            } catch (IOException ioException) {
                System.err.println("Errore durante l'invio dell'eccezione al client: " + ioException.getMessage());
                ioException.printStackTrace();
            }
        }
    }

    private void handleAggiungiValutazione() {
        try {
            int idLibro = (int) in.readObject();
            int votoStile = (int) in.readObject();
            int votoContenuto = (int) in.readObject();
            int votoGradevolezza = (int) in.readObject();
            int votoOriginalita = (int) in.readObject();
            int votoEdizione = (int) in.readObject();
            String noteStile = (String) in.readObject();
            String noteContenuto = (String) in.readObject();
            String noteGradevolezza = (String) in.readObject();
            String noteOriginalita = (String) in.readObject();
            String noteEdizione = (String) in.readObject();
            int idUser = this.userID;

            boolean aggiuntaValutazione = valutazioniService.aggiungiValutazione(idUser, idLibro, votoStile, votoContenuto, votoGradevolezza,
                    votoOriginalita, votoEdizione, noteStile, noteContenuto, noteGradevolezza, noteOriginalita, noteEdizione);
            RispostaLogged esito;
            if(aggiuntaValutazione){
                esito = new RispostaLogged(true);
            } else {
                esito = new RispostaLogged("Errore durante l'inserimento della valutazione (Handler)");
            }
            out.writeObject(esito);

        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Errore nel metodo handleAggiungiValutazione: " + e.getMessage());
            e.printStackTrace();
            try {
                RispostaLogged errore = new RispostaLogged("Errore nel metodo di inserimento valutazione (Handler");
                out.writeObject(errore);
            } catch (IOException ioException) {
                System.err.println("Errore durante l'invio dell'eccezione al client: " + ioException.getMessage());
                ioException.printStackTrace();
            }
        }
    }


}
