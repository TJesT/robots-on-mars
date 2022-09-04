import server.Server;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServerMain {
    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = new Server();
        boolean isLogging = server.isLogging();
        if (isLogging) {
            LogManager.getLogManager().readConfiguration(ServerMain.class.getResourceAsStream("resources/logging.properties"));
        }
        Thread serverThread = new Thread(server);
        serverThread.start();
        System.out.println("Server started");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("/stop")) {
                server.stop();
                break;
            }
        }
        serverThread.join();
        System.out.println("Server stopped");
    }
}
