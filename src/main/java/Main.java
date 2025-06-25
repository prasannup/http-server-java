import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    // Uncomment this block to pass the first stage
    //
     try {
       ServerSocket serverSocket = new ServerSocket(4221);
    
       // Since the tester restarts your program quite often, setting SO_REUSEADDR
    //   // ensures that we don't run into 'Address already in use' errors
       serverSocket.setReuseAddress(true);
    //
       Socket clientSocket = serverSocket.accept(); // Wait for connection from client.
       System.out.println("accepted new connection");
       // object to read and write to the socket
       BufferedReader in = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream())); 
       PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
       String requestMessage, responseMessage;
       // read the request message from the client
        requestMessage = in.readLine();
        System.out.println("requestMessage: " + requestMessage);
        // create a response message

        if(requestMessage !=null &&  requestMessage.split(" ")[1].equals("/")) {
          responseMessage = "HTTP/1.1 200 OK\r\n" ;
                            
        } else {
          responseMessage = "HTTP/1.1 404 Not Found\r\n";
        }

        // send the response message to the client
        out.println(responseMessage);
        System.out.println("responseMessage: " + responseMessage);
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();


     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
}
