package com.prasann.httpserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.prasann.httpserver.ClientHandler;

public class Main {
  public static void main(String[] args) {
    System.out.println("Logs from your program will appear here!");

    String directory = null;
    for (int i = 0; i < args.length - 1; i++) {
      if (args[i].equals("--directory")) {
        directory = args[i + 1];
      }
    }

    if (directory == null) {
      System.out.println("No directory provided. Exiting.");
      return;
    }

    File baseDir = new File(directory);
    System.out.println("Directory set to: " + baseDir.getAbsolutePath());

    try {
      ServerSocket serverSocket = new ServerSocket(4221);
      serverSocket.setReuseAddress(true);
      System.out.println("Server is listening on port 4221...");

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("New client connected");

        Thread thread = new Thread(new ClientHandler(clientSocket, baseDir));
        thread.start();
      }

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}
