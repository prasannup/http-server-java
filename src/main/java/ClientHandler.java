import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final File baseDirectory;

    public ClientHandler(Socket socket, File baseDirectory) {
        this.clientSocket = socket;
        this.baseDirectory = baseDirectory;
    }

    @Override
    public void run() {
        try (
                InputStream is = clientSocket.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String requestMessage = in.readLine();
            System.out.println("requestMessage: " + requestMessage);

            if (requestMessage == null || requestMessage.isEmpty())
                return;

            String[] parts = requestMessage.split(" ");
            String method = parts[0];
            String path = parts[1];

            System.out.println("Method: " + method + ", Path: " + path);

            int contentLength = 0;
            String userAgent = null;
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                System.out.println("Header: " + line);
                if (line.startsWith("User-Agent: ")) {
                    userAgent = line.substring(12).trim();
                } else if (line.startsWith("Content-Length: ")) {
                    contentLength = Integer.parseInt(line.substring(16).trim());
                }
            }

            String responseMessage;

            // Handle POST /files/{filename}
            if (method.equals("POST") && path.startsWith("/files/") && baseDirectory != null) {
                String filename = path.substring("/files/".length());
                File file = new File(baseDirectory, filename);
                System.out.println("Preparing to write to file: " + file.getAbsolutePath());

                char[] bodyBuffer = new char[contentLength];
                int read = in.read(bodyBuffer, 0, contentLength);
                System.out.println("Expected to read: " + contentLength + ", Actually read: " + read);

                if (read != contentLength) {
                    responseMessage = "HTTP/1.1 400 Bad Request\r\n" +
                            "Content-Type: text/plain\r\n" +
                            "Content-Length: 11\r\n\r\n" +
                            "Bad Request";
                } else {
                    try (FileWriter fw = new FileWriter(file)) {
                        fw.write(bodyBuffer, 0, read);
                    }
                    responseMessage = "HTTP/1.1 201 Created\r\n\r\n";
                }

                out.print(responseMessage);
                out.flush();
                return;
            }

            // Handle GET /
            if (path.equals("/")) {
                responseMessage = "HTTP/1.1 200 OK\r\n\r\n";
            }
            // Handle /echo/
            else if (path.startsWith("/echo/")) {
                String echo = path.substring("/echo/".length());
                responseMessage = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/plain\r\n" +
                        "Content-Length: " + echo.length() + "\r\n\r\n" +
                        echo;
            }
            // Handle /user-agent
            else if (path.equals("/user-agent")) {
                if (userAgent != null) {
                    responseMessage = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/plain\r\n" +
                            "Content-Length: " + userAgent.length() + "\r\n\r\n" +
                            userAgent;
                } else {
                    responseMessage = "HTTP/1.1 400 Bad Request\r\n" +
                            "Content-Type: text/plain\r\n" +
                            "Content-Length: 11\r\n\r\n" +
                            "Bad Request";
                }
            }
            // Handle GET /files/{filename}
            else if (method.equals("GET") && path.startsWith("/files/") && baseDirectory != null) {
                String filename = path.substring("/files/".length());
                File file = new File(baseDirectory, filename);

                if (file.exists() && file.isFile()) {
                    byte[] fileBytes = java.nio.file.Files.readAllBytes(file.toPath());
                    String header = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: application/octet-stream\r\n" +
                            "Content-Length: " + fileBytes.length + "\r\n\r\n";
                    clientSocket.getOutputStream().write(header.getBytes());
                    clientSocket.getOutputStream().write(fileBytes);
                    clientSocket.getOutputStream().flush();
                    return;
                } else {
                    responseMessage = "HTTP/1.1 404 Not Found\r\n" +
                            "Content-Type: text/plain\r\n" +
                            "Content-Length: 9\r\n\r\n" +
                            "Not Found";
                }
            }
            // Handle everything else
            else {
                responseMessage = "HTTP/1.1 404 Not Found\r\n" +
                        "Content-Type: text/plain\r\n" +
                        "Content-Length: 9\r\n\r\n" +
                        "Not Found";
            }

            out.print(responseMessage);
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
