import client.Client;
import view.Viewer;

public class ClientMain {
    public static void main(String[] args) {
        Client client = new Client("192.168.0.108", 12345);
        client.addObserver(new Viewer(client));
        client.run();
    }
}
