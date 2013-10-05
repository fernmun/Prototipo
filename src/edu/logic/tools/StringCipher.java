package edu.logic.tools;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.util.encoders.Base64;

public class StringCipher {
    private static final String SECRET = "tvnw63ufg9gh5e92";
    private static SecretKey key;
    private static Cipher cipher;

    static {
        try {
            key = new SecretKeySpec(SECRET.getBytes(), "AES");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static synchronized String encrypt(String plainText) throws Exception {
           cipher.init(Cipher.ENCRYPT_MODE, key);
           byte[] cipherText = cipher.doFinal(plainText.getBytes());
           return  new String(Base64.encode(cipherText));
    }

    public static synchronized String decrypt(String codedText) throws Exception {
           byte[] encypted = Base64.decode(codedText.getBytes());
           cipher.init(Cipher.DECRYPT_MODE, key);
           byte[] decrypted = cipher.doFinal(encypted);  
           return new String(decrypted);
    }
 
}