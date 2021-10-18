package Server;

import Common.CommonUtils;
import Server.ClientHandler;

import java.io.*;
import java.net.*;

/*
    The Server.CryptoServer class provides the following services:
    1. Encrypt text file
    2. Decrypt text file

    The Server.CryptoServer class provides the following features:
    1.
    2.
 */

public class CryptoServer {
    public static void main(String[] args)
    {
        ServerSocket server = null;
        try {
            // Basic command line args and port validation and parsing
            int port;
            if (args.length == 0 || args[0] == null
                || args[0].trim().isEmpty()) {
                System.out.println("Invalid command line argument: please specify port number.");
                System.exit(0);
            }
            port = CommonUtils.parsePort(args[0]);
            // Instantiate server socket on the port
            server = new ServerSocket(port);
            server.setReuseAddress(true);

            System.out.println("CryptoServer running - ready for service...");

            // Keep server running indefinitely
            while (true) {

                // Wait for client connection and accept it
                Socket client = server.accept();

                System.out.println(">>> Connected with " + client.getInetAddress().getHostAddress());

                // Create new thread to handle client interaction
                ClientHandler ch = new ClientHandler(server, client);

                // Handle the connected client in a dedicated thread
                new Thread(ch).start();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
