import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class PlayerClient {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int playerNumber = 0;
        boolean connect = true;
        boolean playerInfoSet = false;
        boolean gameStarted = false;
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
                    System.out.print("Enter your name player " + playerNumber + ": ");
                    String name = input.nextLine();

                    // Send the player name to the server
                    outputToServer.writeUTF(name);

                    String serverMessage = inputFromServer.readUTF();
                    System.out.println(serverMessage);

                    playerInfoSet = true;
                    outputToServer.writeBoolean(playerInfoSet);
                }
                System.out.println(inputFromServer.readUTF());
                /*
                gameStarted = inputFromServer.readBoolean();
                // if the game has started
                if (gameStarted) {
                    System.out.println("The game has started!!");
                }
                else {
                    System.out.println("The game has not started yet");
                }
                 */

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


