package server;

import shared.*;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import shared.ServiziLibriSearch;

public class ClientHandler implements Runnable {
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
                            handleInserisciConsigli();
                            break;
                        case Comandi.GET_IDLIBRO:
                            handleGetIdLibro();
                            break;
                        case Comandi.CREA_IBRERIA:
                            handleCreaLib();
                            break;
                        case Comandi.VISUALIZZA_LIBRERIA_UTENTE:
                            handleVisualizzaLibUser();
                            break;
                        case Comandi.AGGIUNGI_LIBRO_LIB:
                            handleAggiungiLibroLib();
                            break;
                        case Comandi.AGGIUNGI_VALUTAZIONE:
                            handleAggiungiValutazione();
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

            boolean register = accountService.inserimentoAccountDB(nome, cognome, codFiscale, mail, pass);
            out.writeObject(register);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleLogin() {
        try {
            String mail = (String) in.readObject();
            String pass = (String) in.readObject();

            boolean login = accountService.login(mail, pass);
            out.writeObject(login);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleSearchByTitle() {
        try {
            String titolo = (String) in.readObject();

            List<Libro> libri = libriSearchService.searchByTitle(titolo);
            out.writeObject(libri);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleSearchByAuth() {
        try {
            String autore = (String) in.readObject();

            List<Libro> libri = libriSearchService.searchByAuth(autore);
            out.writeObject(libri);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleSearchByAuthYear() {
        try {
            String autore = (String) in.readObject();
            int anno = (int) in.readObject();

            List<Libro> risultati = libriSearchService.searchByAuthYear(autore, anno);
            out.writeObject(risultati);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleInserisciConsigli() {
        try {
            String referenced = (String) in.readObject();
            String libro1 = (String) in.readObject();
            String libro2 = (String) in.readObject();
            String libro3 = (String) in.readObject();

            boolean consigliInseriti = consigliService.DBinserisciCongili(referenced, libro1, libro2, libro3);
            out.writeObject(consigliInseriti);

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleReturnConsigli() {
        try {
            String titolo = (String) in.readObject();

            List<Libro> toReturn = consigliService.DBReturnConsigli(titolo);
            out.writeObject(toReturn);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleGetIdLibro() {
        try {
            String titolo = (String) in.readObject();
            int idLibro = consigliService.DBGetIDLibro(titolo);
            out.writeObject(idLibro);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleCreaLib() {
        try {
            String nome = (String) in.readObject();

            boolean libreriaCreata = librerieService.creaLibreria(nome);
            out.writeObject(libreriaCreata);

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleVisualizzaLibUser() {
        try {
            String mailUtente = (String) in.readObject();

            List<Libreria> librerieUtente = librerieService.visuaizzaLibrerieUtente(mailUtente);
            out.writeObject(librerieUtente);

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleAggiungiLibroLib() {
        try {
            int idLibro = (int) in.readObject();
            int idLibreria = (int) in.readObject();

            boolean aggiuntoLibroLibreria = librerieService.aggiungiLibro(idLibro, idLibreria);
            out.writeObject(aggiuntoLibroLibreria);

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
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

            boolean aggiuntaValutazione = valutazioniService.aggiungiValutazione(idLibro, votoStile, votoContenuto, votoGradevolezza,
                    votoOriginalita, votoEdizione, noteStile, noteContenuto, noteGradevolezza, noteOriginalita, noteEdizione);
            out.writeObject(aggiuntaValutazione);

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }


}
