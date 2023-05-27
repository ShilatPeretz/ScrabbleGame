package Model;

import Server.*;
import Server.Tile.Bag;
import common.Player;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class scrabbleModel implements ClientHandler {
    //data
    private String bookScrabbleServerHostname = "localhost";
    private Socket bookScrabbleServerSocket;
    private Board board;
    private Bag bag;
    private int serverPort = 6667;
    private int clientServerPort = 6668;
    private MyServer clientsServer;
    private List<String> dictionarybooks = Arrays.asList("file1.txt", "file2.txt", "file3.txt", "file4.txt", "file5.txt", "file6.txt", "file7.txt");
    private Map<String, Player> players = new HashMap<>();


    //functions
    public scrabbleModel() {
        this.clientsServer = new MyServer(serverPort, this);
        this.board = new Board();
        this.bag = Tile.Bag.getBag();
    }


    public void initializeGame() {
        clientsServer.start();
        try {
            bookScrabbleServerSocket = new Socket(bookScrabbleServerHostname, clientServerPort);
        } catch (IOException e) {
            System.out.println("exception thrown while trying to initialize the game ");
            throw new RuntimeException(e);
        }
    }

    public void finalizeGame() {
        try {
            bookScrabbleServerSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public int TryAddWordToBoard(Word word, String playerName) {
        // -1 -> indicates word is not legal
        // 0 -> indicates word can`t be placed on the board
        String query = BuildQueryFromWord(word, "Q");
        if (!queryServer(query))
            return -1;
        System.out.println("got to here - only need to place word on board and get score");
        int score = board.tryPlaceWord(word);
        players.get(playerName).updateScore(score);
        updatePlayersTiles(playerName, word);
        return score;
    }

    private boolean queryServer(String query) {
        //System.out.println(query);
        String result = "";
        try {
            PrintWriter BookScrabbleOut = new PrintWriter(bookScrabbleServerSocket.getOutputStream(), true);
            BufferedReader BookScrabbleIn = new BufferedReader(new InputStreamReader(bookScrabbleServerSocket.getInputStream()));
            BookScrabbleOut.println(query);
            result = BookScrabbleIn.readLine();
        } catch (IOException e) {
            System.out.println("exception thrown while quering the server");
            throw new RuntimeException(e);
        }
        return (result.equals("true"));
    }

    public int CallengeServer(Word word, String playerName) {
        // -1 -> indicates word is not legal
        // 0 -> indicates word can`t be placed on the board
        String query = BuildQueryFromWord(word, "C");
        if(!queryServer(query))
            return -1;
        int score = board.tryPlaceWord(word)*2;
        players.get(playerName).updateScore(score);
        updatePlayersTiles(playerName, word);
        return score; //return double the score
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
    }

    public int getNumberOfPlayers(){
        return players.size();
    }

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        PrintWriter ClientOut = new PrintWriter(outToClient, true);
        BufferedReader ClientIn = new BufferedReader(new InputStreamReader(inFromclient));
        String userInput;

        try {
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
