package ServerUtilities;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class CryptoServices {

    // Generate AES secret key from user password and a salt
    public static SecretKey getAESKeyFromPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Random salt string, this should ideally be provided by the user
        String salt = "jjedal!ESAJ840";
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    // Generate DES secret key from user password and a salt
    public static SecretKey getDESKeyFromPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Random salt string, this should ideally be provided by the user
        String salt = "jjedal!ESAJ840";
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "DES");
    }

    // Generate 16-byte initial vector using secure random class
    public static IvParameterSpec generateAESIV() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    // Generate 8-byte initial vector using secure random class
    public static IvParameterSpec generateDESIV() {
        byte[] iv = new byte[8];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String aesEncryptFile(String fileName, String location, SecretKey key, IvParameterSpec iv) throws IOException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        File textFile = new File(location + fileName);
        File encryptedFile = new File(location + "AESEncrypted_" + fileName);
        FileInputStream in = new FileInputStream(textFile);
        FileOutputStream out = new FileOutputStream(encryptedFile);

        byte[] buffer = new byte[64];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                out.write(output);
            }
        }
        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            out.write(outputBytes);
        }
        in.close();
        out.close();

        return "AESEncrypted_" + fileName;
    }

    public static String aesDecryptFile(String fileName, String location, SecretKey key, IvParameterSpec iv) throws IOException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        File encryptedFile = new File(location + fileName);
        File decryptedFile = new File(location + "AESDecrypted_" + fileName);
        FileInputStream in = new FileInputStream(encryptedFile);
        FileOutputStream out = new FileOutputStream(decryptedFile);

        byte[] buffer = new byte[64];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                out.write(output);
            }
        }
        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            out.write(outputBytes);
        }
        in.close();
        out.close();

        return "AESDecrypted_" + fileName;

    }

    public static String desEncryptFile(String fileName, String location, SecretKey key, IvParameterSpec iv) throws IOException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        File textFile = new File(location + fileName);
        File encryptedFile = new File(location + "DESEncrypted_" + fileName);
        FileInputStream in = new FileInputStream(textFile);
        FileOutputStream out = new FileOutputStream(encryptedFile);

        byte[] buffer = new byte[64];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                out.write(output);
            }
        }
        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            out.write(outputBytes);
        }
        in.close();
        out.close();

        return "DESEncrypted_" + fileName;
    }

    public static String desDecryptFile(String fileName, String location, SecretKey key, IvParameterSpec iv) throws IOException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        File encryptedFile = new File(location + fileName);
        File decryptedFile = new File(location + "DESDecrypted_" + fileName);
        FileInputStream in = new FileInputStream(encryptedFile);
        FileOutputStream out = new FileOutputStream(decryptedFile);

        byte[] buffer = new byte[64];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                out.write(output);
            }
        }
        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            out.write(outputBytes);
        }
        in.close();
        out.close();

        return "DESDecrypted_" + fileName;
    }
}
