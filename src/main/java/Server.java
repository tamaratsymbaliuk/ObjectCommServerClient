import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final String CREDENTIALS_FILE = "credentials.txt";
    private static Map<String, String> users = new HashMap<>();

    public static void main(String[] args) {
        int port = 1234;

        // Load existing users from file
        loadCredentials();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                try (Socket socket = serverSocket.accept();
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                    Message receivedMessage = (Message) in.readObject();

                    if (receivedMessage.isAuthentication()) {
                        handleAuthentication(receivedMessage, out);
                    } else {
                        System.out.println("Received from client: " + receivedMessage.getContent());
                        Message response = new Message("Hello from Server");
                        out.writeObject(response);
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Authentication handling: login or registration
    private static void handleAuthentication(Message receivedMessage, ObjectOutputStream out) throws IOException {
        String username = receivedMessage.getUsername();
        String password = receivedMessage.getPassword();
        String authType = receivedMessage.getAuthType();

        if ("login".equals(authType)) {
            // Handle login
            if (users.containsKey(username)) {
                if (users.get(username).equals(password)) {
                    out.writeObject(new Message("Login successful!"));
                } else {
                    out.writeObject(new Message("Invalid password!"));
                }
            } else {
                out.writeObject(new Message("Username not found!"));
            }
        } else if ("register".equals(authType)) {
            // Handle registration
            if (users.containsKey(username)) {
                out.writeObject(new Message("Username already exists!"));
            } else {
                users.put(username, password);
                saveCredentials(username, password);
                out.writeObject(new Message("Registration successful! You can now log in."));
            }
        }
    }

    // Load credentials from file
    private static void loadCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                users.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.out.println("No credentials file found, starting with an empty user list.");
        }
    }

    // Save new credentials to the file
    private static void saveCredentials(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}