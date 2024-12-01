# ObjectCommServerClient Project

This project implements a client-server communication system that supports two approaches:

1. **Socket-Based Communication**  
2. **RESTful API Communication**

This dual-approach architecture allows the application to support both the original socket-based communication and the newer RESTful architecture, offering flexibility for various use cases and future scalability.

---

## Approach 1: Socket-Based Communication

### Overview

The original implementation uses Java's `Socket` and `ServerSocket` classes to facilitate communication between the client and the server. The server handles:

- **User Authentication**: Login and registration based on a credentials file.
- **Message Exchange**: Sending and receiving messages after successful authentication.

### Files

- **`Client.java`**: Connects to the server using a socket and exchanges messages.
- **`Server.java`**: Handles client connections, processes authentication, and responds to messages.

### Communication Workflow

1. The client establishes a socket connection with the server.
2. The client sends a `Message` object (containing user credentials or a message).
3. The server processes the `Message` and responds accordingly.
4. The client reads and displays the server's response.

## Approach 2: RESTful API Communication

### Overview

A newer implementation leverages a RESTful API architecture, built using Spring Boot, to manage user authentication and messaging. This approach provides a scalable and widely used standard for web communication.

### Files

- **RestClient.java**: Sends HTTP requests to interact with the REST API endpoints.
- **UserController.java**: Manages REST endpoints for user authentication and messaging.
- **User.java**: Represents a user entity for REST communication.

### REST Endpoints

| Endpoint        | Method | Description                        |
|-----------------|--------|------------------------------------|
| `/api/register` | POST   | Registers a new user               |
| `/api/login`    | POST   | Authenticates a user               |
| `/api/message`  | POST   | Sends a message to the server      |

## Key Benefits of the Dual Approach

1. **Flexibility**
   - This approach supports both the original socket-based communication and the newer RESTful architecture, catering to different scenarios:
     - **Socket-Based**: Ideal for direct client-server communication in a controlled environment.
     - **RESTful API**: Standardized for modern web applications, enabling scalability and integration with external systems.

2. **Backward Compatibility**
   - The original socket-based approach ensures compatibility with existing clients while the REST API adds future-ready capabilities.

3. **Gradual Transition**
   - This dual-approach allows for a smooth transition from traditional socket-based communication to a RESTful architecture without disrupting existing workflows.

