package Model;

import BookScrabbleServer.BookScrabbleServer;
import Server.*;
import Server.Tile.Bag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class scrabbleModel {
    //data
    private BookScrabbleServer bookScrabbleServer;
    private Socket socket;
    private Board board;
    private Bag bag;
    private boolean stop;
    private List<String> dictionarybooks = Arrays.asList("file1.txt","file2.txt","file3.txt","file4.txt","file5.txt","file6.txt","file7.txt");
    private Map<String, Player> players = new HashMap<>();

    //functions
    public scrabbleModel() {
        this.bookScrabbleServer= new BookScrabbleServer();
        this.board = new Board();
        this.bag = Tile.Bag.getBag();
        this.stop = false;
    }


    public void initializeGame() {
        String hostName = "localhost";
        int portNumber = 6667;

            try {
                socket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in));
                String userInput;

                while ((userInput = stdIn.readLine()) != null) {
                    out.println(userInput);
                    System.out.println("response: " + in.readLine());
                }

            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

    public void finalizeGame() {
        stop=true;
        bookScrabbleServer.close();
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String BuildQueryFromWord(Word word, String queryID) {
        StringBuilder stringBuilder = new StringBuilder(queryID+",");
        for (String book: dictionarybooks){
            stringBuilder.append(book+",");
        }
        stringBuilder.append(word.getWord());
        return stringBuilder.toString();
    }

    public int TryAddWordToBoard(Word word) {
        // -1 -> indicates word is not legal
        // 0 -> indicates word can`t be placed on the board
        String query = BuildQueryFromWord(word, "Q");
        if (!queryServer(query))
            return -1;
        return board.tryPlaceWord(word);
    }

    private boolean queryServer(String query){
        String res="";
        try {
            PrintWriter out =
                    new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
            out.println(query);
            out.flush();
            res=in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (res.equals("true"));
    }

    public int CallengeServer(Word word) {
        String query = BuildQueryFromWord(word, "C");
        if(!queryServer(query))
            return -1;
        return board.tryPlaceWord(word)*2; //return double the score
    }

    //players functions
    private class Player {
        private String name;
        private Map<Character, Tile> playersTiles;
        private int score;

        public Player(String name){
            this.playersTiles=new HashMap<>();
            this.name=name;
            this.score=0;
            completePlayersTiles();
        }
        public void completePlayersTiles(){
            while(playersTiles.size()!=7){
                Tile t = bag.getRand();
                playersTiles.put(t.letter, t);
            }
        }
        public void removeTiles(String word){
            for (char ch : word.toCharArray()){
                playersTiles.remove(ch);
            }
            completePlayersTiles();
        }
    }

    private void updatePlayersTiles(String playername, Word word){
        players.get(playername).removeTiles(word.getWord());

    }
    public void addPlayer(String name){
        if(players.size()==4)
            return;
        Player player = new Player(name);
        players.put(name,player);
    }
}
