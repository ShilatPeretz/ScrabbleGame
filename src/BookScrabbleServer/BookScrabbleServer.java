package BookScrabbleServer;

import Server.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class BookScrabbleServer {
    private final MyServer gameServer;

    public BookScrabbleServer(){
        this.gameServer = new MyServer(6668, new BookScrabbleHandler());
    };

    public void start(){
        this.gameServer.start();
    }
    public void close(){
        this.gameServer.close();
    }
}
