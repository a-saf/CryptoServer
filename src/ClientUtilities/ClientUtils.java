package ClientUtilities;

public class ClientUtils {

    public static void optionsMenu() {
        System.out.println("> CryptoServer at your service!\n" +
                "> (I encrypt and decrypt text files using AES and DES formats.)\n" +
                "> Usage: encrypt|decrypt aes|des\n" +
                "> To quit, type \"quit\". \n");
    }

    public static boolean passwordValidation(String password) {
        if (password == null || password.length() < 8) return false;
        return true;
    }

    public static String[] parseRequest(String request) {
        // Case 1: string is empty
        if (request.isEmpty()) {
            System.out.println("Invalid command. Usage: encrypt|decrypt aes|des\n");
            return null;
        }
        // Split into array of strings on white space, trim white spaces in the beginning or at the end of the string
        String[] r = request.trim().split(" ");
        // Case 2: array has one item
        if (r.length == 1) {
            // The command is not the quit command
            if (!r[0].equalsIgnoreCase("quit")) {
                r = null;
                System.out.println("Would you like to quit? Please type \"quit\".\n");
            }
        }
        // Case 3: array has two items
        else if (r.length == 2) {
            if (!r[0].equalsIgnoreCase("decrypt") && !r[0].equalsIgnoreCase("encrypt")) {
                System.out.println("Invalid usage. Must be encrypt or decrypt.\n");
                r = null;
            }
            else if (!r[1].equalsIgnoreCase("aes") && !r[1].equalsIgnoreCase("des")) {
                System.out.println("Invalid usage. Must be aes or des.\n");
                r = null;
            }
        }
        // Case 4: array has more than two items
        else if (r.length > 2) {
            System.out.println("Invalid command. Usage: encrypt|decrypt aes|des\n");
            r = null;
        }

        return r;
    }
}
