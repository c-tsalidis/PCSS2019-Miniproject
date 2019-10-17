import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class MultiThreadServer {
    private int clientNumber = 0;
    private ArrayList<PlayerClientHandler> playerHandlers;
    public int GetClientNumber() {
        return clientNumber;
    }

    public void CreateServer() {
        // create a new thread to not stop the main thread from waiting and stopping the program for every connection
        new Thread( () -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                System.out.println("MultiThreadServer started at: " + new Date());
                while (true) {
                    // Listen for a new connection request --> it doesn't execute the rest of the code until it accepts a new client connection
                    Socket socket = serverSocket.accept();
                    // There is a new client, so increment the clients counter
                    this.clientNumber++;
                    System.out.println("Starting thread for client " + this.clientNumber + " at " + new Date());
                    // Find the client's host name, and IP address
                    InetAddress inetAddress = socket.getInetAddress();
                    System.out.println("Client " + this.clientNumber + "'s host name is " + inetAddress.getHostName());
                    System.out.println("Client " + this.clientNumber + "'s IP Address is " + inetAddress.getHostAddress());
                    // create a new PlayerClientHandler
                    PlayerClientHandler playerClientHandler = new PlayerClientHandler(socket, clientNumber);
                    // add it to the playersHandler list
                    // playerHandlers.add(playerClientHandler);
                    // Create and start a new thread for the connection
                    new Thread(playerClientHandler).start();
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }).start();
    }
}