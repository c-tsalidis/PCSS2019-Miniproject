import java.io.*;
import java.net.Socket;

// Define the thread class for handling new connection
class PlayerClientHandler implements Runnable {
    private Socket socket; // A connected socket
    // Construct a thread
    public PlayerClientHandler(Socket socket) {
        this.socket = socket;
    }
    // Run a thread
    public void run() {
        try {
            // Create data input and output streams
            DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
            DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
            boolean connect = true;
            // Continuously serve the client
            while (connect) {
                // Receive name of the player from the PlayerClient
                String playerName = inputFromClient.readUTF();
                String welcomeMessage = "Welcome to the server " + playerName + "!";
                // Send results back to the client
                outputToClient.writeUTF(welcomeMessage);
                System.out.println("Welcome message sent to " + playerName);
                boolean c = inputFromClient.readBoolean();
                connect = c;
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}