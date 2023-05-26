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
    public boolean query(String query){
        String res="";
        try {
            Socket server=new Socket("localhost",gamePort); /**??????*/
            PrintWriter out=new PrintWriter(server.getOutputStream());
            Scanner in=new Scanner(server.getInputStream());
            out.println(query);
            out.flush();
            res=in.next();
            in.close();
            out.close();
            server.close();
        } catch (IOException e) {
            System.out.println("IOException thrown");
        }
        return (res.equals("true")?true:false);
    }

    public void run(){
        this.gameServer.start();
    }
    public void close(){
        this.gameServer.close();
    }
}
