import java.io.*;
import java.net.Socket;

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
    /*
    // has the first move been made?
    private boolean firstMoveMade;
    public boolean isFirstMoveMade() {
        return firstMoveMade;
    }
    public void setFirstMoveMade(boolean firstMoveMade) {
        this.firstMoveMade = firstMoveMade;
    }

     */
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
                if(!playerInfoSet) {
                    // send the player number to the client
                    outputToClient.writeInt(this.playerNumber);

                    // Receive name of the player from the PlayerClient
                    playerName = inputFromClient.readUTF();

                    String welcomeMessage = "Welcome to the server " + playerName + "! \n " +
                            "You are player number " + this.playerNumber + " \n " +
                            "Your info is now correctly set up in the server" + " \n " +
                            "Please wait while other players connect - Thanks for your patience!";

                    // Send results back to the client
                    outputToClient.writeUTF(welcomeMessage);
                    System.out.println("Welcome message sent to " + playerName + " --> Player number " + this.playerNumber);
                    playerInfoSet = true;
                }
                if(this.gameReady) {
                    // tell the client that the game is ready
                    outputToClient.writeBoolean(true);
                }
                else {
                    // tell the client that the game is not ready yet
                    outputToClient.writeBoolean(false);
                }
                while(this.gameReady) {
                    /*
                    if(!this.firstMoveMade) {

                        if(this.playerNumber == 1) {
                            this.isTurn = true;
                            this.firstMoveMade = true;
                        }
                        else {
                            this.isTurn = false;
                        }
                     }
                     */
                    if(this.isTurn) {
                        // tell the player that it's his turn to play
                        outputToClient.writeBoolean(true);
                        // read if the player has done his move yet
                        this.moveMade = inputFromClient.readBoolean();
                    }
                    else {
                        // tell the user that it's not his turn to play
                        outputToClient.writeBoolean((false));
                    }
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