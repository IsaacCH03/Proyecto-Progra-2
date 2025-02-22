// LaunchServer.java
package business;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LaunchServer {
    private final int PORT = 5000;
    private ServerSocket serverSocket;
    private final List<Socket> activeClients = new ArrayList<>();

    public LaunchServer() {
        startServer();
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado en el puerto: " + PORT);

            while (true) {
                System.out.println("Esperando cliente...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                synchronized (activeClients) {
                    activeClients.add(clientSocket);
                }

                new Thread(new ClientHandler(clientSocket, this)).start();
            }
        } catch (IOException e) {
            System.out.println("Error iniciando el servidor: " + e.getMessage());
        }
    }

    public void removeClient(Socket clientSocket) {
        synchronized (activeClients) {
            activeClients.remove(clientSocket);
        }
        System.out.println("Cliente eliminado. Conexiones activas: " + activeClients.size());
    }
    




}
