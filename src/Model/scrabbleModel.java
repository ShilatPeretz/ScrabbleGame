package Model;

import Server.*;
import Server.Tile.Bag;
import common.Player;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class scrabbleModel extends Observable implements ClientHandler {
    //data
    private String bookScrabbleServerHostname = "localhost";
    private Board board;
    private Bag bag;
    private int serverPort = 6667;
    private int clientServerPort = 6668;
    private MyServer clientsServer;
    //"file1.txt", "file2.txt", "file3.txt", "file4.txt", "file5.txt", "file6.txt", "file7.txt"
    private List<String> dictionarybooks = Arrays.asList("file1.txt");
    public Map<String, Player> players = new HashMap<>();


    //functions
    public scrabbleModel() {
        this.clientsServer = new MyServer(serverPort, this);
        this.board = new Board();
        this.bag = Tile.Bag.getBag();
        clientsServer.start();
    }

    public void finalizeGame() {
        clientsServer.close();
    }

    private String BuildQueryFromWord(Word word, String queryID) {
        StringBuilder stringBuilder = new StringBuilder(queryID+",");
        for (String book: dictionarybooks){
            stringBuilder.append(book+",");
        }
        stringBuilder.append(word.getWord());
        return stringBuilder.toString();
    }


    public void TryAddWordToBoard(Word word, String playerName) {
        // -1 -> indicates word is not legal
        // 0 -> indicates word can`t be placed on the board
        String query = BuildQueryFromWord(word, "Q");
        if (!queryServer(query))
            return;
        System.out.println("got to here - only need to place word on board and get score");
        int score = board.tryPlaceWord(word);
        players.get(playerName).updateScore(score);
        updatePlayersTiles(playerName, word);
        setChanged();
        notifyObservers();
    }

    private boolean queryServer(String query) {
        String result = "";
        try {
            Socket bookScrabbleServerSocket = new Socket(bookScrabbleServerHostname, clientServerPort);
            PrintWriter BookScrabbleOut = new PrintWriter(bookScrabbleServerSocket.getOutputStream(), true);
            BufferedReader BookScrabbleIn = new BufferedReader(new InputStreamReader(bookScrabbleServerSocket.getInputStream()));
            BookScrabbleOut.println(query);
            result = BookScrabbleIn.readLine();
            BookScrabbleOut.close();
            BookScrabbleIn.close();
            bookScrabbleServerSocket.close();
        } catch (IOException e) {
            System.out.println("exception thrown while quering the server");
            throw new RuntimeException(e);
        }
        return (result.equals("true"));
    }

    public void CallengeServer(Word word, String playerName) {
        // -1 -> indicates word is not legal
        // 0 -> indicates word can`t be placed on the board
        String query = BuildQueryFromWord(word, "C");
        if(!queryServer(query))
            return;
        int score = board.tryPlaceWord(word)*2;
        players.get(playerName).updateScore(score);
        updatePlayersTiles(playerName, word);
        setChanged();
        notifyObservers();
    }

    //players functions
    private void updatePlayersTiles(String playername, Word word){
        players.get(playername).removeTiles(word.getWord(), bag);
    }


    public void addPlayer(String name){
        if(players.size()==4)
            return;
        Player player = new Player(name, bag);
        players.put(name,player);
        setChanged();
        notifyObservers();
    }


    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        PrintWriter ClientOut = new PrintWriter(outToClient, true);
        BufferedReader ClientIn = new BufferedReader(new InputStreamReader(inFromclient));
        String userInput;

        try {
            Socket bookScrabbleServerSocket = new Socket(bookScrabbleServerHostname, clientServerPort);
            PrintWriter BookScrabbleOut = new PrintWriter(bookScrabbleServerSocket.getOutputStream(), true);
            BufferedReader BookScrabbleIn = new BufferedReader(new InputStreamReader(bookScrabbleServerSocket.getInputStream()));
            while ((userInput = ClientIn.readLine()) != null) {
                System.out.println(userInput);
                BookScrabbleOut.println(userInput);
                ClientOut.println(BookScrabbleIn.readLine());
            }
        } catch (IOException e) {
            System.out.println("exception thrown while trying to handle client");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close(){}
}
