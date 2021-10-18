package Server;

import Common.CommonUtils;
import ServerUtilities.ServerUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;


public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ServerSocket serverSocket;

    // Constructor
    public ClientHandler(ServerSocket server, Socket client) {
        this.serverSocket = server;
        this.clientSocket = client;
    }

    public void run() {
        PrintWriter clientOut = null;
        BufferedReader clientIn = null;
        boolean active = true;

        try {
            InputStream ins = clientSocket.getInputStream();
            // To client
            clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
            // From client
            clientIn = new BufferedReader(new InputStreamReader(ins));
            String line;


            while ((line = clientIn.readLine()) != null) {

                // Parse service request and split it into tokens
                String[] serviceRequest = line.split(" ");
                // Save serviceArgs, password and filename to local vars
                String[] serviceArgs = {serviceRequest[0], serviceRequest[1]};
                String password = serviceRequest[2];
                String filename = serviceRequest[3];
                String location = "absolutePathToFile";
                int fileSize = Integer.parseInt(serviceRequest[4]);

                CommonUtils.downloadFile(clientSocket, location + filename, fileSize);

                String updatedFileName = ServerUtils.provideService(serviceArgs, filename, location, password);
                File file = new File(location + updatedFileName);
                int updatedFileSize = (int) file.length();

                clientOut.println(updatedFileSize);
                clientOut.println(updatedFileName);
                CommonUtils.uploadFile(clientSocket, location + updatedFileName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (clientOut != null) {
                    clientOut.close();
                }
                if (clientIn != null) {
                    clientIn.close();
                    clientSocket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}