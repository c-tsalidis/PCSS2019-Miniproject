import java.io.*;
import java.net.Socket;

// Define the thread class for handling new connection
class PlayerClientHandler implements Runnable {
    private Socket socket; // A connected socket
    private boolean isTurn; // is it the player's turn?
    private int playerNumber;
    // Construct a thread
    public PlayerClientHandler(Socket socket, int playerNumber) {
        this.socket = socket;
        this.playerNumber = playerNumber;
    }
    // Run a thread
    public void run() {
        try {
            // Create data input and output streams
            DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
            DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
            boolean connect = true;
            boolean gameStarted = false;
            boolean playerInfoSet = false;
            // Continuously serve the client
            while (connect) {
                // send the player number to the client
                outputToClient.writeInt(this.playerNumber);

                // Receive name of the player from the PlayerClient
                String playerName = inputFromClient.readUTF();

                String welcomeMessage = "Welcome to the server " + playerName + "! \n " +
                        "You are player number " + this.playerNumber + " \n " +
                        "Please wait while other players connect";

                // Send results back to the client
                outputToClient.writeUTF(welcomeMessage);
                System.out.println("Welcome message sent to " + playerName + " --> Player number " + this.playerNumber);

                playerInfoSet = inputFromClient.readBoolean();
                String infoSetMessage = "Your info is now correctly set up in the server " + playerName + " - Thanks for your patience!";
                outputToClient.writeUTF(infoSetMessage);
                while(playerInfoSet) {

                }
                /*
                if(gameStarted) {
                    // tell the client that the game has started
                    outputToClient.writeBoolean(true);
                }
                else {
                    outputToClient.writeBoolean(false);
                }
                 */
                // to check if the connection is still active
                connect = inputFromClient.readBoolean();
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}