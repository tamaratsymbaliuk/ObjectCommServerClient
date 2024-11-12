import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            Scanner scanner = new Scanner(System.in);

            // Authentication Process
            System.out.println("Do you want to log in or register?");
            System.out.println("1. Login");
            System.out.println("2. Register");
            String choice = scanner.nextLine();

            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            // Determine whether to send login or registration request
            String authType = "1".equals(choice) ? "login" : "register";

            // Send authentication message to server
            out.writeObject(new Message(username, password, true, authType));

            // Read server's response
            Message serverResponse = (Message) in.readObject();
            System.out.println("Server: " + serverResponse.getContent());

            // After authentication, proceed to send a message
            if (serverResponse.getContent().contains("successful")) {
                System.out.println("Enter a message to send to the server:");
                String message = scanner.nextLine();
                out.writeObject(new Message(message));

                Message response = (Message) in.readObject();
                System.out.println("Received from server: " + response.getContent());
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}