import client.Client;
import view.Viewer;

public class ClientMain {
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 12345);
        client.addObserver(new Viewer(client));
        client.run();
    }
}
