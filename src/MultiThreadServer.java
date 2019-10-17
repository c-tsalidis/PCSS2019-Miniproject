import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MultiThreadServer {
    public static void main(String[] args) {
        // create a new thread to not stop the main thread from waiting and stopping the program for every connection
        new Thread(() -> {
            try {
                int clientNumber = 0;
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                System.out.println("MultiThreadServer started at: " + new Date());
                while (true) {
                    // Listen for a new connection request --> it doesn't execute the rest of the code until it accepts a new client connection
                    Socket socket = serverSocket.accept();
                    // There is a new client, so increment the clients counter
                    clientNumber++;
                    System.out.println("Starting thread for client " + clientNumber + " at " + new Date());
                    // Find the client's host name, and IP address
                    InetAddress inetAddress = socket.getInetAddress();
                    System.out.println("Client " + clientNumber + "'s host name is " + inetAddress.getHostName());
                    System.out.println("Client " + clientNumber + "'s IP Address is " + inetAddress.getHostAddress());
                    // Create and start a new thread for the connection
                    new Thread(new PlayerClientHandler(socket)).start();
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }).start();
    }
}