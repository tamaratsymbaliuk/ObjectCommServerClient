import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String content;
    private String username;
    private String password;
    private boolean isAuthentication;
    private String authType;  // "login" or "register"

    // Constructor for normal messages
    public Message(String content) {
        this.content = content;
        this.isAuthentication = false;
    }
    // Constructor for authentication messages (login/register)
    public Message(String username, String password, boolean isAuthentication, String authType) {
        this.username = username;
        this.password = password;
        this.isAuthentication = isAuthentication;
        this.authType = authType;
    }

    public String getContent() {
        return content;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public boolean isAuthentication() {
        return isAuthentication;
    }
    public String getAuthType() {
        return authType;
    }
}
