package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
    private final ClientHandler clientHandler;
    private ServerSocket serverSocket;
    private final int port;
    private Socket clientSocket;
    private volatile boolean stop;
    public ExecutorService threadPool;


    public MyServer(int port, ClientHandler clientHandler) {
        this.threadPool = Executors.newFixedThreadPool(4);
        this.clientHandler = clientHandler;
        this.stop = false;
        this.port = port;
        this.serverSocket=null;
        this.clientSocket=null;
    }

    private void runServer() {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(1000);
        } catch (IOException e) {
            // Need to throw here cause failed to start serverSocket as desired.
            //throw new RuntimeException(e);
        }

        while (!stop) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("connected");
                threadPool.execute(()->{
                    try {
                        this.clientHandler.handleClient(clientSocket.getInputStream(),
                                clientSocket.getOutputStream());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    cleanClientSocket();
                });
            } catch (IOException e) {
                // No need for, throw new RuntimeException(e);
            }
            catch (Exception e) {
                // No need for, throw new RuntimeException(e);
            }
        }
    }

    public void start() {
        new Thread(this::runServer).start();
    }

    private void cleanClientSocket(){
        clientHandler.close();
        if(clientSocket != null){
            if(!clientSocket.isClosed()) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("problem cleaning client socket");
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private void validate_close(){
        cleanClientSocket();
        if(!serverSocket.isClosed()){
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("problem validating servers close");
                throw new RuntimeException(e);
            }
        }
    }

    public void close() {
        try {
            cleanClientSocket();
            stop = true;
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("problem closing the server");
            throw new RuntimeException(e);
        }finally {
            validate_close();
        }
    }
}
