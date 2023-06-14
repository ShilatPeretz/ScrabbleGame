package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RemoteClient {
    String ModelServerHostname = "localhost";
    int ModelServerPort = 6668;
    Socket ModelServerSocket;

    public void run(String query) throws IOException {
        try {
            ModelServerSocket = new Socket(ModelServerHostname, ModelServerPort);
            PrintWriter out = new PrintWriter(ModelServerSocket.getOutputStream(), true);
            Scanner in=new Scanner(ModelServerSocket.getInputStream());
            out.println(query);
            out.flush();
            String res=in.next();
            in.close();
            out.close();
            ModelServerSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}