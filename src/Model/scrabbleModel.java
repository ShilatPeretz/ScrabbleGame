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

public class scrabbleModel {
    //data
    private BookScrabbleServer bookScrabbleServer;
    private Socket socket;
    private Board board;

    //functions
    public scrabbleModel() {
        /*******************/
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

    ;

    public void finalizeGame() {
        bookScrabbleServer.close();
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String BuildQueryFromWord(Word word, String queryID) {
        StringBuilder stringBuilder = new StringBuilder(queryID + ",s1.txt,s2.txt,");
        stringBuilder.append(word.getWord());
        return stringBuilder.toString();
    }

    public int TryAddWordToBoard(Word word) {
        // -1 -> indicates word is not legal
        // 0 -> indicates word can`t be placed on the board
        String query = BuildQueryFromWord(word, "Q");
        if(!bookScrabbleServer.query(query))
            return -1;
        return board.tryPlaceWord(word);
    }

    public int CallengeServer(Word word) {
        String query = BuildQueryFromWord(word, "C");
        if(!bookScrabbleServer.query(query))
            return -1;
        return board.tryPlaceWord(word)*2; //return double the score
    }
    //add players


    //additional classes
    private class Player {
        private String name;
        private Bag bag;
        private int score;
    }

    ;
}
