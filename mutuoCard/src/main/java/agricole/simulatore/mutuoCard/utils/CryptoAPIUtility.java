package agricole.simulatore.mutuoCard.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoAPIUtility {

    private static final Logger log = LoggerFactory.getLogger(CryptoAPIUtility.class);

    /**
     * Utilizzo:
     * <pre>
     * String masterKey = "12345678901234567890123456789012";
     * String initVector = "6543210987654321";
     * String decryptedPassword = "PROVAPWD";
     *
     * String masterKeyBase64 = javax.xml.bind.DatatypeConverter.printBase64Binary(masterKey.getBytes());
     * String initVectorBase64 = javax.xml.bind.DatatypeConverter.printBase64Binary(initVector.getBytes());
     *
     * byte[] encryptedPasswordBytes = encrypt_AES_CBC_PKCS7(javax.xml.bind.DatatypeConverter.parseBase64Binary(masterKeyBase64), javax.xml.bind.DatatypeConverter.parseBase64Binary(initVectorBase64), decryptedPassword.getBytes());
     * String encryptedPasswordBase64 = javax.xml.bind.DatatypeConverter.printBase64Binary(encryptedPasswordBytes);
     * TmLogger.debug("ENCRYPTED: " + encryptedPasswordBase64);
     *
     * String decryptedPassword = decryptAsString_AES_CBC_PKCS7(javax.xml.bind.DatatypeConverter.parseBase64Binary(masterKeyBase64), javax.xml.bind.DatatypeConverter.parseBase64Binary(initVectorBase64), encryptedPasswordBytes);
     * TmLogger.debug("DECRYPTED: " + decryptedPassword);
     * @param masterKeyBytes
     * @param initVectorBytes
     * @param encryptedPasswordBytes
     * @return
     * @throws Exception
     */
    public static String decryptAsString_AES_CBC_PKCS7(byte[] masterKeyBytes, byte[] initVectorBytes, byte[] encryptedPasswordBytes) throws Exception {
        byte[] decryptedPasswordBytes = decrypt_AES_CBC_PKCS7(masterKeyBytes, initVectorBytes, encryptedPasswordBytes);
        return new String(decryptedPasswordBytes);
    }

    /**
     * Utilizzo:
     * <pre>
     * String masterKey = "12345678901234567890123456789012";
     * String initVector = "6543210987654321";
     * String decryptedPassword = "PROVAPWD";
     *
     * String masterKeyBase64 = javax.xml.bind.DatatypeConverter.printBase64Binary(masterKey.getBytes());
     * String initVectorBase64 = javax.xml.bind.DatatypeConverter.printBase64Binary(initVector.getBytes());
     *
     * byte[] encryptedPasswordBytes = encrypt_AES_CBC_PKCS7(javax.xml.bind.DatatypeConverter.parseBase64Binary(masterKeyBase64), javax.xml.bind.DatatypeConverter.parseBase64Binary(initVectorBase64), decryptedPassword.getBytes());
     * String encryptedPasswordBase64 = javax.xml.bind.DatatypeConverter.printBase64Binary(encryptedPasswordBytes);
     * TmLogger.debug("ENCRYPTED: " + encryptedPasswordBase64);
     *
     * byte[] decryptedPasswordBytes = decrypt_AES_CBC_PKCS7(javax.xml.bind.DatatypeConverter.parseBase64Binary(masterKeyBase64), javax.xml.bind.DatatypeConverter.parseBase64Binary(initVectorBase64), encryptedPasswordBytes);
     * TmLogger.debug("DECRYPTED: " + new String(decryptedPasswordBytes));
     * <pre>
     * @param masterKeyBytes
     * @param initVectorBytes
     * @param encryptedPasswordBytes
     * @return
     * @throws Exception
     */
    public static byte[] decrypt_AES_CBC_PKCS7(byte[] masterKeyBytes, byte[] initVectorBytes, byte[] encryptedPasswordBytes) throws Exception {
        log.info("Master Key length: {} bytes", masterKeyBytes.length);
        log.info("Initialization Vector length: {} bytes", initVectorBytes.length);
        log.info("Encrypted password bytes length: {} bytes", encryptedPasswordBytes.length);

        javax.crypto.spec.IvParameterSpec iv = new javax.crypto.spec.IvParameterSpec(initVectorBytes);
        javax.crypto.spec.SecretKeySpec skeySpec = new javax.crypto.spec.SecretKeySpec(masterKeyBytes, "AES");

        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, skeySpec, iv);
        return cipher.doFinal(encryptedPasswordBytes);
    }

    /**
     * Genera un Initialization Vector (IV) di 16 byte Random derivato dalla masterKey.
     *
     * @param masterKey La master key da cui generare l'IV.
     * @return Un array di byte di 16 byte come IV.
     * @throws NoSuchAlgorithmException Se l'algoritmo di hashing non è disponibile.
     */
    public static byte[] generateIVFromMasterKey(byte[] masterKey) throws NoSuchAlgorithmException {
       // if (masterKey.length != 32) {
         //   throw new IllegalArgumentException("Master key must be exactly 32 bytes long.");
       // }

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(masterKey);

        byte[] iv = new byte[16];
        System.arraycopy(hash, 0, iv, 0, iv.length);
        return iv;
    }

    /**
     * Decripta un array di byte utilizzando la chiave principale e l'IV forniti.
     *
     * @param masterKey      La chiave principale da utilizzare per la decrittografia.
     * @param iv             La Initialization Vector da utilizzare (16 Bytes).
     * @param encryptedBytes L'array di byte cifrati da decrittografare.
     * @return La stringa decifrata.
     * @throws Exception Se si verifica un errore durante la decrittografia.
     */
    public static String decryptOnly(String masterKey, byte[] iv, byte[] encryptedBytes) throws Exception {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        SecretKeySpec secretKeySpec = new SecretKeySpec(masterKey.getBytes(StandardCharsets.UTF_8), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * Utilizzo:
     * <pre>
     * String masterKey = "12345678901234567890123456789012";
     * String initVector = "6543210987654321";
     * String decryptedPassword = "PROVAPWD";
     *
     * String masterKeyBase64 = javax.xml.bind.DatatypeConverter.printBase64Binary(masterKey.getBytes());
     * String initVectorBase64 = javax.xml.bind.DatatypeConverter.printBase64Binary(initVector.getBytes());
     *
     * byte[] encryptedPasswordBytes = encrypt_AES_CBC_PKCS7(javax.xml.bind.DatatypeConverter.parseBase64Binary(masterKeyBase64), javax.xml.bind.DatatypeConverter.parseBase64Binary(initVectorBase64), decryptedPassword.getBytes());
     * String encryptedPasswordBase64 = javax.xml.bind.DatatypeConverter.printBase64Binary(encryptedPasswordBytes);
     * TmLogger.debug("ENCRYPTED: " + encryptedPasswordBase64);
     * <pre>
     * @param masterKeyBytes
     * @param initVectorBytes
     * @param decryptedPasswordBytes
     * @return
     * @throws Exception
     */
    public static byte[] encrypt_AES_CBC_PKCS7(byte[] masterKeyBytes, byte[] initVectorBytes, byte[] decryptedPasswordBytes) throws Exception {
        log.info("Master Key length: {} bytes", masterKeyBytes.length);
        log.info("Initialization Vector length: {} bytes", initVectorBytes.length);
        log.info("Decrypted password bytes length: {} bytes", decryptedPasswordBytes.length);

        javax.crypto.spec.IvParameterSpec iv = new javax.crypto.spec.IvParameterSpec(initVectorBytes);
        javax.crypto.spec.SecretKeySpec skeySpec = new javax.crypto.spec.SecretKeySpec(masterKeyBytes, "AES");

        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, skeySpec, iv);
        return cipher.doFinal(decryptedPasswordBytes);
    }
}
