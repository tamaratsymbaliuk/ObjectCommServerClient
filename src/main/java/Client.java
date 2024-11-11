import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            Message message = new Message("Hello from Client");
            out.writeObject(message);

            Message response = (Message) in.readObject();
            System.out.println("received from server: " + response.getContent());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
