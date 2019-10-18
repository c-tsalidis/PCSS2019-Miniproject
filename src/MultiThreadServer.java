import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class MultiThreadServer {
    private int clientNumber = 0;
    private ArrayList<PlayerClientHandler> playerHandlers;
    private int minimumPlayers = 2;
    private boolean gameStarted;
    private boolean firstMoveMade;
    public void setFirstMoveMade (boolean _move) {
        this.firstMoveMade = _move;
    }

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
                    while (this.clientNumber == this.minimumPlayers) {
                        // System.out.println("Game has started because there are " + this.minimumPlayers + " minimum amount of players");
                        if (!gameStarted) {
                            for (int i = 0; i < this.playerHandlers.size(); i++) {
                                // the game is ready, and therefore it starts
                                this.playerHandlers.get(i).setGameReady(true);
                            }
                        }
                        UpdateTurns();
                    }
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }).start();
    }

    public void UpdateTurns() {
        if(!firstMoveMade) {
            // manage the turns of every player
            for (int i = 0; i < this.playerHandlers.size(); i++) {
                PlayerClientHandler player = this.playerHandlers.get(i);
                if (player.isMoveMade()) {
                    System.out.println(player.getPlayerName() + " has made his move");
                    if (i < (this.playerHandlers.size() - 1)) {
                        this.playerHandlers.get(i + 1).setTurn(true);
                        System.out.println("It's player " + this.playerHandlers.get(i + 1).getPlayerNumber() + "'s turn");
                    } else {
                        this.playerHandlers.get(0).setTurn(true);
                        System.out.println("It's player " + this.playerHandlers.get(0).getPlayerNumber() + "'s turn");
                    }
                    player.setTurn(false);
                    player.setMoveMade(false);
                } else {
                    // player.setTurn(false);
                    // player.setMoveMade(false);
                }
            }
        }
    }
}