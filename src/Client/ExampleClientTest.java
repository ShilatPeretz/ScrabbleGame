package Client;

import java.io.IOException;

public class ExampleClientTest {

    public static void main(String[] args) {
        ExampleClient exampleClient = new ExampleClient();
        try {
            exampleClient.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
