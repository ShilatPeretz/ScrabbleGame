package Client;

import Server.MyServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ExampleClient {
    String ModelServerHostname = "localhost";
    int ModelServerPort = 6668;
    Socket ModelServerSocket;

    public void run() throws IOException {
        ModelServerSocket = new Socket(ModelServerHostname, ModelServerPort);
        PrintWriter out = new PrintWriter(ModelServerSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(ModelServerSocket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("Received: " + in.readLine());
        }
    }
}
