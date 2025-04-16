package client;

import shared.Comandi;
import shared.Libro;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

    }
    /**
     * Inizializzazione dei canali di comunicazione in e out, scanner e socket.
     */
    private Socket socket = new Socket("localhost", 5000);
    private ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
    private ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
    private Scanner scanner;

    public Client() throws IOException {
    }


    private void searchByTitolo(){
        try{
            String titolo = ""; //da casella di testo
            //gestione cose varie
            out.writeObject(Comandi.SEARCH_BY_TITLE);
            out.writeObject(titolo);

            List<Libro> resultSet = (List<Libro>) in.readObject();
            //stampa o cose varie

        }catch (IOException | ClassNotFoundException e){

        }

    }



}
