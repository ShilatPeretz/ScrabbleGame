package Server;


import java.io.*;
import java.util.Arrays;

public class BookScrabbleHandler implements ClientHandler{
    PrintWriter OutToClient;
    BufferedReader InFromClient;
    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        InFromClient=new BufferedReader(new InputStreamReader(inFromclient));
        DictionaryManager dictionaryManager=DictionaryManager.get();
        OutToClient=new PrintWriter(outToClient);
        String[] books = new String[0];
        try {
            books = InFromClient.readLine().split(",");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean result = false;
        if(books[0].equals("Q")){ //Q for query
            result=dictionaryManager.query(Arrays.copyOfRange(books,1,books.length));
        }
        else if(books[0].equals("C")){ //C for challenge
            result=dictionaryManager.challenge(Arrays.copyOfRange(books,1, books.length));
        }
        OutToClient.println(""+result);
        OutToClient.flush();
    }

    @Override
    public void close() {
        try {
            InFromClient.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutToClient.close();
    }
}
