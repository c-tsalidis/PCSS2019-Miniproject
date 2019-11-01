import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

// Define the thread class for handling new connection
class PlayerClientHandler implements Runnable {
    private Socket socket; // A connected socket

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    private String playerName = "Anonymous";
    private boolean isTurn; // is it the player's turn?
    public boolean isTurn() {
        return isTurn;
    }
    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    private int playerNumber;
    private boolean gameReady;
    public void setGameReady(boolean _gameReady) {
        this.gameReady = _gameReady;
    }
    public boolean getGameReady () {
        return this.gameReady;
    }
    private boolean moveMade;
    public boolean isMoveMade() {
        return moveMade;
    }
    public void setMoveMade(boolean moveMade) {
        this.moveMade = moveMade;
    }

    // Construct a thread
    public PlayerClientHandler(Socket socket, int playerNumber) {
        this.socket = socket;
        this.playerNumber = playerNumber;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    private String gameState;

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
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!playerInfoSet) {
                    // send the player number to the client
                    outputToClient.writeInt(this.playerNumber);
                    outputToClient.flush();

                    // Receive name of the player from the PlayerClient
                    playerName = inputFromClient.readUTF();

                    String welcomeMessage = "Welcome to the server " + playerName + "! \n " +
                            "You are number " + this.playerNumber + " \n " +
                            "Your info is now correctly set up in the server" + " \n " +
                            "Please wait while your other buddies connect - Thanks for your patience!";

                    // Send results back to the client
                    outputToClient.writeUTF(welcomeMessage);
                    outputToClient.flush();
                    System.out.println("Welcome message sent to " + playerName + " --> Player number " + this.playerNumber);
                    playerInfoSet = true;

                }
                // tell the client whether or not the game is ready
                outputToClient.writeBoolean(this.gameReady);
                outputToClient.flush();
                if(this.gameReady) {
                    // tell the player whether or not it's his turn to play
                    outputToClient.writeBoolean(this.isTurn);
                    outputToClient.flush();
                    if(this.isTurn) {
                        String move = inputFromClient.readUTF();
                        System.out.println(move);
                        this.moveMade = true;
                        this.isTurn = false;
                        outputToClient.flush();
                    }
                    if(this.gameState != null) outputToClient.writeUTF(this.gameState);
                    outputToClient.flush();
                }
                connect = inputFromClient.readBoolean();
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}