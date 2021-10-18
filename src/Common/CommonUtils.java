package Common;

import java.io.*;
import java.net.Socket;

public class CommonUtils {

    public static int parsePort(String port) {
        int portNum;
        try {
            if (Integer.parseInt(port) < 0 || Integer.parseInt(port) > 65535) {
                System.out.println("Valid port number range is 0 to 65535. Port number provided is out of range.");
                System.exit(0);
            }
        } catch (NumberFormatException e){
            System.out.println("Specify a valid port number.");
            System.exit(0);
        }
        portNum = Integer.parseInt(port);
        return portNum;
    }

    public static void downloadFile(Socket socket, String fileName, int fileSize) throws IOException {
        FileOutputStream fos = null;
        InputStream in = null;

        try {
            byte[] byteArray = new byte[fileSize];
            in = socket.getInputStream();
            fos = new FileOutputStream(fileName);
            int read = 0;
            int totalRead = 0;
            int remaining = fileSize;
            while((read = in.read(byteArray, 0, Math.min(byteArray.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                fos.write(byteArray, 0, read);
            }
        }
        finally {
           if (fos != null) fos.close();
           //if (in != null) in.close();
        }
    }

    public static void uploadFile(Socket socket, String fileName) throws IOException, FileNotFoundException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            File file = new File(fileName);
            byte[] byteArray = new byte[(int) file.length()];
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            bis.read(byteArray, 0, byteArray.length);
            os = socket.getOutputStream();
            os.write(byteArray, 0, byteArray.length);
            os.flush();
        } finally {
           if (bis != null) bis.close();
           //if (os != null) os.close();
        }
    }


}
