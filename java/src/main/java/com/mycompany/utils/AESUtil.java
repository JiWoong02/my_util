package org.mdback.util;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    private static final String ALGORITHM = "AES";

    //암호화코드
    private static final String ENCRYPTION_KEY = "bR3@p8Vx3d2Lz#7D";

    // 암호화 메서드
    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        String encryptedString = Base64.getEncoder().encodeToString(encryptedData);
        encryptedString = encryptedString.replace("+", "-")
                                            .replace("/", "_")
                                            .replace("=", ".");
        return encryptedString;
    }

    // 복호화 메서드
    public static String decrypt(String encryptedData) throws Exception {
        String decodedString = encryptedData.replace("-", "+")
                                            .replace("_", "/")
                                            .replace(".", "=");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedData = Base64.getDecoder().decode(decodedString);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }
}