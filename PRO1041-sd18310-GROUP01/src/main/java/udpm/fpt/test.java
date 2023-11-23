package udpm.fpt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 *
 * @author NONG HOANG VU
 */
public class test {

    public static String decodeBase64(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    public static String encodeBase64(String plainText) {
        byte[] encodedBytes = Base64.getEncoder().encode(plainText.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        System.out.println("Encode String: " + encodeBase64("Invalid username or password!"));
//        System.out.println("Decode String: " + decodeBase64("MTkyLjE2OC4xMS4xMDI="));
    }
}
