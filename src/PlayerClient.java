import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class PlayerClient {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean connect = true;
        try {
            // Create a socket to connect to the server
            Socket connectToServer = new Socket("localhost", 8000);
            // Create an input stream to receive data from the server
            DataInputStream inputFromServer = new DataInputStream(connectToServer.getInputStream());
            // Create an output stream to send data to the server
            DataOutputStream outputToServer = new DataOutputStream(connectToServer.getOutputStream());
            while (connect) {
                // Enter the player's name
                System.out.println("Enter your name:");
                String name = input.nextLine();
                // Send the player name to the server
                outputToServer.writeUTF(name);
                // flush the output to the server data stream
                // outputToServer.flush();
                System.out.println("Your name " + name + " has been sent to the server");
                String serverMessage = inputFromServer.readUTF();
                System.out.println(serverMessage);
                // Exit or continue with a new set of values
                System.out.print("Type yes to continue or no to stop: ");
                input.reset();
                if (input.next().equals("no")) {
                    connect = false;
                }
                else connect = true;
                // send the connect boolean to the client player handler
                outputToServer.writeBoolean(connect);
            }
            input.close();
            connectToServer.close();
        }
        catch (IOException ex) {
            System.out.println(ex.toString() + '\n');
        }
    }
}


