import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestClient {
    private static final String BASE_URL = "http://localhost:8080/api";
    public static void main(String[] args) {
        try {
            // Example: Registration
            String registerPayload = "{\"username\":\"testuser\", \"password\":\"testpass\"}";
            sendPostRequest("http://localhost:8080/api/register", registerPayload);

            // Example: Login
            String loginPayload = "{\"username\":\"testuser\", \"password\":\"testpass\"}";
            sendPostRequest("http://localhost:8080/api/login", loginPayload);

            // Example: Send Message
            String messagePayload = "\"Hello Server\"";
            sendPostRequest("http://localhost:8080/api/message", messagePayload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendPostRequest(String targetURL, String payload) throws Exception {
        URL url = new URL(targetURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = connection.getOutputStream()) {
            os.write(payload.getBytes());
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
    }
}
