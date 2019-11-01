import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class MultiThreadServer {
    private int clientNumber = 0;
    private ArrayList<PlayerClientHandler> playerHandlers;
    private int minimumPlayers = 3;
    private boolean gameStarted;
    private boolean firstMoveMade = false;
    private boolean isMinimumPlayers = false;

    public int GetClientNumber() {
        return this.clientNumber;
    }

    public void CreateServer() {
        playerHandlers = new ArrayList<PlayerClientHandler>();
        // create a new thread to not stop the main thread from waiting and stopping the program for every connection
        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                System.out.println("MultiThreadServer started at: " + new Date());
                while (true) {
                    // Listen for a new connection request --> it doesn't execute the rest of the code until it accepts a new client connection
                    Socket socket = serverSocket.accept();
                    // There is a new client, so increment the clients counter
                    this.clientNumber++;
                    // set up a new thread for the new client (socket)
                    this.SetupThreadForClient(socket);
                    // check if there minimum amount of players are connected, and if so, start the game
                    if(!this.isMinimumPlayers) {
                        if (this.clientNumber == this.minimumPlayers) {
                            this.isMinimumPlayers = true;
                        }
                    }
                    // if there are the minimum amount of players connected, then play the game
                    while (this.isMinimumPlayers) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        this.SetGameAsReady();
                        this.UpdateTurns();
                    }
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }).start();
    }

    private void SetGameAsReady() {
        if (!this.gameStarted) {
            for (int i = 0; i < this.playerHandlers.size(); i++) {
                // the game is ready, and therefore it starts
                this.playerHandlers.get(i).setGameReady(true);
            }
        }
    }

    private void SetupThreadForClient(Socket socket) {
        System.out.println("Starting thread for client " + this.clientNumber + " at " + new Date());
        // Find the client's host name, and IP address
        InetAddress inetAddress = socket.getInetAddress();
        System.out.println("Client " + this.clientNumber + "'s host name is " + inetAddress.getHostName());
        System.out.println("Client " + this.clientNumber + "'s IP Address is " + inetAddress.getHostAddress());
        // create a new PlayerClientHandler
        PlayerClientHandler playerClientHandler = new PlayerClientHandler(socket, this.clientNumber);
        // Create and start a new thread for the connection
        new Thread(playerClientHandler).start();
        // add it to the playersHandler list
        playerHandlers.add(playerClientHandler);
    }

    private void UpdateTurns() {
        int playerTurn = 0;
        // if the first move has already been made, then update the turns.
        if(this.firstMoveMade) {
            // manage the turns of every player
            for (int i = 0; i < this.playerHandlers.size(); i++) {
                PlayerClientHandler player = this.playerHandlers.get(i);
                if (player.isMoveMade()) {
                    this.UpdateGameState(player.getPlayerName() + " has made their move");
                    if (i < (this.playerHandlers.size() - 1)) {
                        playerTurn = i + 1;
                    } else {

                        playerTurn = 0;
                    }
                    this.UpdateGameState("It's " + this.playerHandlers.get(playerTurn).getPlayerName() + "'s turn");
                    this.playerHandlers.get(playerTurn).setTurn(true);
                    player.setTurn(false);
                    player.setMoveMade(false);
                }

            }
        }
        // if the first move has not been made, then set the turn to the first player
        else {
            // if(this.playerHandlers.get(0).isFirstMoveMade()) this.firstMoveMade = false;
            if(this.playerHandlers.get(0).isMoveMade()) {
                this.firstMoveMade = true;
                playerTurn = 1;
                this.playerHandlers.get(playerTurn).setTurn(true);
                this.playerHandlers.get(0).setTurn(false);
            }
            else {
                playerTurn = 0;
                this.playerHandlers.get(playerTurn).setTurn(true);

            }
        }
    }
    
    public void UpdateGameState(String gameState) {
        System.out.println("Updated game state - " + gameState);
        /*
        for (int i = 0; i < playerHandlers.size(); i++) {
            PlayerClientHandler player = playerHandlers.get(i);
            if(gameState != null) player.setGameState(gameState);
        }*/
    }
}