package BookScrabbleServer;

import Server.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class BookScrabbleServer {
    private final MyServer gameServer;
    private int gamePort;
    //functions
    public BookScrabbleServer(){
        /** return 4 digits number so that other players can connect to the game*/
        Random r=new Random();
        this.gamePort=6667;
        this.gameServer = new MyServer(gamePort, new BookScrabbleHandler());
    };

    public void run(){
        this.gameServer.start();
    }
    public void close(){
        this.gameServer.close();
    }
}
