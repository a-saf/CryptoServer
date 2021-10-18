package Client;

import ClientUtilities.ClientUtils;
import Common.CommonUtils;

import java.io.*;
import java.net.*;
import java.util.*;

class Client {

    public static void main(String[] args)
    {
        // Basic command line and port validation and parsing
        int port;
        String host;
        boolean active = true;
        String filename = null;
        String location = null;

        if (args.length == 0 || args[0] == null || args[1] == null
                || args[0].trim().isEmpty()
                || args[1].trim().isEmpty()) {
            System.out.println("Usage: java Client.Client host port");
            System.exit(0);
        }
        port = CommonUtils.parsePort(args[1]);
        host = args[0];

        try {
            Socket clientSocket = new Socket(host, port);
            InputStream ins = clientSocket.getInputStream();
            // Write to server
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            // Read from server
            BufferedReader in = new BufferedReader(new InputStreamReader(ins));

            // Initialize scanner object
            Scanner sc = new Scanner(System.in);
            String inputLine = null;

            // Basic user input parsing and validation is done on the client side
            while (active) {

                ClientUtils.optionsMenu();

                // Read user input
                inputLine = sc.nextLine();
                String[] serviceArgs = ClientUtils.parseRequest(inputLine);

                if (serviceArgs != null) {
                    if (serviceArgs.length == 1) {
                        active = false;
                    } else {
                        boolean pass = true;
                        String password = "";

                        // Repeat until user provides valid password
                        while (pass) {
                            System.out.print("For encryption, provide new password.\n" +
                                    "For decryption, provide the password used to encrypt the file.\n" +
                                    "For DES password should be exactly 8 characters. Keep your password safe! \n\n");
                            //Console console = System.console();
                            // Mask password on user input
                            /* if (console != null) {
                                password = String.valueOf(console.readPassword("Enter password: "));
                            } */
                            password = sc.nextLine();
                            pass = !ClientUtils.passwordValidation(password);
                        }

                        // Get the name of the file to be encrypted/decrypted
                        System.out.print("File name: ");
                        filename = sc.nextLine();

                        ///System.out.print("File location: ");
                        //location = sc.nextLine();
                        location = "absolutePathToFile";

                        // Get the size of the file to be transferred
                        File file = new File(location + "\\" + filename);
                        long fileSize = file.length();



                        // Send validated user input to server to execute encryption/decryption
                        // Send user input to the server after initial parsing and validation
                        // Service request string is of the following format: "service algorithm password filename"
                        String serviceRequest = serviceArgs[0] + " " + serviceArgs[1] + " " + password + " " + filename + " " + fileSize;
                        out.println(serviceRequest);
                   }
                }

                System.out.println("Sending " + filename + " to CryptoServer.");
                long start = System.currentTimeMillis();
                CommonUtils.uploadFile(clientSocket, location + "\\" + filename);
                int updatedFileSize = 0;
                String updatedClientName = null;

                if (in.ready()) {
                    updatedFileSize = in.read();
                }
                if (in.ready()) {
                    updatedClientName = in.readLine();
                }

                CommonUtils.downloadFile(clientSocket, location + "\\" +updatedClientName, updatedFileSize);
                long end = System.currentTimeMillis();
                System.out.println("CryptoServer completed request in " + (end - start) + " ms.");


            }
            // Close scanner object
            System.out.println("Closing connection to crypto server. Goodbye!");
            sc.close();
            System.out.println("Connection closed.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
