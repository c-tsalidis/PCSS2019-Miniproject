import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PlayerClient_3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String name = "";
        int playerNumber = 0;
        boolean connect = true;
        boolean playerInfoSet = false;
        boolean gameReady = false;
        boolean isPlayersTurn = false;
        String gameState = null;
        try {
            // Create a socket to connect to the server
            Socket connectToServer = new Socket("localhost", 8000);
            // Create an input stream to receive data from the server
            DataInputStream inputFromServer = new DataInputStream(connectToServer.getInputStream());
            // Create an output stream to send data to the server
            DataOutputStream outputToServer = new DataOutputStream(connectToServer.getOutputStream());
            while (connect) {
                if(!playerInfoSet) {
                    // get the player number from the server
                    playerNumber = inputFromServer.readInt();
                    // Enter the player's name
                    System.out.print("Enter your name " + playerNumber + ": ");
                    name = input.nextLine();

                    // Send the player name to the server
                    outputToServer.writeUTF(name);
                    outputToServer.flush();

                    // get the message from the server welcoming the player
                    String serverMessage = inputFromServer.readUTF();
                    System.out.println(serverMessage);
                    playerInfoSet = true;
                }

                // read the input that the game is ready to start
                gameReady = inputFromServer.readBoolean();
                if(gameReady) {
                    isPlayersTurn = inputFromServer.readBoolean();
                    if(isPlayersTurn) {
                        // ask the user to write his move
                        System.out.println("it's your turn " + name + " Write a message to your buddies");
                        String move = input.nextLine();
                        // tell the server that the move has been made
                        outputToServer.writeUTF(move);
                        outputToServer.flush();

                        isPlayersTurn = false;
                    }
                }

                // send the connect boolean to the client player handler
                outputToServer.writeBoolean(connect);
                // flush the output to the server data stream
                outputToServer.flush();
            }
            input.close();
            connectToServer.close();
        } catch (IOException ex) {
            System.out.println(ex.toString() + '\n');
        }
    }
}


