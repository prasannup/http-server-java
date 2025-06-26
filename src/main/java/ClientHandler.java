import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String requestMessage = in.readLine();
            System.out.println("requestMessage: " + requestMessage);

            String[] paths = requestMessage.split(" ");
            String path = paths[1];

            String userAgent = null;
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                if (line.startsWith("User-Agent: ")) {
                    userAgent = line.substring(12).trim();
                }
            }

            String responseMessage;

            if (path.equals("/")) {
                responseMessage = "HTTP/1.1 200 OK\r\n\r\n";
            } else if (path.startsWith("/echo/")) {
                String echoMessage = path.substring(6);
                responseMessage = "HTTP/1.1 200 OK\r\n" +
                                  "Content-Type: text/plain\r\n" +
                                  "Content-Length: " + echoMessage.length() + "\r\n" +
                                  "\r\n" +
                                  echoMessage;
            } else if (path.equals("/user-agent")) {
                if (userAgent != null) {
                    responseMessage = "HTTP/1.1 200 OK\r\n" +
                                      "Content-Type: text/plain\r\n" +
                                      "Content-Length: " + userAgent.length() + "\r\n" +
                                      "\r\n" +
                                      userAgent;
                } else {
                    responseMessage = "HTTP/1.1 400 Bad Request\r\n" +
                                      "Content-Type: text/plain\r\n" +
                                      "Content-Length: 11\r\n" +
                                      "\r\n" +
                                      "Bad Request";
                }
            } else {
                responseMessage = "HTTP/1.1 404 Not Found\r\n" +
                                  "Content-Type: text/plain\r\n" +
                                  "Content-Length: 9\r\n" +
                                  "\r\n" +
                                  "Not Found";
            }

            out.print(responseMessage); // use print() to avoid extra \n
            out.flush();

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}
