package ServerUtilities;

import ServerUtilities.CryptoServices;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class ServerUtils {
    private static IvParameterSpec ivAES = CryptoServices.generateAESIV();
    private static IvParameterSpec ivDES = CryptoServices.generateDESIV();


    public static String provideService(String[] request, String filename, String location, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, BadPaddingException, InvalidKeyException {
        String service = request[0];
        String cipher = request[1];
        String updatedFileName = null;


        SecretKey secAESKey = CryptoServices.getAESKeyFromPassword(password);
        SecretKey secDESKey = CryptoServices.getDESKeyFromPassword(password);

        if (service.equalsIgnoreCase("decrypt") && cipher.equalsIgnoreCase("aes")) {
            updatedFileName = CryptoServices.aesDecryptFile(filename, location, secAESKey, ivAES);
        } else if (service.equalsIgnoreCase("encrypt") && cipher.equalsIgnoreCase("aes")) {
            updatedFileName = CryptoServices.aesEncryptFile(filename, location, secAESKey, ivAES);
        } else if (service.equalsIgnoreCase("decrypt") && cipher.equalsIgnoreCase("des")) {
            updatedFileName = CryptoServices.desDecryptFile(filename, location, secDESKey, ivDES);
        } else {
            updatedFileName = CryptoServices.desEncryptFile(filename, location, secDESKey, ivDES);
        }

        return updatedFileName;
    }
}
