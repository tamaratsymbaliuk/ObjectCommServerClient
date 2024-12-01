import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserController {
    private static final String CREDENTIALS_FILE = "credentials.txt";
    private static Map<String, String> users = new HashMap<>();

    static {
        loadCredentials();
    }

    // Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (users.containsKey(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists!");
        } else {
            users.put(user.getUsername(), user.getPassword());
            saveCredentials(user.getUsername(), user.getPassword());
            return ResponseEntity.ok("Registration successful! You can now log in.");
        }
    }
    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        if (users.containsKey(user.getUsername())) {
            if (users.get(user.getUsername()).equals(user.getPassword())) {
                return ResponseEntity.ok("Login successful!");
            } else {
                return ResponseEntity.badRequest().body("Invalid password!");
            }
        } else {
            return ResponseEntity.badRequest().body("Username not found!");
        }
    }

    // Endpoint for message exchange
    @PostMapping("/message")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        // Here, you can handle message logic
        System.out.println("Received from client: " + message);
        return ResponseEntity.ok("Hello from Server");
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
